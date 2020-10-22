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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MainFrame extends JFrame implements ActionListener, View {
	
	private final static String VERSION_NUMBER = "v.0.2";
	
	public static final Logger logger = LogManager.getLogger(MainFrame.class);  

	private JPanel contentPane;
	private JFileChooser fileChooser;
	private JTextArea textArea;
	private JButton btn_convert;
	private JButton btn_fileChooser;
	private JTextArea lblFileName;
	private JButton btn_clipboard;
	private JCheckBox chckbxImageForWeb;

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
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBounds(0, 138, 530, 245);
		contentPane.add(resultPanel);
		resultPanel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(0, 0, 530, 206);
		textArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(530, 205);
		resultPanel.add(scrollPane);
		
		btn_clipboard = new JButton("Copy to Clipboard");
		btn_clipboard.setBounds(353, 210, 168, 25);
		btn_clipboard.addActionListener(this);
		btn_clipboard.setEnabled(false);
		btn_clipboard.setBackground(Color.LIGHT_GRAY);
		resultPanel.add(btn_clipboard);
		
		fileChooser = new JFileChooser();
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 0, 533, 137);
		inputPanel.add(fileChooser);
		contentPane.add(inputPanel);
		inputPanel.setLayout(null);
		
		btn_convert = new JButton("Convert to Base 64");
		btn_convert.setBounds(27, 99, 141, 25);
		btn_convert.setEnabled(false);
		btn_convert.addActionListener(this);
		inputPanel.add(btn_convert);
		
		btn_fileChooser = new JButton("Select file");
		btn_fileChooser.setBounds(27, 31, 114, 25);
		btn_fileChooser.addActionListener(this);
		inputPanel.add(btn_fileChooser);
		
		lblFileName = new JTextArea("");
		lblFileName.setBounds(153, 35, 368, 51);
		lblFileName.setLineWrap(true);
		lblFileName.setEditable(false);
		lblFileName.setBackground(getBackground());
		inputPanel.add(lblFileName);
		
		chckbxImageForWeb = new JCheckBox("image for web");
		chckbxImageForWeb.setBounds(176, 99, 125, 25);
		chckbxImageForWeb.setSelected(false);
		chckbxImageForWeb.addActionListener(this);
		inputPanel.add(chckbxImageForWeb);
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
		      
		      btn_convert.setEnabled(true);
		      btn_clipboard.setEnabled(false);
		      btn_clipboard.setBackground(Color.LIGHT_GRAY);
		      textArea.setText("");
		      
		    }
		    else{
		      if(btn_convert.isVisible()){
		    	  btn_convert.setEnabled(false);
		    	  btn_clipboard.setEnabled(false);
		      }
		    }
		}else if(e.getSource() == this.btn_convert){
			logger.info("User clicked: Convert Button");
			File file = new File(""+fileChooser.getSelectedFile());
			  if(file.exists()){
				  Convert convertion = new Convert(file, chckbxImageForWeb.isSelected());
				  convertion.addView(this);
				  convertion.start();
				  btn_convert.setEnabled(false);
				  btn_clipboard.setBackground(Color.LIGHT_GRAY);
				  btn_clipboard.setEnabled(true);
			  }
		}else if(e.getSource() == this.btn_clipboard){
			logger.info("User clicked: Copy to clipboard Button");
			StringSelection selection = new StringSelection(textArea.getText());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			btn_clipboard.setEnabled(false);
			btn_clipboard.setBackground(Color.GREEN);
			btn_convert.setEnabled(true);
		}else if(e.getSource() == this.chckbxImageForWeb){
			logger.info("User clicked: checkbox Image for web ("+chckbxImageForWeb.isSelected()+")");
			if(chckbxImageForWeb.isSelected()){
				chckbxImageForWeb.setSelected(true);
			}else{
				chckbxImageForWeb.setSelected(false);
			}
		}
	}

	@Override
	public void updateResult(String result) {
		textArea.setText(result);
		btn_convert.setEnabled(true);
	}

	@Override
	public void setFinishedStatus(boolean status) {
		// TODO Auto-generated method stub
	}
}
