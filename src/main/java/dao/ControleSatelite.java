/*
*   Trabalho I de POO   
*
*   Classe: ControleSatelite.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

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
	 * json e importa os dados.
	 *
	 * @param arqEsquadrao Nome do arquivo json para armazenar os
	 *					   dados dos esquadrões cadastrados.
	 * @param arqFloresta  Nome do arquivo json para armazenar os
	 *					   dados das regiões cadastradas.
	 */
	public ControleSatelite(String arqEsquadrao, String arqFloresta) {
		this();
		this.arqFloresta  = arqFloresta;
        this.arqEsquadrao = arqEsquadrao;
		this.importarDados(arqEsquadrao, arqFloresta);
	}

    /**
     * Método para retornar o nome do arquivo json.
     *
     * @return nome do arquivo json. 
     */
    public String getArqFloresta() { return this.arqFloresta; }

    /**
     * Método para retornar o nome do arquivo json.
     *
     * @return nome do arquivo json. 
     */
    public String getArqEsquadrao() { return this.arqEsquadrao; }

    /**
     * Método para cadastrar esquadrões na lista de esquadrões. Se
     * a lista estiver vazia insere o primeiro esquadrão, se já existir
     * um esqudrão com as mesmas informações ele não é cadastrado e
     * se for um novo esquadrão ele é inserido na lista.
     *
     * @param nomeEsq Nome do esquadrão a ser cadastrado.
     * @param especialidadeEsq Tipo da especialidade do esquadrão de acordo com
     *                         o enum.
     * @param tamEsq Valor inteiro que determina o tamanho do esquadrão, ou seja,
     *               quantos membros ele possui.  
     *
     * @return 1 se cadastrou ou 0 se não cadastrou.
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

    /**
     * Método para cadastrar regiões na lista de regiões. Se
     * a lista estiver vazia insere a primeira região, se já existir
     * uma região com as mesmas informações ele não é cadastrado e
     * se for uma nova região ela é inserida na lista.
     *
     * @param nomeReg Nome da região a ser cadastrada.
     * @param areaProtec Enum que determina se é PROTEGIDO ou NAO_PROTEGIDO.
     * @param NomeEsq Nome do esquadrão alocado à região.
     * @param imagemRegiao Nome da imagem da região.
     * @param cores Vetor inteiro de 3 posições da quantidade de cores RGB.
     *
     * @return 1 se cadastrou ou 0 se não cadastrou.
     */
    public int cadastrarRegiao(String nomeReg, ProtecaoFloresta areaProtec,
            String nomeEsq, String imagemRegiao, int[] cores) {
        if (this.floresta.isEmpty()) {
            Floresta flor = new Floresta();
            flor.setNomeRegiao(nomeReg);
            flor.setAreaProtecao(areaProtec);
            flor.setEsquadrao(retornaEsquadrao(nomeEsq));
            flor.setImagemRegiao(imagemRegiao);
            flor.setCores(cores);
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
                flor.setCores(cores);
                floresta.add(flor);
                System.out.println(flor.toString());
                return 1;
            }
        }
        return 0;
    }

    /**
     * Método para retornar um esquadrão sendo informado o nome.
     *
     * @param nomeEsq Nome do esquadrão a ser retornado.
     *
     * @return Esquadrão se o encontrou ou null se não encontrou.
     */
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

    /**
     * Método para cadastrar o nome da imagem na região correta.
     *
     * @param imagemRegiao Nome da região a ter a imagem.
     * @param cores Vetor inteiro de 3 posições da quantidade de cores RGB.
     */
    public void cadastrarImagem(String imagemRegiao, int[] cores) {
        if (!this.floresta.isEmpty()) {
            for (int i = 0; i < floresta.size(); i++) {
                if (floresta.get(i).getNomeRegiao().equals(imagemRegiao)) {
                    floresta.get(i).setImagemRegiao(imagemRegiao + ".ppm");
                    floresta.get(i).setCores(cores);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não há regiões cadastradas.",
                "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Método para verificar se possui um esquadrão na lista de esquadrões.
     *
     * @param nomeEsq Nome do esquadrão a ser verificado.
     *
     * @return true se não existir ou false se existir.
     */
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

    /**
     * Método para verificar se possui uma região na lista de regiões.
     *
     * @param nomeReg Nome da região a ser verificada.
     *
     * @return true se não existir ou false se existir.
     */
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

    /**
     * Método para verificar se uma região já possui imagem cadastrada.
     *
     * @param nomeReg Nome da região a ser verificada.
     *
     * @return true se já possui ou false se não possui.
     */
    public boolean contemImagem(String nomeReg) {
        for (Floresta flo : floresta) {
            if (flo.getNomeRegiao().equals(nomeReg)) {
                if (!flo.getImagemRegiao().equals("Não informado")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método para retornar um vetor dos nomes das regiões.
     *
     * @return vetor de String com nomes das regiões cadastradas.
     */
    public String[] nomesRegioes() {
        String nomesReg[] = new String[50];
        for (int i = 0; i < floresta.size(); i++) {
            if (floresta.get(i) != null) {
                nomesReg[i] = floresta.get(i).getNomeRegiao();
            }
        }
        return nomesReg;
    }

    /**
     * Método para retornar o vetor de cores RGB da imagem da região.
     *
     * @param nomeRegiao Nome da região a ser verificado as cores.
     *
     * @return vetor do tipo int[3] das cores ou null se a região
     *         não possuir cores.
     */
    public int[] coresRegiao(String nomeRegiao) {
        for (int i = 0; i < floresta.size(); i++) {
            if (floresta.get(i) != null) {
                if (floresta.get(i).getNomeRegiao().equals(nomeRegiao)) {
                    return floresta.get(i).getCores();
                }
            }
        }
        return null;
    }

    /**
     * Método para importar os dados dos esquadrões do arquivo json.
     *
     * @param nomeArq Nome do arquivo a ser lido.
     *
     * @return true se deu certo ou false se deu erro na leitura.
     */
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

    /**
     * Método para importar os dados das regiões do arquivo json.
     *
     * @param nomeArq Nome do arquivo a ser lido.
     *
     * @return true se deu certo ou false se deu erro na leitura.
     */
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
                                    floresta.getImagemRegiao(),
                                    floresta.getCores());
                    System.out.println(floresta.toString());
                    linha = leitor.readLine();
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Método para importar os dados do arquivo json.
     *
     * @param arqEsquadrao Nome do arquivo a ser lido.
     * @param arqFloresta Nome do arquivo a ser lido.
     *
     * @return true se deu certo ou false se deu erro na leitura.
     */
	public boolean importarDados(String arqEsquadrao, String arqFloresta) {
    	return !(!importarEsquadrao(arqEsquadrao) || !importarFloresta(arqFloresta));
    }

    /**
     * Método para enviar os dados para os arquivos json. Também já envia-os para
     * o drive fazendo o upload junto com o upload das imagens.
     */
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

    /**
     * Método para enviar os dados da lista de regiões para o json.
     *
     * @param nomeArq Nome do arquivo a ser lido.
     *
     * @return true se deu certo.
     */
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