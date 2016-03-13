package br.com.android.mixplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.android.mixplay.utils.Constants;
import br.com.android.mixplay.utils.Gallery;
import br.com.android.mixplay.utils.Utils;

import com.netcompss.ffmpeg4android_client.BaseWizard;
import com.netcompss.ffmpeg4android_client.Prefs;

/**
 * 
 * @author lucasv
 *
 */
public class MixPlay extends BaseWizard {
	
	private String pathVideoSelecionado;
	private String pathAudioSelecionado;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createFilesSystem();
		// Verifica que o processamento do arquivo está sendo executado, portanto é necessário esperar finalizar.
		if (Prefs.transcodingIsRunning) {
			Toast.makeText(this, getString(R.string.trascoding_running_background_message),	Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		setContentView(R.layout.layout_principal);
		
		Button invokeSelecionarVideo = (Button) findViewById(R.id.selecionarVideoButton);
		invokeSelecionarVideo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selecionarArquivo(Gallery.TYPE_MOVIE,"");			
			}
		});
		
		Button invokeSelecionarAudio = (Button) findViewById(R.id.selecionarAudioButton);
		invokeSelecionarAudio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				selecionarArquivo(Gallery.TYPE_MUSIC,"");
			}
		});
		
		Button invokeSalvar = (Button) findViewById(R.id.salvarButton);
		invokeSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				processarArquivo();
			}
		});
		
	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);       
        if (data != null && requestCode == 0){      
        	Bundle params = data.getExtras();  
            String nomeArquivo = params.getString(Gallery.PATHFILE);
            if(nomeArquivo != null && nomeArquivo.length() > 0) {
            	setPathArquivoSelecionado(nomeArquivo); 
            }                      
        }
    }
    
    public void setPathArquivoSelecionado(String nomeArquivo) {
    	if(nomeArquivo != null) {
    		String tipoArquivo = nomeArquivo.substring(nomeArquivo.length() - Constants.TAMANHO_EXTENSAO_ARQUIVO, nomeArquivo.length());
    		if(tipoArquivo.equals(Constants.MP4) || tipoArquivo.equals(Constants.MKV) || tipoArquivo.equals(Constants.AVI)) {
    			this.pathVideoSelecionado = nomeArquivo;
    		} else if (tipoArquivo.equals(Constants.MP3) || tipoArquivo.equals(Constants.WMA)) {
    			this.pathAudioSelecionado = nomeArquivo;
    		}
    	}
    }
    
    public void selecionarArquivo(String tipo, String titulo) {
    	Intent intent = new Intent(MixPlay.this, Gallery.class);
		Bundle params = new Bundle();
		params.putString(Gallery.TYPE, tipo);
		params.putString(Gallery.TITLE, titulo);
		intent.putExtras(params);
		startActivityForResult(intent, 0);
    }

    /**
     * Metodo responsavel por executar os processamentos necessarios no arquivo.
     */
	public void processarArquivo() {
		// Nome do arquivo de saída.
		String nomeArquivo = Prefs.getWorkFolder() + Utils.getNameOutFile();
		
		// Comando ffmpeg a ser executado.	
		FactoryCommand factory = new FactoryCommand();
		String[] arrayCommand = new String[2];
		arrayCommand[0] = factory.executeCropMusic(this.getPathAudioSelecionado(), "00:00:30",  "00:00:40", "/sdcard/teste.mp3");
		arrayCommand[1] = factory.executeMovieAddMusic(this.getPathVideoSelecionado(), "/sdcard/teste.mp3", nomeArquivo);
		setCommand(arrayCommand);
		runTranscoing();
	
		// Arquivo de saída.
		setOutputFilePath(nomeArquivo);
		// Processamento em andamento, mensagem do popup.
		setProgressDialogTitle("Processando...");
		setProgressDialogMessage("Aguarde o processamento do vídeo.");
		// Icone da notificação.
		setNotificationIcon(R.drawable.notification_icon);
		// Processamento em andamento.
		setNotificationTitle("MIXplay");
		setNotificationMessage("Aguarde, processando...");
		// Processamento finalizado.
		setNotificationfinishedMessageTitle("Vídeo concluído.");
		setNotificationfinishedMessageDesc("Play");
		// Processamento parou.
		setNotificationStoppedMessage("Processamento interrompido.");
		// Executa o processamento.
		runTranscoing();
	}
	
	/**
	 * @return the pathVideoSelecionado
	 */
	public String getPathVideoSelecionado() {
		if(pathVideoSelecionado != null) {
			pathVideoSelecionado = pathVideoSelecionado.replaceAll("/mnt", "");
		}
		return pathVideoSelecionado;
	}

	/**
	 * @param pathVideoSelecionado the pathVideoSelecionado to set
	 */
	public void setPathVideoSelecionado(String pathVideoSelecionado) {
		this.pathVideoSelecionado = pathVideoSelecionado;
	}

	/**
	 * @return the pathAudioSelecionado
	 */
	public String getPathAudioSelecionado() {
		if(pathAudioSelecionado != null) {
			pathAudioSelecionado = pathAudioSelecionado.replaceAll("/mnt", "");
		}
		return pathAudioSelecionado;
	}

	/**
	 * @param pathAudioSelecionado the pathAudioSelecionado to set
	 */
	public void setPathAudioSelecionado(String pathAudioSelecionado) {
		this.pathAudioSelecionado = pathAudioSelecionado;
	}

}
