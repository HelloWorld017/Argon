package org.He.W.project_argon.activity;

import org.He.W.project_argon.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {
	private static Context ctxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctxt = this;
	}
	
	public static String getStringbyResid(int resid){
		return ctxt.getString(resid);
	}
	
	public static String[] getStringArraybyResid(int resid){
		return ctxt.getResources().getStringArray(resid);
	}
	
	public static Context getMainActivityContext(){
		return ctxt;
	}
}
