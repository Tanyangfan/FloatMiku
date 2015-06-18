package sftf.listadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.mymikufloat.R;

import sftf.listadapter.DemoAdapter;
import sftf.listadapter.DemoBean;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DemoListActivity extends Activity implements OnClickListener,
		OnItemClickListener{
	
	private static String TAG = "Function";
	
	//返回按钮
	private ViewGroup btnCancle = null;
	
	//ListView
	private ListView funListView = null;

	//适配对象
	private DemoAdapter adpAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.listactivity_layout);
		
		initView();
		
		initData();
//		DisplayMetrics dm = new DisplayMetrics();
//
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		
//		int screenWidth  = dm.widthPixels;
//		int screenHeight = dm.heightPixels;
		

	}
	
	/**
	 * 初始化控件
	 */
	private void initView() {

		Log.d(TAG, "initView");
		
		btnCancle = (ViewGroup) findViewById(R.id.btnCancle);
		btnCancle.setOnClickListener(this);
		
		funListView = (ListView) findViewById(R.id.funListView);
		funListView.setOnItemClickListener(this);

	}
	
	/**
	 * 初始化视图
	 */
	private void initData() {

		// 模拟假数据
		List<DemoBean> demoDatas = new ArrayList<DemoBean>();

		demoDatas.add(new DemoBean("张三", true));
		demoDatas.add(new DemoBean("李四", true));
		demoDatas.add(new DemoBean("王五", false));
		demoDatas.add(new DemoBean("赵六", true));

		adpAdapter = new DemoAdapter(this, demoDatas);

		funListView.setAdapter(adpAdapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
