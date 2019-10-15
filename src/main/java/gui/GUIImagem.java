package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

import dao.ControleSatelite;
import util.Ppm;

public class GUIImagem extends JFrame {

	private JPanel contentPane;
	private ControleSatelite controle;
	private JComboBox comboRegiao = new JComboBox();

	public GUIImagem(ControleSatelite controle) {
		GUIImag();
		this.controle = new ControleSatelite();
		this.controle = controle;

		String nomesRegiao[] = controle.nomesRegioes();
        for (String nome : nomesRegiao) {
            if (nome != null) {
                comboRegiao.addItem(nome);
            }
        }
	}

	/**
	 * Create the frame.
	 */
	public void GUIImag() {
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
		
		JLabel lblNewLabel = new JLabel("Nome da Região:");
		lblNewLabel.setBounds(12, 25, 139, 15);
		panel.add(lblNewLabel);
		
		comboRegiao.setModel(new DefaultComboBoxModel(new String[] {"Sem região"}));
		comboRegiao.setBounds(250, 25, 170, 24);
		panel.add(comboRegiao);

		JPanel panelImagem = new JPanel();
		panelImagem.setBounds(121, 98, 200, 138);
		panel.add(panelImagem);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setBounds(28, 263, 117, 25);
		panel.add(btnVoltar);
		
		JButton btnGerar = new JButton("Gerar ");
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = comboRegiao.getSelectedItem().toString();
				String imagePath = "src/files/" + nome + ".ppm";

				if (!nome.equals("Sem região")) {
					try {
						Ppm.ppmGenerate(nome + ".ppm");	
						controle.cadastrarImagem(nome);
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma região.",
        				"Informação", JOptionPane.INFORMATION_MESSAGE);
				} 
			}
		});
		btnGerar.setBounds(321, 263, 117, 25);
		panel.add(btnGerar);
	}
}