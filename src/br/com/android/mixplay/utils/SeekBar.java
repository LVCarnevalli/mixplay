package br.com.android.mixplay.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import br.com.android.mixplay.R;
import br.com.android.mixplay.utils.SeekBarWithTwoThumb.SeekBarChangeListener;

public class SeekBar extends Activity implements SeekBarChangeListener {

	public static TextView textInicSeekBar, textFimSeekBar;
	private SeekBarWithTwoThumb seekBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_principal);
		textInicSeekBar = (TextView) findViewById(R.id.textView1);
		textFimSeekBar = (TextView) findViewById(R.id.textView2);
		seekBar = (SeekBarWithTwoThumb) findViewById(R.id.myseekbar);
		seekBar.setSeekBarChangeListener(this);
		seekBar.setSeekBarRangeSize(20);
		seekBar.setSeekBarTimeSize(100);		
	}

	public void SeekBarValueChanged(int Thumb1Value, int Thumb2Value) {
		textInicSeekBar.setText("De: " + Utils.getDurationString(Thumb1Value));
		textFimSeekBar.setText("Até: " + Utils.getDurationString(Thumb2Value));
	}
	
}
