package br.com.android.mixplay.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import br.com.android.mixplay.R;

/**
 * Alertas do aplicativo.
 * @author lucasv
 *
 */
public class Alerts {

	@SuppressWarnings("deprecation")
	public static void exibirAlertaOk(Context context, String titulo, String mensagem) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensagem);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();
    }
	
}
