package dao;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.security.GeneralSecurityException;
import com.google.gson.Gson;

import connection.DriveConnection;
import modelo.*;

public class ControleSatelite {

	private List<Floresta>  floresta;
    private List<Esquadrao> esquadrao;
	private String arqFloresta;
    private String arqEsquadrao;
	
	/**
	 * Construtor sem parâmetro que inicia as duas listas
	 * como nulo e os nomes dos arquivos como Não informado.
	 */
	public ControleSatelite() {
		this.floresta   = new ArrayList<>(10);
        this.esquadrao  = new ArrayList<>(10);
		this.arqFloresta  = "Não informado";
        this.arqEsquadrao = "Não informado";
	}

	/**
	 * Construtor com parâmetros que recebe o nome dos arquivos
	 * json.
	 *
	 * @param arqEsquadrao Nome do arquivo json para salver os
	 *					   dados dos esquadrões cadastrados.
	 * @param arqFloresta  Nome do arquivo json para salvar os
	 *					   dados das regiões cadastradas.
	 */
	public ControleSatelite(String arqEsquadrao, String arqFloresta) {
		this();
		this.arqFloresta  = arqFloresta;
        this.arqEsquadrao = arqEsquadrao;
		this.importarDados(arqEsquadrao, arqFloresta);
	}

    /**
     * Método para cadastrar esquadrões na lista de esquadrão. Se
     * a lista estiver vazia insere o primeiro esquadrão, se já existir
     * um esqudrão com as mesmas informações ele não é cadastrado e
     * se for um novo esquadrão ele é inserido na lista.
     *
     * @param nomeEsq Nome do arquivo json para salver os dados dos esquadrões 
     *                cadastrados.
     * @param especialidadeEsq Tipo da especialidade do esquadrão de acordo com
     *                         o enum.
     * @param tamEsq Valor inteiro que determina o tamanho do esquadrão, ou seja,
     *               quantos membros ele possui.  
     */
    public int cadastrarEsquadrao(String nomeEsq, EspecialidadeEsq especialidadeEsq,
            int tamEsq) {
        if (this.esquadrao.isEmpty()) {
            Esquadrao esq = new Esquadrao();
            esq.setNomeEsq(nomeEsq);
            esq.setEspecialidadeEsq(especialidadeEsq);
            esq.setTamEsquadrao(tamEsq);
            esquadrao.add(esq);
            return 1;
        } else {
            if (this.contemEsquadrao(nomeEsq)) {
                Esquadrao esq = new Esquadrao();
                esq.setNomeEsq(nomeEsq);
                esq.setEspecialidadeEsq(especialidadeEsq);
                esq.setTamEsquadrao(tamEsq);
                esquadrao.add(esq);
                return 1;               
            }
        }
        return 0;
    }

    public int cadastrarRegiao(String nomeReg, ProtecaoFloresta areaProtec,
            String nomeEsq, String imagemRegiao) {
        if (this.floresta.isEmpty()) {
            Floresta flor = new Floresta();
            flor.setNomeRegiao(nomeReg);
            flor.setAreaProtecao(areaProtec);
            flor.setEsquadrao(retornaEsquadrao(nomeEsq));
            flor.setImagemRegiao(imagemRegiao);
            floresta.add(flor);
            System.out.println(flor.toString());
            return 1;
        } else {
            if (this.contemFloresta(nomeReg)) {
                Floresta flor = new Floresta();
                flor.setNomeRegiao(nomeReg);
                flor.setAreaProtecao(areaProtec);
                flor.setEsquadrao(retornaEsquadrao(nomeEsq));
                flor.setImagemRegiao(imagemRegiao);
                floresta.add(flor);
                System.out.println(flor.toString());
                return 1;
            }
        }
        return 0;
    }

