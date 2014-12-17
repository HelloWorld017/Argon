package org.He.W.project_argon.activity;

import org.He.W.project_argon.R;

import com.He.W.onebone.Circuit.Cu.settings.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		AlphaAnimation fadeIn = new AlphaAnimation(0.0f,1.0f);
		AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
		AnimationSet fadeInOut = new AnimationSet(false);
		//AnimatorSet doesn't support GingerBread.
		
		fadeIn.setDuration(300);
		fadeIn.setInterpolator(new DecelerateInterpolator());
		fadeOut.setDuration(300);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setStartOffset(800);
		
		fadeInOut.addAnimation(fadeIn);
		fadeInOut.addAnimation(fadeOut);
		
		TextView epilepsy = (TextView) findViewById(R.id.epilepsy);
		epilepsy.setTypeface(Settings.getFont());
		
		TextView epilepsyDesc = (TextView) findViewById(R.id.epilepsy_desc);
		epilepsyDesc.setTypeface(Settings.getFont());
		
		TextView developer = (TextView) findViewById(R.id.developer);
		developer.setTypeface(Settings.getFont());
		
		epilepsy.startAnimation(fadeIn);
		epilepsyDesc.startAnimation(fadeIn);
		developer.startAnimation(fadeInOut);
		fadeInOut.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
