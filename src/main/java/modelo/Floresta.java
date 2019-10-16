/*
*   Trabalho I de POO   
*
*   Classe: Floresta.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

package modelo;

public class Floresta {
	
	private String nomeRegiao;
	private ProtecaoFloresta areaProtecao;
	private Esquadrao esquadrao;
	private String imagemRegiao;
	private int[] cores;
	
	/**
	 * Construtor sem parâmetro para a classe.
	 */
	public Floresta() {
		this.nomeRegiao = "Não informado.";
		this.areaProtecao = null;
		this.esquadrao = new Esquadrao();
		this.imagemRegiao = "Não informado.";
		this.cores = new int[3];
	}
	
	/**
	 * Construtor com parâmetro para a classe.
	 * 
	 * @param nomeRegiao Nome da região.
	 * @param areaProtecao Tipo da area de proteção.
	 * @param esquadrao Esquadrão alocado a região.
	 * @param imagemRegiao Nome da imagem da região.
	 */
	public Floresta(String nomeRegiao, ProtecaoFloresta areaProtecao,
			Esquadrao esquadrao, String imagemRegiao) {
		this();
		this.nomeRegiao = nomeRegiao;
		this.areaProtecao = areaProtecao;
		this.esquadrao = new Esquadrao();
		this.esquadrao = esquadrao;
		this.imagemRegiao = imagemRegiao;
	}
	
	public String getNomeRegiao() { return nomeRegiao; }
	
	public void setNomeRegiao(String nomeRegiao) {
		this.nomeRegiao = nomeRegiao;
	}
	
	public ProtecaoFloresta getAreaProtecao() { return areaProtecao; }

	public void setAreaProtecao(ProtecaoFloresta areaProtecao) {
		this.areaProtecao = areaProtecao;
	}

	public Esquadrao getEsquadrao() { return esquadrao; }
	
	public String getEsq() { return esquadrao.getNomeEsq(); }
	
	public void setEsquadrao(Esquadrao esquadrao) {
		this.esquadrao = esquadrao;
	}
	
	public String getImagemRegiao() { return imagemRegiao; }
	
	public void setImagemRegiao(String imagemRegiao) {
		this.imagemRegiao = imagemRegiao;
	}
	
	public int[] getCores() { return cores; }
	
	public void setCores(int[] cores) { this.cores = cores; }

	/**
	 * Método toString(). 
	 *
	 * @return Texto dos atributos da classe.
	 */
	public String toString() {
		String retorno = 
				"Floresta: " + this.getNomeRegiao() +
				"\nÁrea de Proteção: " + this.getAreaProtecao() +
				"\nEsquadrão: " + this.getEsquadrao() +
				"\nImagem: " + this.getImagemRegiao() +
				"\nCores: " + this.getCores() +
				".\n";
		
		return retorno;
	}
}