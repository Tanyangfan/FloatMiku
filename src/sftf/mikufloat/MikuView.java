package sftf.mikufloat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

public class MikuView extends ImageView {
	public static final String POSITION = "position";
	
	private float mTouchX;
	private float mTouchY;
	private float mStartX;
	private float mStartY;
	private OnClickListener mClickListener;
	private OnLongClickListener mOnLongClickListener;
	private boolean isLongClick = false;
	private WindowManager windowManager = (WindowManager) getContext()
			.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	// 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
	private WindowManager.LayoutParams windowManagerParams = ((MikuApplication) getContext()
			.getApplicationContext()).getWindowParams();
	private int width = windowManager.getDefaultDisplay().getWidth();
	// private int height = windowManager.getDefaultDisplay().getHeight();
	private float x;
	private float y;
	
	private SharedPreferences sharedPreferences = null;
	private Editor editor = null;
	
	private Thread thread_alpha_low = null;
	private Thread thread_alpha_high = null;
	private Thread thread_count = null;
//	private Thread thread_move = null;
//	private Thread thread = null;
//	private boolean fadeout = true;
	
	public MikuView(Context context) {
		super(context);
		sharedPreferences = context.getSharedPreferences(POSITION, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		thread_alpha_low = new Thread(){
			@Override
			public void run() {
				MikuView.this.setAlpha(100);
				super.run();
			}
		};
		thread_alpha_high = new Thread(){
			@Override
			public void run() {
				MikuView.this.setAlpha(255);
				super.run();
			}
		};
		thread_count = new Thread() {
			public void run() {
				isLongClick = true;
				super.run();
			};
		};
		
		
	}
	
	public void autoAlphaLow(){
		if(getHandler() != null && thread_alpha_low != null){
			getHandler().postDelayed(thread_alpha_low, 3000);
		}else{
			//PrintUtils.println(" handler or thread is null");
		}
	}
	
	public void autoAlphaHigh(){
		if(getHandler() != null && thread_alpha_high != null){
			getHandler().post(thread_alpha_high);
		}else{
			//PrintUtils.println(" handler or thread is null");
		}
	}
	
	public void getLongClick() {
		if(getHandler() != null && thread_count != null){
			getHandler().postDelayed(thread_count, 1000);
		}else{
			//PrintUtils.println(" handler or thread is null");
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//PrintUtils.println();
		// 获取到状态栏的高度
		Rect frame = new Rect();
		getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		// 获取相对屏幕的坐标，即以屏幕左上角为原点
		x = event.getRawX();
		y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
			// 获取相对View的坐标，即以此View左上角为原点
			MikuView.this.setAlpha(255);
			mTouchX = event.getX();
			mTouchY = event.getY();
			mStartX = x;
			mStartY = y;
			
			isLongClick = false;
			getLongClick();

			//PrintUtils.println("startX" + mTouchX + "====startY" + mTouchY);
			break;
		case MotionEvent.ACTION_MOVE: 
			updateViewPosition();
			getHandler().post(thread_alpha_high);
			break;
		case MotionEvent.ACTION_UP: 
			updateViewPosition();
			//autoMove();
			editor.putFloat("x", windowManagerParams.x);
			editor.putFloat("y", windowManagerParams.y);
			editor.commit();
			autoAlphaLow();
			
			mTouchX = mTouchY = 0;
			if ((x - mStartX) < 5 && (y - mStartY) < 5) {
				if (!isLongClick) {

					if (mClickListener != null) {
						mClickListener.onClick(this);
					}

				} else {
					if (mOnLongClickListener != null) {
						mOnLongClickListener.onLongClick(this);
					}
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.mClickListener = l;
	}
	
	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		// TODO Auto-generated method stub
		this.mOnLongClickListener = l;
	}
	
	private void updateViewPosition() {

		windowManagerParams.x = (int) (x - mTouchX);
		windowManagerParams.y = (int) (y - mTouchY);
		windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
	}
	
	private void autoMove(){
		int middle = (width - this.getWidth()) / 2;
		while(true){
			if(windowManagerParams.x <= middle && windowManagerParams.x > 5){
				windowManagerParams.x = windowManagerParams.x - 5;
				windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
			}else if(windowManagerParams.x > middle && windowManagerParams.x < middle + width / 2 - 5){
				windowManagerParams.x = windowManagerParams.x + 5;
				windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
			}else{
				break;
			}
		}
		editor.putFloat("x", windowManagerParams.x);
		editor.putFloat("y", windowManagerParams.y);
		editor.commit();
		//autoAlphaLow();
	}
	
}
