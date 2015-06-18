package sftf.appinfo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymikufloat.R;

public class AppInfoAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<Programe> mList = null;
	
	public AppInfoAdapter(Context context, List<Programe> list) {
		this.mContext = context;
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
        ViewHolder holder;  
        if(convertView == null)  
        {    
        	LayoutInflater  la = LayoutInflater.from(mContext);  
            convertView=la.inflate(R.layout.appinfo_listitem, null);  
              
            holder = new ViewHolder();  
            holder.imgage=(ImageView) convertView.findViewById(R.id.appIcon);  
            holder.text = (TextView) convertView.findViewById(R.id.appName);  
              
            convertView.setTag(holder);  
        }else{  
            holder = (ViewHolder) convertView.getTag();  
        }  
        
        Programe pro = (Programe)mList.get(position);  
        //设置图标  
        holder.imgage.setImageDrawable(pro.getIcon());  
        //设置程序名  
        holder.text.setText(pro.getName());
          
        return convertView;  
    }  
	
	class ViewHolder{  
		TextView text;  
		ImageView imgage;
	} 

}
