package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import dao.ControleSatelite;

public class GUIGrafico extends JFrame {

	private JPanel contentPane;
	private JTextField TextAreaChamas;
	private JTextField TextAumento;
	private JTextField TextData;
	private ControleSatelite controle;
	private JComboBox comboRegiao = new JComboBox();

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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 74, 202, 138);
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Nome Região: ");
		lblNewLabel.setBounds(23, 25, 125, 15);
		panel.add(lblNewLabel);
		
		comboRegiao.setModel(new DefaultComboBoxModel(new String[] {"Sem região"}));
		comboRegiao.setBounds(280, 20, 158, 24);
		panel.add(comboRegiao);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnVoltar.setBounds(33, 263, 117, 25);
		panel.add(btnVoltar);
		
		JButton btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGerar.setBounds(298, 263, 117, 25);
		panel.add(btnGerar);
		
		JLabel lblDerea = new JLabel("% de área em chamas");
		lblDerea.setBounds(261, 74, 154, 15);
		panel.add(lblDerea);
		
		TextAreaChamas = new JTextField();
		TextAreaChamas.setBounds(280, 101, 114, 19);
		panel.add(TextAreaChamas);
		TextAreaChamas.setColumns(10);
		
		JLabel lblDoAumento = new JLabel("% de aumento ");
		lblDoAumento.setBounds(280, 132, 135, 15);
		panel.add(lblDoAumento);
		
		TextAumento = new JTextField();
		TextAumento.setBounds(280, 159, 114, 19);
		panel.add(TextAumento);
		TextAumento.setColumns(10);
		
		TextData = new JTextField();
		TextData.setBounds(52, 214, 114, 19);
		panel.add(TextData);
		TextData.setColumns(10);
	}
}
