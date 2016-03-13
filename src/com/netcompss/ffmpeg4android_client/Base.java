package com.netcompss.ffmpeg4android_client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import br.com.android.mixplay.R;

public class Base extends Activity {
	
	protected static final int DIALOG_ABOUT = 0;
	protected static final int DIALOG_CONTACT = 1;
	protected static final int MENU_CONTACT = 2;
	protected static final int MENU_ABOUT = 4;
	protected static final int MENU_PREFS = 5;
	protected static final int MENU_CUT = 6;
	
	
	protected static final int HELP_CROP_DIALOG = 11;
	protected static final int CROP_MENU_HELP = 12;
	
	protected static final int HELP_SEG_DIALOG = 13;
	protected static final int SEG_MENU_HELP = 14;
	
	protected static final int HELP_RES_DIALOG = 15;
	protected static final int RES_MENU_HELP = 16;
	
	protected static final int HELP_EXTRACT_SOUND_DIALOG = 17;
	protected static final int EXTRACT_SOUND_MENU_HELP = 18;
	
	protected static final int HELP_EXTRACT_PIC_DIALOG = 19;
	protected static final int EXTRACT_PIC_MENU_HELP = 20;
	
	protected static final int HELP_TRANS_AUDIO_DIALOG = 21;
	protected static final int TRANS_AUDIO_MENU_HELP = 22;
	
	protected static final int HELP_COMPRESS_DIALOG = 23;
	protected static final int COMPRESS_MENU_HELP = 24;
	
	protected static final int MENU_SHOW_FILE_INFO = 33;
	protected static final int FILE_INFO_DIALOG = 34;
	
	protected static final int MENU_SHOW_LOGS = 35;
	
	protected static final int FILE_ERROR_DIALOG = 100;
	
	protected static final int MENU_FORCE_STOP_TRANS = 150;
		
	private String fileErrorMessage = "File not found, please correct.";
	
	public String getFileErrorMessage() {
		return fileErrorMessage;
	}


	public void setFileErrorMessage(String fileErrorMessage) {
		this.fileErrorMessage = fileErrorMessage;
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		Builder builder;
		AlertDialog dialog = null;
		switch (id) {
		
		
         
            
		case DIALOG_ABOUT:
			builder = new AlertDialog.Builder(this);
			builder.setCustomTitle(null);
			builder.setPositiveButton(R.string.ui_ok, null);

			View contentView = getLayoutInflater().inflate(R.layout.layout_sobre, null, false);
			WebView webView = (WebView) contentView.findViewById(R.id.about_content);
			webView.loadData(getAboutText(), "text/html", "utf-8");
			builder.setView(contentView);

			Dialog d = builder.create();
			return d;
					
		case FILE_ERROR_DIALOG:
			 builder = new AlertDialog.Builder(this);
			 builder.setMessage(getFileErrorMessage())
			        .setCancelable(false)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle(getString(R.string.dialog_error_title))
			        .setPositiveButton(R.string.ui_ok, null);
			       
			 AlertDialog alert = builder.create();
			 return alert;
            
		}
		
		
		return dialog;
    }
	
	
	protected String getAboutText() {
		try {
			InputStream input = getResources().getAssets().open("sobre.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer buf = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buf.append(line);
			}
			String aboutText = buf.toString();
			aboutText = String.format(aboutText, getString(R.string.app_name), Prefs.getVersionName(getApplicationContext()));
			
			return aboutText;
		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			return "An error occured while reading about.html";
		}

	}

}