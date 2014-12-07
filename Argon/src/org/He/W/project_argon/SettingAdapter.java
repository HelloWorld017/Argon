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
		Object[] temp;
		Entry<EnumSettings, Integer> tempEntry;
		EnumSettings tempSetting;
		ArrayList<EnumSettingParents> lists = new ArrayList<EnumSettingParents>();
		while(iterator.hasNext()){
			temp = new Object[8];
			/*Index of temp
			 * 0 = Name 
			 * 1 = Tip
			 * 2 = isParent
			 * 3 = isDefaultTrueFalse
			 * 4 = values
			 * 5 = Original Value
			 * 6 = Current Value
			 * 7 = needs restart
			 *
			 */
			tempEntry = iterator.next();
			tempSetting = tempEntry.getKey();
			temp[0] = tempSetting.getName();
			temp[1] = tempSetting.getTip();
			temp[2] = false;
			temp[3] = tempSetting.isUsingDefaultTrueFalse();
			temp[4] = null;
			if(!tempSetting.isUsingDefaultTrueFalse()){
				temp[4] = tempSetting.getValues();
			}
			temp[5] = tempSetting.getOriginalValue();
			temp[6] = tempEntry.getValue();
			temp[7] = tempSetting.needsRestart();
			
			if(!lists.contains(tempSetting.getParent())){
				lists.add(tempSetting.getParent());
				database.add(new Object[] {tempSetting.getParent().getName(), "", true});
			}
			
			database.add(temp);
		}
		
		temp = null;
		tempEntry = null;
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
		if((Boolean) data[2]){
			if(arg1 == null){
				arg1 = Selectable;	
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText((String)data[0]);
			settingName.setTextSize(50);
			settingName.setTypeface(Settings.getFont());
			arg1.findViewById(R.id.txtDesc).setVisibility(View.GONE);
			arg1.findViewById(R.id.spnValues).setVisibility(View.GONE);
		}else if((Boolean)data[3]){
			if(arg1 == null){
				arg1 = TrueFalse;	
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText((String)data[0]);
			settingName.setTypeface(Settings.getFont());
			TextView settingDesc = ((TextView)arg1.findViewById(R.id.txtDesc));
			settingDesc.setText((String)data[1]);
			settingDesc.setTypeface(Settings.getFont());
			
			ToggleButton settingToggle = (ToggleButton)arg1.findViewById(R.id.spnValues);
			settingToggle.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
					
					if(isChecked){
						Settings.writeSetting(EnumSettings.valueOf((String)data[0]), 1);
					}else{
						Settings.writeSetting(EnumSettings.valueOf((String)data[0]), 0);
					}
					
				}
				
			});
			settingToggle.setTypeface(Settings.getFont());
		}if((Boolean) data[3]){
			if(arg1 == null){
				arg1 = Selectable;
			}
			TextView settingName = ((TextView)arg1.findViewById(R.id.txtName));
			settingName.setText((String)data[0]);
			settingName.setTypeface(Settings.getFont());
			TextView settingDesc = ((TextView)arg1.findViewById(R.id.txtDesc));
			settingDesc.setText((String)data[1]);
			settingDesc.setTypeface(Settings.getFont());
			
			//TODO update Spinner
			Spinner settingSpinner = (Spinner)arg1.findViewById(R.id.spnValues);
			ArrayAdapter settingValues = new ArrayAdapter(ctxt, android.R.layout.simple_spinner_item);
			
		}
		return arg1;
	}

}
