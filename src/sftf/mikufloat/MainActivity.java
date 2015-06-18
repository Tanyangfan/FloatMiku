package sftf.mikufloat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
//		DisplayMetrics dm = new DisplayMetrics();
//
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		
//		int screenWidth  = dm.widthPixels;
//		int screenHeight = dm.heightPixels;
		
		startService(new Intent(this, MikuService.class));
		finish();
	}
}
