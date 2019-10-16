package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;

import image.PGMFileReader;
import image.PGMImage;
import image.PPMFileReader;

import dao.ControleSatelite;
import util.Ppm;

public class GUIImagem extends JFrame {

	private JPanel contentPane;
	private ControleSatelite controle;
	private JComboBox comboRegiao = new JComboBox();
	private PGMImage image;
	private JLabel jLabelFoto = new JLabel();
	private JPanel panelImagem = new JPanel();;

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
		
		panelImagem.setBounds(121, 98, 200, 138);
		panel.add(panelImagem);
		
		panelImagem.add(jLabelFoto);
		
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
				int[] cores = new int[3]; 

				if (!nome.equals("Sem região")) {
					if (!controle.contemImagem(nome)) {
						try {
							cores = Ppm.ppmGenerate(nome + ".ppm");	
							image = PPMFileReader.readImage(imagePath).convertToPGM();
							System.out.println(image);
							draw();
							
							controle.cadastrarImagem(nome, cores);
						} catch(IOException e) {
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Região já possui imagem registrada.",
        					"Informação", JOptionPane.INFORMATION_MESSAGE);	
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
	
    public void draw() {
        MemoryImageSource source = new MemoryImageSource(image.getWidth(), image.getHeight(), ColorModel.getRGBdefault(), image.toRGBModel(), 0, image.getWidth());
        Image img = Toolkit.getDefaultToolkit().createImage(source);
        jLabelFoto.setIcon(new ImageIcon(img.getScaledInstance(200, 138, Image.SCALE_SMOOTH)));
        panelImagem.add(jLabelFoto);
        System.out.println("Acabou o draw");
    }
}