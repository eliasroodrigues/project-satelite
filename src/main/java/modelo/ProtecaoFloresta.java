package modelo;

public enum ProtecaoFloresta {
	
	PROTEGIDO("Área de proteção"),
	NAO_PROTEGIDO("Área não pretegida");
	
	private String descricao;
	
	ProtecaoFloresta(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
