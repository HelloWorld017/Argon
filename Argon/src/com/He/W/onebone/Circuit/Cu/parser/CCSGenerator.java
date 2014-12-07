package com.He.W.onebone.Circuit.Cu.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Environment;

import com.He.W.onebone.Circuit.Cu.settings.EnumSettings;

public class CCSGenerator {
	private static String settingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/Argon/Settings.ccs";
	private static String arfolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/Argon/";
	
	public static void testCCFolder(){
		File ccf = new File(arfolderPath);
		if(!ccf.exists()){
			ccf.mkdirs();
			genSettings();
		}
	}
	
	
	public static void genSettings(){
		try {
			File f = new File(settingPath);

			if(!f.exists()){
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("Argon Setting File v1");
			bw.newLine();
			bw.append("[Settings]");
			EnumSettings[] Settings = EnumSettings.values();
			for(int a = 0; a < Settings.length; a++){
				bw.newLine();
				bw.append(Settings[a].getName() + "," + Settings[a].getOriginalValue());
			}
			bw.newLine();
			bw.append("[/]");
			bw.flush();
			bw.close();
			osw.close();
			fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
