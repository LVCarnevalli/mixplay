package br.com.android.mixplay;

/**
 * Classe que retorna todos os comandos ffmpeg a serem executados.
 * @author lucasv
 *
 */
public class FactoryCommand {

	/**
	 * Metodo responsavel por preparar os comandos do ffmpeg.
	 * @param movie - Video
	 * @param music - Musica
	 * @return
	 */
	public String executeMovieAddMusic(String movie, String music, String outFile) {
		String command = "";
		
		command = "ffmpeg -y"
				+ " -i " + movie
				+ " -i " + music 
				+ " -strict experimental -vcodec mpeg4 -acodec aac -t 00:00:40 -ss 00:00:45 -map 0:0 -map 1:0 -s 160x120 " 
				+ outFile;

		return command;
	}
	
	public String executeCropMusic(String music, String valorInicial, String valorFinal, String outFile) {
		String command = "";
		
		command = "ffmpeg -y"
				+ " -i " + music 
				+ " -acodec copy -t " + valorInicial + " -ss " + valorFinal + " " 
				+ outFile;

		return command;
	}
	
}
