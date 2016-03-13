package br.com.android.mixplay.utils;

/**
 * Utilidades do aplicativo.
 * @author lucasv
 *
 */
public class Utils {

	/**
	 * M�todo que retorna o nome do arquivo de sa�da.
	 * @return
	 */
	public static String getNameOutFile() {
		return "out" + "." + Constants.MP4;
	}
	
	public static String getDurationString(int seconds) {
	    int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    seconds = seconds % 60;
	    return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
	}

	public static String twoDigitString(int number) {
	    if (number == 0) {
	        return "00";
	    }
	    if (number / 10 == 0) {
	        return "0" + number;
	    }
	    return String.valueOf(number);
	}
	
}
