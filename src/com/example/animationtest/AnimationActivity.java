package com.example.animationtest;

import java.io.File;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimationActivity extends Activity 
{
	
	Vector<ImageView> mimageViews;
	Vector<AnimationSet> mAnimations;
	RelativeLayout activityLayout;
	File seperatedLayersFolder ;
	String TAG="AnimationActivity";
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		

		activityLayout= new RelativeLayout(this);
		activityLayout.setBackgroundColor(Color.BLACK);
		
		mimageViews=new Vector<ImageView>();
		// Create that many ImageViews as much as there are in the Tesseract/Layers
		seperatedLayersFolder= new File(Environment.getExternalStorageDirectory()+"/Tesseract/Layers/");
		
		LoadFiles(seperatedLayersFolder);
	
		setContentView(activityLayout);
		startAnimation();
		
		// Add it to the Relative Layout

//		activityLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
//		mimageView.setLayoutParams(activityLayout);   
//		
		
	}

	private void startAnimation() 
	{
		
		AnimationSet animation[] ;
		
		for(int i=0;i<mimageViews.size();i++)
		{
			
			AnimationSet tempAnimation=new AnimationSet(true);
		 	
			TranslateAnimation anim = new TranslateAnimation( 0,0- mimageViews.get(i).getTop()  , 0, 0-mimageViews.get(i).getLeft()  );
		    anim.setDuration(1000);
		    anim.setFillAfter( true );
		    
		  
		    
		    ScaleAnimation scaleanimation = new ScaleAnimation(1, (float)0.5, 1, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5);
		    scaleanimation.setDuration(1000);
		   // mImageView.startAnimation(animation);
		    tempAnimation.addAnimation(anim);tempAnimation.addAnimation(scaleanimation);
		   
		  // This takes care that the ImageViews stay in their final positions after animating . 
		    tempAnimation.setFillEnabled(true);
		    tempAnimation.setFillAfter(true);
		    
		    mImageView.startAnimation(animation);
	    
		
		
			
			
			
		}
		
	}

	private void LoadFiles(File seperatedLayersFolder) 
	{
		
		
		File[] files = seperatedLayersFolder.listFiles();
		
		Log.d(TAG,"Number of files:"+files.length);
		
		for (File file : files)
		{
        	Log.d("File path:","Path="+file.getPath());
        	mimageViews.add(createImageView(file.getPath()));
		}

		
    }

	private ImageView createImageView(String imageLocation) 
	{
		
		ImageView newimageView=new ImageView(this);
		
		Bitmap tempBitmap;
		
		tempBitmap = BitmapFactory.decodeFile(imageLocation);
		newimageView.setImageBitmap(tempBitmap);
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
			
		newimageView.setLayoutParams(layoutParams);
		activityLayout.addView(newimageView);
		
		return newimageView;
	}
		
	
}
