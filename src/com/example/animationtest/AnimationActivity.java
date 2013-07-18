package com.example.animationtest;

import java.io.File;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimationActivity extends Activity 
{
	
	Vector<ImageView> mimageViews;
	Vector<ImageView> CanvasImageViews;
	Vector<AnimationSet> mAnimations;
	Vector<int[]> sizes;
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
		
		CanvasImageViews=new Vector<ImageView>();
		
		sizes=new Vector<int[]>();
		
		// Create that many ImageViews as much as there are in the Tesseract/Layers
		seperatedLayersFolder= new File(Environment.getExternalStorageDirectory()+"/Tesseract/Layers/");
		
		LoadFiles(seperatedLayersFolder);
	
//		setContentView(R.layout.timepasslayout);
		setContentView(activityLayout);
		
	
	}

	private void startAnimation() 
	{
		
		int[] loc = new int[2]; 
		float xPos,yPos;
		
		
// This will make the small layer icons ...		
		
		for(int i=0;i<mimageViews.size();i++)
		{
			
			AnimationSet tempAnimation=new AnimationSet(true);
			
			mimageViews.get(i).getLocationOnScreen(loc);

			
			
			/* How is this evaluated, 
			 * 
			 * The layers are going to be displayed from 25,150 onwards .Then after every image,we leave a space of 2.5*height().
			 * So animation  
			 * */
			
			
			
			xPos= (-this.getWindowManager().getDefaultDisplay().getWidth()/2)+(float)0.22*mimageViews.get(i).getWidth()/2+25;                          // From horizontal centre.x - the destined postion.x
			yPos=(-this.getWindowManager().getDefaultDisplay().getHeight()/2)+(float) (150+(0.5+2.5*i)*(0.22*mimageViews.get(i).getHeight())/2);
			
			Log.d(TAG,"position "+150+(0.5+i)*(0.22*mimageViews.get(i).getHeight())/2);
//			TranslateAnimation anim = new TranslateAnimation( 0,(float) (mimageViews.get(i).getTop()-25) , 0, 0+mimageViews.get(i).getLeft()+100*i  );
		
			TranslateAnimation anim = new TranslateAnimation( 0,xPos, 0, yPos );
		 //   anim.setDuration(1000);
		    Log.d(TAG,"location - "+loc[0]+"  "+loc[1]);
		    anim.setFillAfter( true );
		    anim.setFillEnabled(true);
		    
		    
		    ScaleAnimation scaleanimation = new ScaleAnimation(1, (float)0.22, 1, (float)0.22, Animation.RELATIVE_TO_SELF, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5);
		    scaleanimation.setDuration(1000);
		    scaleanimation.setFillEnabled(true);
		    scaleanimation.setFillAfter(true);
		    
		    tempAnimation.addAnimation(scaleanimation);
		    tempAnimation.addAnimation(anim);
		    tempAnimation.setDuration(1000);
		    
		    tempAnimation.willChangeTransformationMatrix();
			   
		  // This takes care that the ImageViews stay in their final positions after animating . 
		    tempAnimation.setFillEnabled(true);
		    tempAnimation.setFillAfter(true);
		    
		    
		    
		    
		    mimageViews.get(i).startAnimation(tempAnimation);
		}
		

		// This will animate the Canvas Views in the middle ..
		
		for(int i=0;i<CanvasImageViews.size();i++)
		{
			AnimationSet tempAnimation=new AnimationSet(true);
			
			TranslateAnimation anim = new TranslateAnimation( 0,0, 0,0 );
		
		    anim.setFillAfter( true );
		    anim.setFillEnabled(true);
		    
		    ScaleAnimation scaleanimation = new ScaleAnimation(1, (float)0.5, 1, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5);
		    scaleanimation.setDuration(1000);
		    scaleanimation.setFillEnabled(true);
		    scaleanimation.setFillAfter(true);
		    
		    tempAnimation.addAnimation(scaleanimation);
		    tempAnimation.addAnimation(anim);
		    tempAnimation.setDuration(1000);
		    
		    tempAnimation.willChangeTransformationMatrix();
			   
		  // This takes care that the ImageViews stay in their final positions after animating . 
		    tempAnimation.setFillEnabled(true);
		    tempAnimation.setFillAfter(true);
		    
		    CanvasImageViews.get(i).startAnimation(tempAnimation);
			
		}
		
		
		
		
	}
	
/** Have to call the startAnimation from here and not onCreate because if to get an ImageView.getWidth(),we have to call it from onWindowFocusChanged and not from onCreate,start or resume 
 * Refer http://stackoverflow.com/questions/7924296/how-to-use-onwindowfocuschanged-method*/
	
	public void onWindowFocusChanged (boolean hasFocus) {
	        
	       startAnimation();
	}

	private void LoadFiles(File seperatedLayersFolder) 
	{
		
		
		File[] files = seperatedLayersFolder.listFiles();
		
		Log.d(TAG,"Number of files:"+files.length);
		
		for (File file : files)
		{
        	Log.d("File path:","Path="+file.getPath());
        	// Create 2 ImageViews,1 for the layer and the other for the bitmap.
        	mimageViews.add(createImageView(file.getPath()));
        	CanvasImageViews.add(createImageView(file.getPath()));
        	//mimageViews.add(createImageView(file.getPath()));
		}
		
		// Copy the ImageViews which will be drawn to the Canvas..
		
		
		
		
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
		
		/* Bug in Android 2.3.If this is not set,a trail is left behind  */
		newimageView.setPadding(1, 1, 1, 1);
		
		activityLayout.addView(newimageView);
		
		
		Log.d(TAG," Width "+newimageView.getWidth());
		
		
		return newimageView;
	}
	
	
		
	
}
