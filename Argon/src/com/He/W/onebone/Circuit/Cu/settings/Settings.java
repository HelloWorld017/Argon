package com.He.W.onebone.Circuit.Cu.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.He.W.project_argon.LogException;
import org.He.W.project_argon.activity.MainActivity;

import com.He.W.onebone.Circuit.Cu.exception.ParseException;
import com.He.W.onebone.Circuit.Cu.parser.CCSGenerator;
import com.He.W.onebone.Circuit.Cu.parser.CCSParser;
import com.He.W.onebone.Circuit.Cu.settings.EnumSettings;

import android.graphics.Typeface;
import android.os.Environment;
import android.widget.Toast;

public class Settings {
	
	private static Typeface tf;
	private static HashMap<EnumSettings, Integer> flags = new HashMap<EnumSettings, Integer>();
	private static boolean isSettingChanged = false;
	private static String settingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/Argon/Settings.ccs";
	
	public static void initSettings(){
		try {
			readAllSettings();
		} catch (ParseException e) {
			switch(e.getType()){
				case ParseException.NO_FILE: 
					Toast.makeText(MainActivity.getMainActivityContext(), "No setting file!", Toast.LENGTH_LONG).show();
					CCSGenerator.genSettings();
					System.exit(0);
					break;
				case ParseException.UNKNOWN: 
					Toast.makeText(MainActivity.getMainActivityContext(), LogException.getLog(e), Toast.LENGTH_LONG).show();
					LogException.log(e);
					System.exit(0);
					break;
				case ParseException.WRONG_FILE: 
					Toast.makeText(MainActivity.getMainActivityContext(), "Wrong Files!", Toast.LENGTH_LONG).show();
					regenSettings(new File(settingPath));
					System.exit(0);
					break;			
			}
			
		} catch (IOException e) {
			Toast.makeText(MainActivity.getMainActivityContext(), "An IOException has been occured.", Toast.LENGTH_LONG).show();
			LogException.log(e);
			e.printStackTrace();
		}
		tf = Typeface.createFromAsset(MainActivity.getMainActivityContext().getAssets(), "font/Ubuntu.ttf");
	}
	
	public static Typeface getFont(){
		return tf;
	}
	
	public static void regenSettings(File f){
		File renameTarget = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/Argon/Settings.ccs.bak");
		if(renameTarget.exists()){
			renameTarget.delete();
		}
		f.renameTo(renameTarget);
		Toast.makeText(MainActivity.getMainActivityContext(), "Your setting file is backed up.", Toast.LENGTH_LONG).show();
		CCSGenerator.genSettings();
	}
	
	public static int readSetting(EnumSettings e){
		return flags.get(e);
	}
	
	public static boolean getBooleanSettings(EnumSettings e){
		int f = flags.get(e);
		switch(f){
			case 0: return false;
			case 1: return true;
			default: 
				switch(e.getOriginalValue()){
					case 0: return false;
					case 1: return true;
					default: return true;
				}
		}
	}
	
	public static void readAllSettings() throws ParseException, IOException {
		File f = new File(settingPath);
		if(!f.exists()){
			CCSGenerator.genSettings();
		}
		
		FileInputStream fis = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String dataType = br.readLine();
		if(!dataType.startsWith("Argon Setting File v")){
			Toast.makeText(MainActivity.getMainActivityContext(), "Error on Argon Setting File", Toast.LENGTH_LONG).show();
			regenSettings(f);
			System.exit(0);
		}else{
			int version = Integer.parseInt(dataType.replace("Argon Setting File v", ""));
			if(!CCSParser.canParse(version)){
				Toast.makeText(MainActivity.getMainActivityContext(), "Setting file is too old to read.", Toast.LENGTH_LONG).show();
				regenSettings(f);
				System.exit(0);
			}
			
			br.reset();
			
			TreeMap<String, String> data = CCSParser.parseCCS(br).get("Settings");
			//data.forEach((k, v) ->flags.put(EnumSettings.valueOf(k), Integer.parseInt(v)));
			//Android uses Java SE 1.6. So, we can't use lambda Expressions.
			//using entry
			Iterator<Entry<String, String>> dataIterator = data.entrySet().iterator();
			while(dataIterator.hasNext()){
				Entry<String, String> dataItem = dataIterator.next();
				flags.put(EnumSettings.valueOf(dataItem.getKey()), Integer.parseInt(dataItem.getValue()));
			}
			br.close();
			isr.close();
			fis.close();
		}
		
	}
	
	public static void writeSetting(EnumSettings e, int value){
		//flags.replace(e, value);
		//Android not supports java 1.8. So, we can't use replace method
		//Using flags. put (If the map previously contained a mapping for the key, the old value is replaced.)
		flags.put(e, value);
		isSettingChanged = true;
	}
	
	
	
	public static void destroyHelper(){
		if(isSettingChanged){
			try {
				writeAllSettings();
			} catch (FileNotFoundException e) {
				Toast.makeText(MainActivity.getMainActivityContext(), "No setting file found!", Toast.LENGTH_LONG).show();
				CCSGenerator.genSettings();
				System.exit(0);
			} catch (IOException e) {
				Toast.makeText(MainActivity.getMainActivityContext(), "An IOException has been occured.", Toast.LENGTH_LONG).show();
				LogException.log(e);
				System.exit(0);
			}
		}
	}
	
	public static void writeAllSettings() throws FileNotFoundException, IOException{
		FileOutputStream fos = new FileOutputStream(new File(settingPath));
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write("Argon Setting File v" + CCSParser.MAX_FILE_VERSION);
		bw.newLine();
		bw.append("[Settings]");
		Iterator<Entry<EnumSettings, Integer>> flagsIterator = flags.entrySet().iterator();
		while(flagsIterator.hasNext()){
			bw.newLine();
			Entry<EnumSettings, Integer> flagEntry = flagsIterator.next();
			bw.append(flagEntry.getKey().name() + "=" + flagEntry.getValue());
		}
		bw.newLine();
		bw.append("[/]");
		bw.flush();
		bw.close();
		osw.close();
		fos.close();
	}
	
	public static HashMap<EnumSettings, Integer> getWholeFlags(){
		return flags;
	}
}
