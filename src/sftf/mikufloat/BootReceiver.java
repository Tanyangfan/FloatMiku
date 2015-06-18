package sftf.mikufloat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    	System.out.println("Boot Complete");
        //context.startService(new Intent(context, MikuService.class));
    }
}