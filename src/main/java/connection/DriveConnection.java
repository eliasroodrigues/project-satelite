/*
*   Trabalho I de POO   
*
*   Classe: DriveConnection.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package connection;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.Drive.Files.Get;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class DriveConnection {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveConnection.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Método para conectar-se com a pasta do google drive e fazer o upload dos
     * arquivos necessários. Também verifica a existência do arquivo e o atualiza
     * com as novas informações.
     *
     * @param nomeArquivo Nome do arquivo a ser feito o upload na pasta.
     *
     * @return true se deu certo ou false se não.
     */
    public static boolean upload(String nomeArquivo) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String idPasta = "1wu2TplaNWcJLrNgt77AiBSjRfU6AJ8AD";
        
        FileList result = service.files().list()
                .setQ("name contains '"+nomeArquivo+"' and parents in '"+idPasta+"' and trashed = false")
                .setFields("nextPageToken, files(name, id, parents)")
                .execute();

        List<File> files = result.getFiles();

        if (files == null || files.isEmpty()) {
            try {
            File json = new File();
            json.setName(nomeArquivo);
            json.setParents(Collections.singletonList(idPasta));
            java.io.File filePath = new java.io.File("src/files/"+nomeArquivo);
            FileContent mediaContent = new FileContent(null, filePath);
            File file = service.files().create(json, mediaContent)
                    .execute();
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                File json = new File();
                json.setName(nomeArquivo);
                java.io.File filePath = new java.io.File("src/files/"+nomeArquivo);
                FileContent mediaContent = new FileContent(null, filePath);
                File file = service.files().update(files.get(0).getId(), json,
                    mediaContent).execute();
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Método para conectar-se com a pasta do google drive e fazer o download dos
     * arquivos necessários.
     *
     * @return true se deu certo ou false se não.
     */
    public static boolean download() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String idPasta = "1wu2TplaNWcJLrNgt77AiBSjRfU6AJ8AD";
        
        FileList result = service.files().list()
                .setQ("parents in '"+idPasta+"' and trashed = false")
                .setFields("nextPageToken, files(name, id, parents)")
                .execute();

        List<File> files = result.getFiles();

        System.out.println("Arquivos: " + files);

        if (!(files == null || files.isEmpty())) {
            try {
                for (int i = 0; i < files.size(); i++) {
                    OutputStream outputStream = new FileOutputStream("src/files/" + files.get(i).getName());
                    service.files().get(files.get(i).getId()).executeMediaAndDownloadTo(outputStream);
                    outputStream.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}