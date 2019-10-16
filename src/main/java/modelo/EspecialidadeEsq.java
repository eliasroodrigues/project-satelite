/*
*   Trabalho I de POO   
*
*   Enum: EspecialidadeEnum.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

package modelo;

public enum EspecialidadeEsq {
	
	COMBATE_INCENDIO("Combater incêndio"),
	RESGATE_ANIMAL("Resgate de animais"),
	RESGATE_INDIGENA("Resgate de indígenas");
	
	private String descricao;

	/**
	 * Constroutor com parâmetro do enum.
	 *
	 * @param descricao tipo da especialidade:
	 *					COMBATE_INCENDIO,
	 *					RESGATE_ANIMAL,
	 *					RESGATE_INDIGENA.
	 */
	EspecialidadeEsq(String descricao) { this.descricao = descricao; }
	
	/**
	 * Método para retornar a descrição
	 *
	 * @return Tipo de especialidade.
	 */
	public String getDescricao() { return this.descricao; }
}