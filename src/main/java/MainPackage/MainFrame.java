package MainPackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import MainPackage.ConvertText.ConvertType;

public class MainFrame extends JFrame implements ActionListener, View {
	
	private final static String VERSION_NUMBER = "v.0.2";
	
	public static final Logger logger = LogManager.getLogger(MainFrame.class);
	
	private static final int WIDTH = 530;

	private JPanel fileConversionContentPane;
	private JPanel textConversionContentPane;
	private JTabbedPane tabpane;
	
	// File conversion
	private JFileChooser fileChooser;
	private JTextArea fileResultTextArea;
	private JButton btn_convertFile;
	private JButton btn_fileChooser;
	private JTextArea lblFileName;
	private JButton btn_clipboard;
	private JCheckBox chckbxImageForWeb;
	
	// Text conversion
	private JButton btn_encodeText;
	private JButton btn_decodeText;
	private JButton btn_clear;
	private JTextArea textInputTextArea;
	private JTextArea textOutputTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// basic log4j configurator  
				BasicConfigurator.configure();  
				String infoText = "Start Application: Base64 Encode - Decode (" + VERSION_NUMBER +")";
				logger.info(infoText);
				
				try {
					MainFrame frame = new MainFrame();
					frame.setTitle(infoText);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		});
	}

	/**
	 * Create the main frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 450);
		
		initFileFrame();
		initTextFrame();
		
		// create TabPane
		tabpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
		tabpane.addTab("File", fileConversionContentPane);
		tabpane.addTab("Text", textConversionContentPane);
		setContentPane(tabpane);
	}
	
	private void initFileFrame() {
		fileConversionContentPane = new JPanel();
		fileConversionContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(fileConversionContentPane);
		fileConversionContentPane.setLayout(null);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBounds(0, 138, WIDTH, 245);
		fileConversionContentPane.add(resultPanel);
		resultPanel.setLayout(null);
		
		fileResultTextArea = new JTextArea();
		fileResultTextArea.setBounds(0, 0, WIDTH, 206);
		fileResultTextArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(fileResultTextArea);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(WIDTH, 205);
		resultPanel.add(scrollPane);
		
		btn_clipboard = new JButton("Copy to Clipboard");
		btn_clipboard.setBounds(353, 210, 168, 25);
		btn_clipboard.addActionListener(this);
		btn_clipboard.setEnabled(false);
		btn_clipboard.setBackground(Color.LIGHT_GRAY);
		resultPanel.add(btn_clipboard);
		
		fileChooser = new JFileChooser();
		
		JPanel inputFilePanel = new JPanel();
		inputFilePanel.setBounds(0, 0, WIDTH, 137);
		inputFilePanel.add(fileChooser);
		fileConversionContentPane.add(inputFilePanel);
		inputFilePanel.setLayout(null);
		
		btn_convertFile = new JButton("Convert to Base 64");
		btn_convertFile.setBounds(27, 99, 150, 25);
		btn_convertFile.setEnabled(false);
		btn_convertFile.addActionListener(this);
		inputFilePanel.add(btn_convertFile);
		
		btn_fileChooser = new JButton("Select file");
		btn_fileChooser.setBounds(27, 31, 114, 25);
		btn_fileChooser.addActionListener(this);
		inputFilePanel.add(btn_fileChooser);
		
		lblFileName = new JTextArea("");
		lblFileName.setBounds(153, 35, 368, 51);
		lblFileName.setLineWrap(true);
		lblFileName.setEditable(false);
		lblFileName.setBackground(getBackground());
		inputFilePanel.add(lblFileName);
		
		chckbxImageForWeb = new JCheckBox("image for web");
		chckbxImageForWeb.setBounds(186, 99, 125, 25);
		chckbxImageForWeb.setSelected(false);
		chckbxImageForWeb.addActionListener(this);
		inputFilePanel.add(chckbxImageForWeb);
	}
	
	private void initTextFrame() {
		
		int height = 155;
		textConversionContentPane = new JPanel();
		textConversionContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		textConversionContentPane.setLayout(null);
		
		JPanel inputTextPanel = new JPanel();
		inputTextPanel.setBounds(0, 0, WIDTH, height);
		textConversionContentPane.add(inputTextPanel);
		
		JPanel outputTextPanel = new JPanel();
		outputTextPanel.setBounds(0, 205, WIDTH, height);
		textConversionContentPane.add(outputTextPanel);
		inputTextPanel.setLayout(null);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(0, 165, WIDTH, 40);
		textConversionContentPane.add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		btn_encodeText = new JButton("Encode");
		btn_encodeText.setBounds(10, 3, 85, 35);
		btn_encodeText.addActionListener(this);
		buttonsPanel.add(btn_encodeText);
		
		btn_decodeText = new JButton("Decode");
		btn_decodeText.setBounds(105, 3, 85, 35);
		btn_decodeText.addActionListener(this);
		buttonsPanel.add(btn_decodeText);
		
		btn_clear = new JButton("Clear");
		btn_clear.setBounds(200, 3, 85, 35);
		btn_clear.addActionListener(this);
		buttonsPanel.add(btn_clear);
		
		textInputTextArea = new JTextArea();
		textInputTextArea.setBounds(0, 0, WIDTH, height);
		textInputTextArea.setLineWrap(true);
		JScrollPane scrollPaneInput = new JScrollPane(textInputTextArea);
		scrollPaneInput.setLocation(0, 5);
		scrollPaneInput.setSize(WIDTH, height);
		inputTextPanel.add(scrollPaneInput);
		outputTextPanel.setLayout(null);
		
		textOutputTextArea = new JTextArea();
		textOutputTextArea.setBounds(0, 0, WIDTH, height);
		textOutputTextArea.setLineWrap(true);
		JScrollPane scrollPaneOutput = new JScrollPane(textOutputTextArea);
		scrollPaneOutput.setLocation(0, 5);
		scrollPaneOutput.setSize(WIDTH, height);
		outputTextPanel.add(scrollPaneOutput);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btn_fileChooser){
			logger.info("User clicked: FileChooser");
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Choose file...");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    //
		    // disable the "All files" option.
		    //
			fileChooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      logger.info("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
		      logger.info("getSelectedFile() : " +  fileChooser.getSelectedFile());
		      
		      String fileName = ""+fileChooser.getSelectedFile();
		      lblFileName.setText(fileName);
		      
		      btn_convertFile.setEnabled(true);
		      btn_clipboard.setEnabled(false);
		      btn_clipboard.setBackground(Color.LIGHT_GRAY);
		      fileResultTextArea.setText("");
		      
		    }
		    else{
		      if(btn_convertFile.isVisible()){
		    	  btn_convertFile.setEnabled(false);
		    	  btn_clipboard.setEnabled(false);
		      }
		    }
		}else if(e.getSource() == this.btn_convertFile){
			logger.info("User clicked: Convert Button");
			File file = new File(""+fileChooser.getSelectedFile());
			  if(file.exists()){
				  ConvertFile convertion = new ConvertFile(file, chckbxImageForWeb.isSelected());
				  convertion.addView(this);
				  convertion.start();
				  btn_convertFile.setEnabled(false);
				  btn_clipboard.setBackground(Color.LIGHT_GRAY);
				  btn_clipboard.setEnabled(true);
			  }
		}else if(e.getSource() == this.btn_clipboard){
			logger.info("User clicked: Copy to clipboard Button");
			StringSelection selection = new StringSelection(fileResultTextArea.getText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			btn_clipboard.setEnabled(false);
			btn_clipboard.setBackground(Color.GREEN);
			btn_convertFile.setEnabled(true);
		}else if(e.getSource() == this.chckbxImageForWeb){
			logger.info("User clicked: checkbox Image for web ("+chckbxImageForWeb.isSelected()+")");
			if(chckbxImageForWeb.isSelected()){
				chckbxImageForWeb.setSelected(true);
			}else{
				chckbxImageForWeb.setSelected(false);
			}
		}else if(e.getSource() == this.btn_encodeText){
			logger.info("User clicked: encode text Button");
			if(!"".equals(this.textInputTextArea.getText().trim())) {
				btn_encodeText.setEnabled(false);
				btn_decodeText.setEnabled(false);
				btn_clear.setEnabled(false);
				ConvertText convertText = new ConvertText(textInputTextArea.getText().trim(), ConvertType.Encode);
				convertText.addView(this);
				convertText.start();
			}
		}else if(e.getSource() == this.btn_decodeText){
			logger.info("User clicked: decode text Button");
			if(!"".equals(this.textInputTextArea.getText().trim())) {
				btn_encodeText.setEnabled(false);
				btn_decodeText.setEnabled(false);
				btn_clear.setEnabled(false);
				ConvertText convertText = new ConvertText(textInputTextArea.getText().trim(), ConvertType.Decode);
				convertText.addView(this);
				convertText.start();
			}
		}else if(e.getSource() == this.btn_clear){
			logger.info("User clicked: clear Button");
			textInputTextArea.setText("");
			textOutputTextArea.setText("");
		}
	}

	@Override
	public void updateResult(String result, TabIndex tabIndex) {
		if(tabIndex == TabIndex.File) {
			fileResultTextArea.setText(result);
			btn_convertFile.setEnabled(true);
		}else if(tabIndex == TabIndex.Text) {
			textOutputTextArea.setText(result);
			btn_encodeText.setEnabled(true);
			btn_decodeText.setEnabled(true);
			btn_clear.setEnabled(true);
		}
	}

	@Override
	public void setFinishedStatus(boolean status) {
		// TODO Auto-generated method stub
	}
}
