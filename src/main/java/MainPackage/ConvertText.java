package MainPackage;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class ConvertText extends Thread{
	
	public enum ConvertType{
		Encode,
		Decode;
	}
	
	public static final Logger logger = LogManager.getLogger(ConvertText.class);
	private MainFrame mf;
	private String input, output;
	private ConvertType convertType;
	
	public ConvertText(String input, ConvertType convertType) {
		this.input = input;
		this.convertType = convertType;
	}
	
	@Override
	public void run() {
		startConvertion();
	}
	
	private void startConvertion() {
		
		try {
			byte[] bytes = new byte[0];
			if(convertType == ConvertType.Encode) {
				bytes = Base64.getEncoder().encode(input.getBytes());
			}else if(convertType == ConvertType.Decode) {
				bytes = Base64.getDecoder().decode(input.getBytes());
			}
			
			output = new String(bytes, StandardCharsets.UTF_8);
			notify(output);
			logger.info("Text result: "+output);
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
	
	public void addView(MainFrame mf){
		this.mf = mf;
	}
	
	public void notify(String result){
		if(mf != null){
			mf.updateResult(result, TabIndex.Text);
		}
	}

}
