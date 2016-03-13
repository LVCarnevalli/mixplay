package br.com.android.mixplay.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Galeria android.
 * @author lucasv
 *
 */
public class Gallery extends Activity {

    /** TODAS AS IMAGENS */
	public static final String TYPE_IMAGE = "image/*";
    /** TODOS OS VIDEOS */
    public static final String TYPE_MOVIE = "video/*";
    /** TODAS AS MÚSICAS */
    public static final String TYPE_MUSIC = "audio/*";
    
    /** TIPO DA GALERIA */
    public static final String TYPE = "type";
    /** TITULO DA GALERIA */
    public static final String TITLE = "title";
    
    /** PATH DO ARQUIVO SELECIONADO */
    public static final String PATHFILE = "pathFile";
    
    /** CONTROLE DA SELEÇÃO DE ARQUIVO */
    protected final int ACTIVITY_CHOOSE_FILE = 1;
        
    /**
     * Metodo responsavel por receber os parametros e chamar o execute.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	// Obter os parametros.
    	Intent intent = getIntent();
    	Bundle params = intent.getExtras();  
		if (params != null) {
			// Tipo da galeria.
			String type = params.getString(TYPE);
			// Titulo da galeria.
	    	String title = params.getString(TITLE);
	    	// Abrir a galeria.
	    	execute(type, title);
		}	
    }

    /**
     * Metodo responsavel por abrir a galeria e possibilitar a seleção de arquivos.
     * @param type - Tipo dos arquivos a serem selecionados.
     * @param title - Titulo do dialogo apresentado na tela.
     */
    protected void execute(String type, String title) {
		Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
		chooseFile.setType(type);
		chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(Intent.createChooser(chooseFile, title),	ACTIVITY_CHOOSE_FILE);
    }
    
    /**
     * Metodo de retorno do Activity, define o path do arquivo.
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		  case ACTIVITY_CHOOSE_FILE: {
			if (resultCode == RESULT_OK) {
				String filename = parseUriToFilename(data.getData());
				if (filename != null) {
					Intent intent = new Intent();
					Bundle params = new Bundle();
					params.putString(PATHFILE, filename);
					intent.putExtras(params);
					setResult(0, intent);
					finish();
				}
			} else {
				Intent intent = new Intent();
				Bundle params = new Bundle();
				params.putString(PATHFILE, "");
				intent.putExtras(params);
				setResult(0, intent);
				finish();
			}
			break;
		  }
		}
	}

    /**
     * Metodo responsavel por converter o path do arquivo selecionado.
     * @param uri - Path do arquivo selecionado.
     * @return - Retorna o path do arquivo formatado.
     */
	protected String parseUriToFilename(Uri uri) {
		String selectedImagePath = null;
		String filemanagerPath = uri.getPath();

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);

		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		}

		if (selectedImagePath != null) {
			return selectedImagePath;
		} else if (filemanagerPath != null) {
			return filemanagerPath;
		}
		return null;
	}

}
