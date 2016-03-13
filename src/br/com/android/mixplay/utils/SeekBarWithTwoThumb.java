package br.com.android.mixplay.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import br.com.android.mixplay.R;

public class SeekBarWithTwoThumb extends ImageView {

	private Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.leftthumb);
	private int thumb1X, thumb2X;
	private int thumb1Value, thumb2Value;
	private int thumbY;
	private Paint paint = new Paint();
	private int selectedThumb;
	private int thumbHalfWidth;
	private SeekBarChangeListener scl;
	private int rangeSize;
	private int timeSize;
	
	public interface SeekBarChangeListener {
		void SeekBarValueChanged(int Thumb1Value, int Thumb2Value);
	}

	public SeekBarWithTwoThumb(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SeekBarWithTwoThumb(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SeekBarWithTwoThumb(Context context) {		
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getHeight() > 0) {
			init();
		}			
	}

	private void init() {
		if (thumb.getHeight() > getHeight()) {
			getLayoutParams().height = thumb.getHeight();
		}

		thumbY = (getHeight() / 2) - (thumb.getHeight() / 2);

		thumbHalfWidth = thumb.getWidth() / 2;
		thumb1X = thumbHalfWidth;
		thumb2X = returnCalculateThumbValue(rangeSize);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(thumb, thumb1X - thumbHalfWidth, thumbY, paint);
		canvas.drawBitmap(thumb, thumb2X - thumbHalfWidth, thumbY, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int thumb1XOLD = thumb1X;
		int thumb2XOLD = thumb2X;
		
		int mx = (int) event.getX();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mx >= thumb1X - thumbHalfWidth && mx <= thumb1X + thumbHalfWidth) {
				selectedThumb = 1;
			} else if (mx >= thumb2X - thumbHalfWidth && mx <= thumb2X + thumbHalfWidth) {
				selectedThumb = 2;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (selectedThumb == 1) {
				thumb1X = mx;
			} else if (selectedThumb == 2) {
				thumb2X = mx;
			}
			break;
		case MotionEvent.ACTION_UP:
			selectedThumb = 0;
			break;
		}

		if (thumb1X != thumb1XOLD) {
			thumb2X = returnCalculateThumbX(returnCalculateThumbValue(thumb1X) + rangeSize);
		} else if (thumb2X != thumb2XOLD) {
			thumb1X = returnCalculateThumbX(returnCalculateThumbValue(thumb2X) - rangeSize);
		}

		if (thumb1X < 0) {
			thumb1X = 0;
			thumb2X = returnCalculateThumbX(0 + rangeSize);
		}

		if (thumb2X > getWidth()) {
			thumb2X = getWidth();
			thumb1X = returnCalculateThumbX(this.timeSize - rangeSize);
		}

		invalidate();
		if (scl != null) {
			calculateThumbValue();
			scl.SeekBarValueChanged(thumb1Value, thumb2Value); 
		}
		return true;
	}

	private void calculateThumbValue() {
		thumb1Value = (this.timeSize * (thumb1X)) / (getWidth());
		thumb2Value = (this.timeSize * (thumb2X)) / (getWidth());
	}
	
	private int returnCalculateThumbX(int thumbValue) {
		return (thumbValue * getWidth()) / this.timeSize;
	}
	
	private int returnCalculateThumbValue(int thumbX) {
		return (this.timeSize * (thumbX)) / (getWidth());
	}
	
	public void setSeekBarChangeListener(SeekBarChangeListener scl) {
		this.scl = scl;
	}
	
	public void setSeekBarRangeSize(int size) {
		this.rangeSize = size;
	}
	
	public void setSeekBarTimeSize(int size) {
		this.timeSize = size;
	}

}