    public Esquadrao retornaEsquadrao(String nomeEsq) {
        Esquadrao Esq = new Esquadrao();
        for (int i = 0; i < esquadrao.size(); i++) {
            if (esquadrao.get(i).getNomeEsq().equals(nomeEsq)){
                Esq = esquadrao.get(i);
                return Esq;
            }   
        }
        return null;
    }

    public void cadastrarImagem(String imagemRegiao) {
        if (!this.floresta.isEmpty()) {
            for (int i = 0; i < floresta.size(); i++) {
                if (floresta.get(i).getNomeRegiao().equals(imagemRegiao)) {
                    floresta.get(i).setImagemRegiao(imagemRegiao + ".ppm");
                    // System.out.println(floresta.get(i).toString());
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não há regiões cadastradas.",
                "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean contemEsquadrao(String nomeEsq) {
        for (Esquadrao esq : esquadrao) {
            if (!esq.getNomeEsq().equals(nomeEsq)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean contemFloresta(String nomeReg) {
        for (Floresta flo : floresta) {
            if (!flo.getNomeRegiao().equals(nomeReg)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String[] nomesRegioes() {
        String nomesReg[] = new String[50];
        for (int i = 0; i < floresta.size(); i++) {
            if (floresta.get(i) != null) {
                nomesReg[i] = floresta.get(i).getNomeRegiao();
            }
        }
        return nomesReg;
    }

    private boolean importarEsquadrao(String nomeArq) {
        Gson gson = new Gson();
        Esquadrao esquadrao = new Esquadrao();

        try {
            FileReader reader;
            reader = new FileReader("src/files/"+nomeArq);
            try (BufferedReader leitor = new BufferedReader(reader)) {
                String linha = leitor.readLine();

                while (linha != null) {
                    esquadrao = gson.fromJson(linha, Esquadrao.class);
                    cadastrarEsquadrao(esquadrao.getNomeEsq(),
                                       esquadrao.getEspecialidadeEsq(),
                                       esquadrao.getTamEsquadrao());
                    System.out.println(esquadrao.toString());
                    linha = leitor.readLine();
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean importarFloresta(String nomeArq) {
        Gson gson = new Gson();
        Floresta floresta = new Floresta();

        try {
            FileReader reader;
            reader = new FileReader("src/files/"+nomeArq);
            try (BufferedReader leitor = new BufferedReader(reader)) {
                String linha = leitor.readLine();

                while (linha != null) {
                    floresta = gson.fromJson(linha, Floresta.class);
                    cadastrarRegiao(floresta.getNomeRegiao(),
                                    floresta.getAreaProtecao(),
                                    floresta.getEsq(),
                                    floresta.getImagemRegiao());
                    System.out.println(floresta.toString());
                    linha = leitor.readLine();
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

	public boolean importarDados(String arqEsquadrao, String arqFloresta) {
    	return !(!importarEsquadrao(arqEsquadrao) || !importarFloresta(arqFloresta));
    }

    public void enviarDados() throws IOException, GeneralSecurityException {
        if (florestaToJson(this.arqFloresta)) {
            try {
                DriveConnection.upload(this.arqFloresta);
                for (int i = 0; i < floresta.size(); i++) {
                    if (!floresta.get(i).getImagemRegiao().equals("Não informado")) {
                        DriveConnection.upload(floresta.get(i).getImagemRegiao());
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ERRO: Erro de leitura do arquivo json.",
                    "Alerta", JOptionPane.WARNING_MESSAGE);
            } catch(GeneralSecurityException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ERRO: Conexão com drive não realizada.",
                    "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Upload de dados completa.",
                "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "ERRO: Arquivo json inexistente.",
                "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean florestaToJson(String nomeArq) {
        Gson gson = new Gson();

        try {
            try (FileWriter writer = new FileWriter("src/files/"+nomeArq)) {
                for (int i = 0; i < floresta.size(); i++) {
                    if (floresta.get(i) != null) {
                        String aux = gson.toJson(floresta.get(i));
                        System.out.println(aux);
                        writer.write(aux);
                        writer.write("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}