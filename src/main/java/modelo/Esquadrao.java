/*
*   Trabalho I de POO   
*
*   Classe: Esquadrao.java
*
*   Alunos: Ana Paula Pacheco
*           Elias Eduardo Silva Rodrigues
*
*/

package modelo;

public class Esquadrao {
	
	private String nomeEsq;
	private EspecialidadeEsq especialidadeEsq;
	private int tamEsquadrao;
	
	/**
	 * Construtor sem parâmetro para a classe.
	 */
	public Esquadrao() {
		this.nomeEsq = "Não informado";
		this.especialidadeEsq = null;
		this.tamEsquadrao = -1;
	}
	
	/**
	 * Construtor com parâmetro para a classe.
	 * 
	 * @param nomeEsq Nome do esquadrão.
	 * @param especialidadeEsq Tipo da especialidade.
	 * @param tamEsquadrao Valor inteiro do tamanho do esquadrão.
	 */
	public Esquadrao(String nomeEsq, EspecialidadeEsq especialidadeEsq,
			int tamEsquadrao) {
		this.nomeEsq = nomeEsq;
		this.especialidadeEsq = especialidadeEsq;
		this.tamEsquadrao = tamEsquadrao;
	}

	public String getNomeEsq() { return nomeEsq; }
	
	public void setNomeEsq(String nomeEsq) { this.nomeEsq = nomeEsq; }
	
	public EspecialidadeEsq getEspecialidadeEsq() { return especialidadeEsq; }
	
	public void setEspecialidadeEsq(EspecialidadeEsq especialidadeEsq) {
		this.especialidadeEsq = especialidadeEsq; 
	}
	
	public int getTamEsquadrao() { return tamEsquadrao; }
	
	public void setTamEsquadrao(int tamEsquadrao) {
		this.tamEsquadrao = tamEsquadrao;
	}
	
	/**
	 * Método toString(). 
	 *
	 * @return Texto dos atributos da classe.
	 */
	public String toString() {
		String retorno = 
				"Esquadrão: " + this.getNomeEsq() +
				"\nEspecialidade: " + this.getEspecialidadeEsq() +
				"\nTamanho: " + this.getTamEsquadrao() +
				".\n";
		
		return retorno;
	}
}