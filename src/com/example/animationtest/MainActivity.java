package com.example.animationtest;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity 
{
	Button IsolateButton;
	private Animation animFadeOut;
	Paint paint;
	static ImageView mImageView;
	Bitmap imageViewBitmap;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		
		setContentView(R.layout.activity_main);
		
		mImageView=(ImageView)findViewById(R.id.customImageView);
		
		IsolateButton=(Button)findViewById(R.id.isolatebutton);
		
		animFadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		
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
	    	 
	    	 ArrowImageView.startAnimation(true);
	    	 
	    	 
	    	 
	        break;
	     
	      }
	}
	
	public static void strokeFinished()
	{
		;
		
	}
	

};