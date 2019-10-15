package modelo;

public class Floresta {
	
	private String nomeRegiao;
	private ProtecaoFloresta areaProtecao;
	private Esquadrao esquadrao;
	private String imagemRegiao;
	
	public Floresta() {
		this.nomeRegiao = "Não informado.";
		this.areaProtecao = null;
		this.esquadrao = new Esquadrao();
		this.imagemRegiao = "Não informado.";
	}
	
	public Floresta(String nomeRegiao, ProtecaoFloresta areaProtecao,
			Esquadrao esquadrao, String imagemRegiao) {
		this.nomeRegiao = nomeRegiao;
		this.areaProtecao = areaProtecao;
		this.esquadrao = new Esquadrao();
		this.esquadrao = esquadrao;
		this.imagemRegiao = imagemRegiao;
	}
	
	public String getNomeRegiao() {
		return nomeRegiao;
	}
	
	public void setNomeRegiao(String nomeRegiao) {
		this.nomeRegiao = nomeRegiao;
	}
	
	public ProtecaoFloresta getAreaProtecao() {
		return areaProtecao;
	}

	public void setAreaProtecao(ProtecaoFloresta areaProtecao) {
		this.areaProtecao = areaProtecao;
	}

	public Esquadrao getEsquadrao() {
		return esquadrao;
	}
	
	public String getEsq() {
		return esquadrao.getNomeEsq();
	}
	
	public void setEsquadrao(Esquadrao esquadrao) {
		this.esquadrao = esquadrao;
	}
	
	public String getImagemRegiao() {
		return imagemRegiao;
	}
	
	public void setImagemRegiao(String imagemRegiao) {
		this.imagemRegiao = imagemRegiao;
	}
	
	public String toString() {
		String retorno = 
				"Floresta: " + this.getNomeRegiao() +
				"\nÁrea de Proteção: " + this.getAreaProtecao() +
				"\nEsquadrão: " + this.getEsquadrao() +
				"\nImagem: " + this.getImagemRegiao() +
				".\n";
		
		return retorno;
	}
	
}
