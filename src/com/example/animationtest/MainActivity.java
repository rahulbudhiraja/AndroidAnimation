package com.example.animationtest;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity 
{
	Button IsolateButton;
	private Animation animFadeOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		
		setContentView(R.layout.activity_main);
		IsolateButton=(Button)findViewById(R.id.isolatebutton);
		
		animFadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void ButtonOnClick(View v) {
	    switch (v.getId()) {
	      case R.id.isolatebutton:
	        // Fade out the Isolate button
	    	 IsolateButton.startAnimation(animFadeOut);
	    	 
	    	 // When the animation finishes ,make the button invisible.
	    	 IsolateButton.setVisibility(View.INVISIBLE);
	        break;
	     
	      }
	}

}
