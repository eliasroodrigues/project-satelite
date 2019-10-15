package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.event.ActionEvent;

import image.PGMFileReader;
import image.PGMImage;
import image.PPMFileReader;

import dao.ControleSatelite;
import image.PPMFileReader;
import util.Ppm;

public class GUIGrafico extends JFrame {

	private JPanel contentPane;
	private JTextField textAreaChamas;
	private JTextField textAumento;
	private JTextField textData;
	private ControleSatelite controle;
	private JLabel jLabelFoto = new JLabel("");
	private JComboBox comboRegiao = new JComboBox();
	private PGMImage image;
	private JPanel panel_1 = new JPanel();

	public GUIGrafico(ControleSatelite controle) {
		GUIGraf();
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
	public void GUIGraf() {
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
		
		panel_1.setBounds(12, 74, 202, 138);
		panel.add(panel_1);
		
		panel_1.add(jLabelFoto);
		
		JLabel lblNewLabel = new JLabel("Nome Região: ");
		lblNewLabel.setBounds(23, 25, 125, 15);
		panel.add(lblNewLabel);
		
		comboRegiao.setModel(new DefaultComboBoxModel(new String[] {"Sem região"}));
		comboRegiao.setBounds(280, 20, 158, 24);
		panel.add(comboRegiao);
		
		comboRegiao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = comboRegiao.getSelectedItem().toString();
				String imagePath = "src/files/" + nome + ".ppm";
				
				if (!nome.equals("Sem região")) {
					image = PPMFileReader.readImage(imagePath).convertToPGM();
					System.out.println(image);
					draw();
					
					int[] cores = Ppm.porcentagem(nome);
					System.out.println("R: " + cores[0] + "\tG: " + cores[1] + "\tB: " + cores[2]);
				}
				
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnVoltar.setBounds(33, 263, 117, 25);
		panel.add(btnVoltar);
		
		JLabel lblDerea = new JLabel("% de área em chamas");
		lblDerea.setBounds(261, 74, 154, 15);
		panel.add(lblDerea);
		
		textAreaChamas = new JTextField();
		textAreaChamas.setEditable(false);
		textAreaChamas.setBounds(280, 101, 114, 19);
		panel.add(textAreaChamas);
		textAreaChamas.setColumns(10);
		
		JLabel lblDoAumento = new JLabel("% de aumento ");
		lblDoAumento.setBounds(280, 132, 135, 15);
		panel.add(lblDoAumento);
		
		textAumento = new JTextField();
		textAumento.setEditable(false);
		textAumento.setBounds(280, 159, 114, 19);
		panel.add(textAumento);
		textAumento.setColumns(10);
		
		textData = new JTextField();
		textData.setEditable(false);
		textData.setBounds(52, 214, 114, 19);
		panel.add(textData);
		textData.setColumns(10);
	}
	
    public void draw() {
        MemoryImageSource source = new MemoryImageSource(image.getWidth(), image.getHeight(), ColorModel.getRGBdefault(), image.toRGBModel(), 0, image.getWidth());
        Image img = Toolkit.getDefaultToolkit().createImage(source);
        // jLabelFoto = new JLabel(new ImageIcon(img.getScaledInstance(200, 138, Image.SCALE_SMOOTH)));
        jLabelFoto.setIcon(new ImageIcon(img));
        panel_1.add(jLabelFoto);
        System.out.println("Acabou o draw");
    }
}
