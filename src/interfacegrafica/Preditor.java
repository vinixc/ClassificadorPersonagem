package interfacegrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import opencv.ExtratorImagem;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Preditor extends JFrame {
	private static final long serialVersionUID = 7589854523863730564L;
	
	private JPanel contentPane;
	private JTextField txtCaminhoImagem;

	private JLabel lblLaranjaBart;
	private JLabel lblAzulCalcaoBart;
	private JLabel lblAzulSapatoBart;
	private JLabel lblAzulHomer;
	private JLabel lblMarromHomer;
	private JLabel lblSapatoHomer;
	
	private Instances instancias;

	private JLabel lblNaiveBayesBart;

	private JLabel lblNaiveBayesHomer;

	private JLabel lblJ48Bart;

	private JLabel lblJ48Homer;

	
	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Preditor frame = new Preditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Instance criaNovoRegistro() {
		//criacao de novo registro
		Instance novo = new DenseInstance(instancias.numAttributes());
		novo.setDataset(instancias);
		
		novo.setValue(0, Float.parseFloat(lblLaranjaBart.getText()));
		novo.setValue(1, Float.parseFloat(lblAzulCalcaoBart.getText()));
		novo.setValue(2, Float.parseFloat(lblAzulSapatoBart.getText()));
		novo.setValue(3, Float.parseFloat(lblMarromHomer.getText()));
		novo.setValue(4, Float.parseFloat(lblAzulHomer.getText()));
		novo.setValue(5, Float.parseFloat(lblSapatoHomer.getText()));
		return novo;
	}
	
	public void classificaNaiveBayes() {
		NaiveBayes nb = new NaiveBayes();
		
		
		try {
			//criacao da tabela de probabilidade;
			nb.buildClassifier(instancias);
			
			Instance novo = criaNovoRegistro();
			
			//previsao
			
			double resultado[] = nb.distributionForInstance(novo);
			DecimalFormat df = new DecimalFormat("#,###.0000");
			
			lblNaiveBayesBart.setText("Bart: " + df.format(resultado[0]));
			lblNaiveBayesHomer.setText("Homer: " + df.format(resultado[1]));
			
		} catch (Exception e) {
			Logger.getLogger(Preditor.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
	}

	public void classificaJ48() throws Exception {
		J48 arvore = new J48();
		
		//criando arvore de decisao
		arvore.buildClassifier(instancias);
		Instance novo = criaNovoRegistro();
		
		double resultado[] = arvore.distributionForInstance(novo);
		DecimalFormat df = new DecimalFormat("#,###.0000");
		
		lblJ48Bart.setText("Bart: " + df.format(resultado[0]));
		lblJ48Homer.setText("Homer: " + df.format(resultado[1]));
		
	}
	
	public void carregaBaseWeka() throws Exception {
		DataSource ds = new DataSource("src\\arff\\caracteristicas.arff");
		instancias = ds.getDataSet();
		instancias.setClassIndex(instancias.numAttributes() -1);
		
	}
	
	/**
	 * Create the frame.
	 */
	public Preditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 587);
		contentPane = new JPanel();
		contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdicionaImagem = new JButton("Selecionar Imagem");
		
		
		btnAdicionaImagem.setActionCommand("btnSelecionarImagemActionPerformed");
		btnAdicionaImagem.setBounds(33, 22, 223, 36);
		btnAdicionaImagem.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnAdicionaImagem);
		
		txtCaminhoImagem = new JTextField();
		txtCaminhoImagem.setBounds(282, 22, 438, 36);
		contentPane.add(txtCaminhoImagem);
		txtCaminhoImagem.setColumns(10);
		
		JLabel lblImagem = new JLabel("");
		lblImagem.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImagem.setBounds(33, 107, 348, 432);
		contentPane.add(lblImagem);
		
		JButton btnExtrairCaracteristicas = new JButton("Extrair Caracterisricas");
		btnExtrairCaracteristicas.setBounds(406, 107, 157, 23);
		contentPane.add(btnExtrairCaracteristicas);
		
		JLabel lblCaracteristicasBart = new JLabel("Caracteristicas do Bart");
		lblCaracteristicasBart.setBounds(391, 155, 172, 14);
		contentPane.add(lblCaracteristicasBart);
		
		JLabel lblCaracteristicasHomer = new JLabel("Caracteristicas do Homer");
		lblCaracteristicasHomer.setBounds(577, 155, 172, 14);
		contentPane.add(lblCaracteristicasHomer);
		
		lblLaranjaBart = new JLabel("");
		lblLaranjaBart.setBounds(405, 199, 90, 14);
		contentPane.add(lblLaranjaBart);
		
		lblAzulCalcaoBart = new JLabel("");
		lblAzulCalcaoBart.setBounds(405, 224, 90, 14);
		contentPane.add(lblAzulCalcaoBart);
		
		lblAzulSapatoBart = new JLabel("");
		lblAzulSapatoBart.setBounds(405, 257, 90, 14);
		contentPane.add(lblAzulSapatoBart);
		
		lblAzulHomer = new JLabel("");
		lblAzulHomer.setBounds(594, 199, 90, 14);
		contentPane.add(lblAzulHomer);
		
		lblMarromHomer = new JLabel("");
		lblMarromHomer.setBounds(594, 224, 90, 14);
		contentPane.add(lblMarromHomer);
		
		lblSapatoHomer = new JLabel("");
		lblSapatoHomer.setBounds(594, 257, 90, 14);
		contentPane.add(lblSapatoHomer);
		
		JButton btnClassificar = new JButton("Classificar");
		btnClassificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					carregaBaseWeka();
					classificaNaiveBayes();
					classificaJ48();
				} catch (Exception e1) {
					Logger.getLogger(Preditor.class.getName()).log(Level.SEVERE, null, e1);
				}
			}
		});
		btnClassificar.setBounds(584, 107, 136, 23);
		contentPane.add(btnClassificar);
		
		JLabel lblNaiveBayes = new JLabel("Naive Bayes");
		lblNaiveBayes.setBounds(393, 374, 136, 14);
		contentPane.add(lblNaiveBayes);
		
		lblNaiveBayesBart = new JLabel("");
		lblNaiveBayesBart.setBounds(391, 413, 124, 14);
		contentPane.add(lblNaiveBayesBart);
		
		lblNaiveBayesHomer = new JLabel("");
		lblNaiveBayesHomer.setBounds(391, 438, 104, 14);
		contentPane.add(lblNaiveBayesHomer);
		
		JLabel lblNewLabel = new JLabel("J48");
		lblNewLabel.setBounds(560, 374, 96, 14);
		contentPane.add(lblNewLabel);
		
		lblJ48Bart = new JLabel("..");
		lblJ48Bart.setBounds(560, 413, 96, 14);
		contentPane.add(lblJ48Bart);
		
		lblJ48Homer = new JLabel("..");
		lblJ48Homer.setBounds(560, 438, 90, 14);
		contentPane.add(lblJ48Homer);
		
		btnAdicionaImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int retorno = fc.showDialog(contentPane, "Selecione a imagem");
				
				if(retorno == JFileChooser.APPROVE_OPTION) {
					File arquivo = fc.getSelectedFile();
					txtCaminhoImagem.setText(arquivo.getAbsolutePath());
					
					try {
						BufferedImage imageBmp = ImageIO.read(arquivo);
						
						ImageIcon imagemLabel = new ImageIcon(imageBmp);
						
						lblImagem.setIcon(
								new ImageIcon(
										imagemLabel.getImage().getScaledInstance(
												lblImagem.getWidth(),
												lblImagem.getHeight(),
												Image.SCALE_DEFAULT)));
						
					} catch (IOException e1) {
						Logger.getLogger(Preditor.class.getName()).log(Level.SEVERE, null, e1);
					}
					
				}
			}
		});
		
		btnExtrairCaracteristicas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtratorImagem extrator = new ExtratorImagem();
				float[] caracteristicas = extrator.extrairCaracteristicasImagem(txtCaminhoImagem.getText());
				
				lblLaranjaBart.setText(String.valueOf(caracteristicas[0]));
				lblAzulCalcaoBart.setText(String.valueOf(caracteristicas[1]));
				lblAzulSapatoBart.setText(String.valueOf(caracteristicas[2]));
				lblAzulHomer.setText(String.valueOf(caracteristicas[3]));
				lblMarromHomer.setText(String.valueOf(caracteristicas[4]));
				lblSapatoHomer.setText(String.valueOf(caracteristicas[5]));
			}
		});
	}
	
	public void showComponents(Container container) {
	    Component[] components = container.getComponents();

	    for (Component c : components) {
	        System.out.println(c);
	        System.out.println();
	    }
	}
}
