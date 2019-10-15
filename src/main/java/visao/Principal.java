package visao;

import dao.ControleSatelite;
import gui.GUIPrincipal;
import connection.DriveConnection;

public class Principal {
	public static void main(String[] args) {
		ControleSatelite controle = new ControleSatelite("arqEsquadrao.json", "arqFloresta.json");
		new GUIPrincipal(controle).setVisible(true);
	}
}