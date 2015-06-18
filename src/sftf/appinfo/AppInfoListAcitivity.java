package sftf.appinfo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mymikufloat.R;

public class AppInfoListAcitivity extends Activity {
	
	private final static String TAG = "AppInfoListAcitivity";
	private ListView appInfoListView = null;
	private Animation anim = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appinfo_activity_layout);
		
		anim = AnimationUtils.loadAnimation(this, R.anim.zoomin); 
		
		initView();
	}

	public void initView() {
		appInfoListView = (ListView)findViewById(R.id.appInfo_list);
		AppInfoAdapter mAdapter = new AppInfoAdapter(this, getAppInfo());
		appInfoListView.setAdapter(mAdapter);
		
	}
	
	private List<Programe> getAppInfo() {

		Log.d(TAG, "MyGetPackgeName");
		PackageManager pckMan = getPackageManager();
		List<PackageInfo> packs = pckMan.getInstalledPackages(0);
		int count = packs.size();
		String name = null;
		String packageName = null;
		
		List<Programe> programList = new ArrayList<Programe>(); 

		for (int i = 0; i < count; i++) {
			PackageInfo p = packs.get(i);
			if (p.versionName == null)
				continue;
			
			ApplicationInfo appInfo = p.applicationInfo;

			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				
				name = p.applicationInfo.loadLabel(pckMan).toString();
				Log.e(TAG, "系统APP" + name);
				} else {
					Programe  pr = new Programe(); 
					if(appInfo != null) {
		            	pr.setIcon(p.applicationInfo.loadIcon(pckMan));  
		                pr.setName(p.applicationInfo.loadLabel(pckMan).toString());
		                System.out.println(p.applicationInfo.loadLabel(pckMan).toString());
		                programList.add(pr);
		            }
				}			
		}
		
		return programList;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		appInfoListView.startAnimation(anim);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		getWindow().setWindowAnimations(R.anim.zoomout);
	}
}
