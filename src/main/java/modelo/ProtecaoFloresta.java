/*
*   Trabalho I de POO   
*
*   Enum: ProtecaoFloresta.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

package modelo;

public enum ProtecaoFloresta {
	
	PROTEGIDO("Área de proteção"),
	NAO_PROTEGIDO("Área não pretegida");
	
	private String descricao;
	
	/**
	 * Constroutor com parâmetro do enum.
	 *
	 * @param descricao tipo da áre de proteção:
	 *					PROTEGIDO,
	 *					NAO_PROTEGIDO.
	 */	
	ProtecaoFloresta(String descricao) { this.descricao = descricao; }
	
	/**
	 * Método para retornar a descrição
	 *
	 * @return Tipo de especialidade.
	 */
	public String getDescricao() { return this.descricao; }
}