package sftf.mikufloat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sftf.appinfo.AppInfoListAcitivity;
import sftf.appinfo.PackagesInfo;
import sftf.appinfo.Programe;

import com.example.mymikufloat.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class FunctionActivity extends Activity{

	private static String TAG = "Function";
	private RelativeLayout layout;
	private Animation anim;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.fun_main);
		layout = (RelativeLayout) findViewById(R.id.fun_main_rl);
		anim = AnimationUtils.loadAnimation(this, R.anim.zoomin);  
	}

	
	
	//正在运行的  
    public List<Programe> getRunningProcess(){  
        PackagesInfo pi = new PackagesInfo(this);  
          
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);  
        //获取正在运行的应用  
        List<RunningAppProcessInfo> run = am.getRunningAppProcesses();  
        //获取包管理器，在这里主要通过包名获取程序的图标和程序名  
        PackageManager pm =this.getPackageManager();  
        List<Programe> list = new ArrayList<Programe>();      
          
        for(RunningAppProcessInfo ra : run){  
            //这里主要是过滤系统的应用和电话应用，当然你也可以把它注释掉。  
            if(ra.processName.equals("system") || ra.processName.equals("com.android.phone")){  
                continue;  
            }  
              
            Programe  pr = new Programe();  
            ApplicationInfo appInfo = pi.getInfo(ra.processName);
            if(appInfo != null) {
            	pr.setIcon(appInfo.loadIcon(pm));  
                pr.setName(pi.getInfo(ra.processName).loadLabel(pm).toString());
                System.out.println(pi.getInfo(ra.processName).loadLabel(pm).toString());  
            }
            	  
            
            list.add(pr);  
        }  
        return list;  
    } 
	
	private void myPlayMusic() {
		
		Intent intent = new Intent("android.intent.action.MUSIC_PLAYER"); 
		startActivity(intent);
	}
	
	public void clickOnApplication(View view) {
		
		Intent intent = new Intent();
		intent.setClass(FunctionActivity.this, AppInfoListAcitivity.class);
		startActivity(intent);
	}
	
	public void clickOnGuesture(View view){

//		Intent intent = new Intent();
//		//intent.setClass(FunctionActivity.this, GuestureActivity.class);
//		startActivity(intent);
		Log.d(TAG, "clickOnGuesture");
		myPlayMusic();
	}
	
	public void clickOnFavorite(View view){

		Intent intent = new Intent();
		//intent.setClass(FunctionActivity.this, FavoriteActivity.class);
		startActivity(intent);
	}
	
	public void clickOnBack(View view){
		
		finish();
	}
	
	public void clickOnDevice(View view){

		Intent intent = new Intent();
		//intent.setClass(FunctionActivity.this, DeviceActivity.class);
		startActivity(intent);
		getWindow().setWindowAnimations(R.anim.zoomout);
	}
	
	public void clickOnMainscreen(View view){
		
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(mHomeIntent);		
	}

	@Override
	protected void onResume() {
		
		super.onResume();

		this.setVisible(true);
		layout.startAnimation(anim);
	}

	@Override
	protected void onPause() {

		super.onPause();

		setVisible(false);
	}

	@Override
	protected void onStop() {
		
//		MikuService.show();
		super.onStop();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
	
	
}
