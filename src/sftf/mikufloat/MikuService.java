package sftf.mikufloat;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.example.mymikufloat.R;

public class MikuService extends Service implements OnClickListener,OnLongClickListener{

	private static WindowManager windowManager = null;
	private static WindowManager.LayoutParams windowManagerParams = null;
	private static MikuView mikuView = null;
	private int width = 480;
	private int height = 800;
	private SharedPreferences sharedPreferences = null;
	public static boolean isVisible = true;
	public static Handler handler = null;
	public static final int FADEOUT = 2001;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case FADEOUT:
					if(mikuView != null){
						mikuView.setVisibility(100);
					}
					break;
				case View.VISIBLE:
					if(mikuView != null){
						mikuView.setVisibility(255);
					}
					break;
				case View.INVISIBLE:
					if(mikuView != null){
						mikuView.setVisibility(0);
					}
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		sharedPreferences = getSharedPreferences(MikuView.POSITION, Context.MODE_PRIVATE);
		createView();
		super.onCreate();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startid) {

	}
	
	public static void show(){
		if(mikuView != null){
			mikuView.setVisibility(View.VISIBLE);
		}
	}
	
	public static void hide(){
		if(mikuView != null){
			mikuView.setVisibility(View.INVISIBLE);
		}
	}
	
	public static void close(){
		System.out.println("close miku");
		
		if(mikuView != null && windowManager != null){
			mikuView.setVisibility(View.INVISIBLE);
			if(mikuView.getParent() != null){
				windowManager.removeView(mikuView);
			}
		}
	}
	
	private void createView() {
		
		mikuView = new MikuView(getApplicationContext());
		mikuView.setId(R.id.miku_image);
		mikuView.setOnClickListener(this);
		mikuView.setOnLongClickListener(this);
		mikuView.setBackgroundResource(R.drawable.anim);
		//mikuView.setImageResource(R.drawable.ic_launcher); // 设置ICON
		windowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		width = windowManager.getDefaultDisplay().getWidth();
		height = windowManager.getDefaultDisplay().getHeight();
		windowManagerParams = ((MikuApplication) getApplication())
				.getWindowParams();
		windowManagerParams.type = LayoutParams.TYPE_PHONE; // 设置window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		windowManagerParams.x = (int) sharedPreferences.getFloat("x", 0);
		windowManagerParams.y = (int) sharedPreferences.getFloat("y", 0);
		windowManagerParams.width = width / 4;
		windowManagerParams.height = width / 4 * 198 / 278;
		//show();
		if(mikuView.getParent() == null){
			windowManager.addView(mikuView, windowManagerParams);
		}
		beginAnim();
		//mikuView.setImageResource(R.drawable.ic_launcher);
		//animationDrawable.
	}
	
	@Override
	public boolean onLongClick(View v) {
		System.exit(0);
		System.out.println("miku onLongClick");
		return true;
	}

	@Override
	public void onClick(View v) {
		//System.out.println("onclick");
		System.out.println(v.getId());
		switch (v.getId()) {
		
		case R.id.miku_image:
			System.out.println("click miku");
			Intent intent = new Intent();
			intent.setClass(MikuService.this, FunctionActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(intent);
			//handler.sendEmptyMessage(View.INVISIBLE);
			//hide();
			break;
		case R.drawable.anim_2:
			System.out.println("click anim");
			break;
		default:
			break;
		}
	}
	
	public void beginAnim() {
		
		AnimationDrawable animationDrawable = (AnimationDrawable)mikuView.getBackground();
		animationDrawable.start();
	}
	
	
	
}
