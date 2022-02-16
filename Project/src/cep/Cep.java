package cep;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

@SuppressWarnings("serial")
public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField txtCep;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblNewLabel_4;
	private JComboBox cboUf;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setTitle("Buscar CEP");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setBounds(10, 24, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtCep = new JTextField();
		txtCep.setBounds(71, 21, 161, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Endere\u00E7o");
		lblNewLabel_1.setBounds(10, 62, 75, 20);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setBounds(10, 104, 75, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setBounds(10, 139, 75, 14);
		contentPane.add(lblNewLabel_3);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(71, 62, 353, 20);
		contentPane.add(txtEndereco);
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(71, 101, 353, 20);
		contentPane.add(txtBairro);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(71, 136, 244, 20);
		contentPane.add(txtCidade);
		
		lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setBounds(325, 139, 30, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					txtCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnBuscar.setBounds(266, 20, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(10, 174, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnNewButton_2.setToolTipText("Sobre");
		btnNewButton_2.setIcon(new ImageIcon(Cep.class.getResource("/img/about.png")));
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setBorder(null);
		btnNewButton_2.setBackground(SystemColor.control);
		btnNewButton_2.setBounds(376, 7, 48, 48);
		contentPane.add(btnNewButton_2);
		/**
		 * Uso da biblioteca Atxy2k para validação do campo txtCep
		 */
		RestrictedTextField validar = new RestrictedTextField(txtCep);
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {" ", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(365, 135, 59, 22);
		contentPane.add(cboUf);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(240, 21, 20, 20);
		contentPane.add(lblStatus);
		validar.setOnlyNums(true);
		validar.setLimit(8);
	}//fim do construtor
	
	private void buscarCep() {
		String logradouro = null;
		String tipoLogradouro = null;
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				
				if(element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if(element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if(element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if(element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if(element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if(tipoLogradouro != null) {
					if(logradouro != null) {
						txtEndereco.setText(tipoLogradouro+" "+logradouro);
					}
				}
				
				if(element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if(!resultado.equals("0")) {
						
						lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					}else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
						limparCampos();
					}
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void limparCampos() {
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(" ");
		lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
	}
}
