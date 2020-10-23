package MainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConvertFile extends Thread{
	
	public static final Logger logger = LogManager.getLogger(ConvertFile.class);
	
	private File imageFile;
	private String result;
	private MainFrame mf;
	private boolean imageForWeb;
	
	public ConvertFile(File imageFile, boolean imageForWeb) {
		this.imageFile = imageFile;
		this.imageForWeb = imageForWeb;
	}

	@Override
	public void run() {
		startConvertion();
	}
	
	private void startConvertion(){
		String base64="";
	    try (InputStream iSteamReader = new FileInputStream(imageFile);){
	        byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
	        base64 = Base64.getEncoder().encodeToString(imageBytes);
	        logger.info("Convert Result is: "+base64);
	        this.result = base64;
	        if(this.imageForWeb){
	        	notify("data:image/png;base64,"+ this.result);
	        }else{
	        	notify(this.result);
	        }
	    }catch(Exception e){
	    	logger.error(e);
	    }
	}
	
	public void addView(MainFrame mf){
		this.mf = mf;
	}
	
	public void notify(String result){
		if(mf != null){
			mf.updateResult(result, TabIndex.File);
		}
	}
}
