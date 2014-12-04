package org.He.W.project_argon;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

public class LogException {
	public static void log(Throwable t){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		Log.e("Error", sw.toString());
	}
	public static String getLog(Throwable t){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}
