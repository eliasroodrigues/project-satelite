package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;

import dao.ControleSatelite;
import connection.DriveConnection;

public class GUIPrincipal extends JFrame {

	private JPanel contentPane;
	private ControleSatelite controle;

	public GUIPrincipal(ControleSatelite controle) {
		GUIPrin();
		this.controle = new ControleSatelite();
		this.controle = controle;
	}

	/**
	 * Create the frame.
	 */
	public void GUIPrin() {
		setTitle("Satélite");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 450, 300);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnGerarImagem = new JButton("Gerar Imagem");
		btnGerarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUIImagem(controle).setVisible(true);
			}
			
		});
		btnGerarImagem.setBounds(37, 55, 133, 25);
		panel.add(btnGerarImagem);
		
		JButton btnGerarGrfico = new JButton("Gerar Gráfico");
		btnGerarGrfico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUIGrafico(controle).setVisible(true);
			}
		});
		btnGerarGrfico.setBounds(271, 55, 133, 25);
		panel.add(btnGerarGrfico);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*try {
					DriveConnection.download();
				} catch(IOException f) {
					f.printStackTrace();
				} catch(GeneralSecurityException g) {
					g.printStackTrace();
				}*/
			}
		});
		btnBuscar.setBounds(42, 188, 128, 25);
		panel.add(btnBuscar);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controle.enviarDados();
				} catch(IOException ex) {
					ex.printStackTrace();
				} catch(GeneralSecurityException ex) {
        			ex.printStackTrace();
        		}
			}
		});
		btnEnviar.setBounds(271, 188, 133, 25);
		panel.add(btnEnviar);
	}
}
