package modelo;

public enum EspecialidadeEsq {
	
	COMBATE_INCENDIO("Combater incêndio"),
	RESGATE_ANIMAL("Resgate de animais"),
	RESGATE_INDIGENA("Resgate de indígenas");
	
	private String descricao;

	EspecialidadeEsq(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
