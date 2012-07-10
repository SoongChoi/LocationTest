package com.kth.marty;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.content.Context;
import android.widget.Toast;

public class LocationLogger {
	
	Context context 			= null;
	File	mLogFilePath 		= null;
	BufferedOutputStream out	= null;
	
	public LocationLogger(Context con){
		context = con;
		File base_dir = null;
		
/**********
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(context, 
					String.format("%s will be used for logfiles.", 
							context.getExternalFilesDir(null).toString()), 
							Toast.LENGTH_LONG).show();
			base_dir = context.getExternalFilesDir(null);
		}
		else {
****/
			Toast.makeText(context, 
					String.format("%s will be used for logfiles.", 
							context.getFilesDir().toString()), 
							Toast.LENGTH_LONG).show();
			base_dir = context.getFilesDir();
/****
	}
****/
		if(!base_dir.exists()) {
			base_dir.mkdirs();
			Toast.makeText(context, "Directory Created!!  " + base_dir.toString(), Toast.LENGTH_SHORT).show();
		}
		mLogFilePath = new File(base_dir.toString() +"/"+ makeDateTimeFileName());
	}

	private String makeDateTimeFileName()
    {
		Calendar cal = Calendar.getInstance();
		
    	return String.format("%04d%02d%02d_%02d%02d.txt",
    			cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE),
    			cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
    }
    
	private String makeTimeString()
    {
		Calendar cal = Calendar.getInstance();
		
    	return String.format("%02d:%02d:%02d",
    			cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }
    
    public boolean logSystemInfo(String log_info)
    {
    	String log_str = String.format("----,%s,%s,,,", makeTimeString(), log_info);
    	return logInformation(log_str);
    }
	
	public boolean logInformation(String info_str)
	{
		try {
			if(out == null) {
				try {
					out = new BufferedOutputStream(new FileOutputStream(mLogFilePath, true));
				}
				catch(FileNotFoundException e1){
					// Heading Information
					out.write("Counter,Time,Provider,Latitue,Lontitude,distance,Accurracy,Altitude,Bearing,Speed\n".getBytes());
				}
			}
			out.write(info_str.getBytes());
		}
		catch (IOException e) {
			Toast.makeText(context, "logInformation() Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
	public void loggerClose()
	{
		try {
			if(out != null) out.close();
			out = null;
		}
		catch (IOException e) {}
	}
	
	
}
