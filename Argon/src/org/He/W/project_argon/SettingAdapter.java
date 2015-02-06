package org.He.W.project_argon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.He.W.onebone.Circuit.Cu.settings.EnumSettingParents;
import com.He.W.onebone.Circuit.Cu.settings.EnumSettings;
import com.He.W.onebone.Circuit.Cu.settings.Settings;

import org.He.W.project_argon.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


public class SettingAdapter extends BaseAdapter {
	ArrayList<Object[]> database;
	private LayoutInflater li;
	private View Selectable = null;
	private View TrueFalse = null;
	private Context ctxt;
	
	public SettingAdapter(Context ctx){
		ctxt = ctx;
		li = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HashMap<EnumSettings, Integer> flags = Settings.getWholeFlags();
		Iterator<Entry<EnumSettings, Integer>> iterator = flags.entrySet().iterator();
		EnumSettings tempSetting;
		ArrayList<EnumSettingParents> lists = new ArrayList<EnumSettingParents>();
		while(iterator.hasNext()){
			tempSetting = iterator.next().getKey();
			if(!lists.contains(tempSetting.getParent())){
				lists.add(tempSetting.getParent());
				database.add(new Object[] {true, null, tempSetting.getParent()});
			}
			if(tempSetting.isVisible()){
				database.add(new Object[]{false, tempSetting, null});
			}
			
			//index of object
			// 0 = isParent
			// 1 = EnumSetting
			// 2 = EnumSettingParent
		}
		
		tempSetting = null;
		iterator = null;
		flags = null;
	}
	
	
	// When you make a listview, use overscrollmode OVER_SCROLL_IF_CONTENT_SCROLLS
	@Override
	public int getCount() {
		return database.size();
	}

	@Override
	public Object getItem(int arg0) {
		return database.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		if(Selectable == null){
			Selectable = li.inflate(R.layout.list_view_selectable, arg2);
		}
		if(TrueFalse == null){
			TrueFalse = li.inflate(R.layout.list_view_default_true_false, arg2);
		}
		
		final Object[] data = database.get(arg0);
		if((Boolean) data[0]){
			if(arg1 == null){
				arg1 = Selectable;	
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText(((EnumSettingParents)data[2]).getName());
			settingName.setTextSize(50);
			settingName.setTypeface(Settings.getFont());
			arg1.findViewById(R.id.txtDesc).setVisibility(View.GONE);
			arg1.findViewById(R.id.spnValues).setVisibility(View.GONE);
		}else if(((EnumSettings)data[1]).isUsingDefaultTrueFalse()){
			if(arg1 == null){
				arg1 = TrueFalse;	
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText(((EnumSettings)data[1]).getName());
			settingName.setTypeface(Settings.getFont());
			TextView settingDesc = ((TextView)arg1.findViewById(R.id.txtDesc));
			settingDesc.setText(((EnumSettings)data[1]).getTip());
			settingDesc.setTypeface(Settings.getFont());
			
			ToggleButton settingToggle = (ToggleButton)arg1.findViewById(R.id.tglValues);
			settingToggle.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					
					if(isChecked){
						Settings.writeSetting(((EnumSettings)data[1]), 1);
					}else{
						Settings.writeSetting(((EnumSettings)data[1]), 0);
					}
					
				}
				
			});
			settingToggle.setTypeface(Settings.getFont());
		}else{
			if(arg1 == null){
				arg1 = Selectable;
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText(((EnumSettings)data[1]).getName());
			settingName.setTypeface(Settings.getFont());
			TextView settingDesc = ((TextView)arg1.findViewById(R.id.txtDesc));
			settingDesc.setText(((EnumSettings)data[1]).getTip());
			settingDesc.setTypeface(Settings.getFont());
			
			Spinner settingSpinner = (Spinner)arg1.findViewById(R.id.spnValues);
			ArrayAdapter<String> settingValues = new ArrayAdapter<String>(ctxt, android.R.layout.simple_spinner_item, ((EnumSettings)data[1]).getValues());
			
			settingSpinner.setAdapter(settingValues);
			settingSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					Settings.writeSetting(((EnumSettings)data[1]), position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
			});
			settingSpinner.setSelection(Settings.readSetting(((EnumSettings)data[1])));
			
		}
		return arg1;
	}

}
