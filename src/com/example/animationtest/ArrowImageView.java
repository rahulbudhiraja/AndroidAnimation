package com.example.animationtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.View;


public class ArrowImageView extends ImageView {
	 
	Bitmap imageViewBitmap;
	Paint paint;
	Path path;
	Random randomGenerator = new Random();
	List<Point> points = new ArrayList<Point>();
	static int count =0;
	static boolean isAnimate=false;
	int animationTime;
	long start_time,lastindex_time;
	int currentindex=0;
		 
	public ArrowImageView(Context context, AttributeSet set) {         
		 super(context, set);     
		 
		 imageViewBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.originalleft);
		 configurePaintandPath();
		 
		 animationTime=3000;
		 start_time=lastindex_time=System.currentTimeMillis();
		} 
	
	 public static void startAnimation(boolean isStart)
	{
//		if(isStart)
//			loadPath();
		isAnimate=true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	    int desiredWidth = imageViewBitmap.getWidth();
	    int desiredHeight = imageViewBitmap.getHeight();

	    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

	    int width;
	    int height;

	    //Measure Width
	    if (widthMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        width = widthSize;
	    } else if (widthMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        width = Math.min(desiredWidth, widthSize);
	    } else {
	        //Be whatever you want
	        width = desiredWidth;
	    }

	    //Measure Height
	    if (heightMode == MeasureSpec.EXACTLY) {
	        //Must be this size
	        height = heightSize;
	    } else if (heightMode == MeasureSpec.AT_MOST) {
	        //Can't be bigger than...
	        height = Math.min(desiredHeight, heightSize);
	    } else {
	        //Be whatever you want
	        height = desiredHeight;
	    }

	    //MUST CALL THIS
	    setMeasuredDimension(width, height);
	}
	
    public ArrowImageView(Context context) {
        super(context);
    }
    
    public void configurePaintandPath()
    {
    	paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
        paint.setColor(Color.RED);                    // set the color
        paint.setStrokeWidth(3);               // set the size
        paint.setDither(true);                    // set the dither to true
        paint.setStyle(Paint.Style.STROKE);       // set to STOKE
        paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        paint.setPathEffect(new CornerPathEffect(20) );   // set the path effect when they join.
        paint.setAntiAlias(true);  
        
        
        loadPath();
        
    }
    
    public void loadPath()
    {
     
    	// This should be loaded from the XML ... 
	    
//	  points.add(new Point(267,469));points.add(new Point(265,400));
//	  points.add(new Point(267,340));points.add(new Point(253,325));
//	  points.add(new Point(240,280));points.add(new Point(248,229));
//	  points.add(new Point(233,193));points.add(new Point(206,214));
//	  points.add(new Point(203,228));points.add(new Point(202,267));
//	  points.add(new Point(206,280));points.add(new Point(201,291));
//	  points.add(new Point(187,330));points.add(new Point(186,342));
//	  points.add(new Point(186,345));points.add(new Point(182,388));   
//	  points.add(new Point(183,395));points.add(new Point(179,435));
//	  points.add(new Point(189,451));points.add(new Point(178,477)); 
//	  points.add(new Point(175,490));
    	
    	 points.add(new Point(188,540));
    	 points.add(new Point(187,530));
    	 points.add(new Point(190,502));
    	 points.add(new Point(186,501));
    	 points.add(new Point(186,523));
    	 points.add(new Point(181,526));
    	 points.add(new Point(177,526));
    	 points.add(new Point(175,523));
    	 points.add(new Point(172,520));
    	 points.add(new Point(170,515));
    	 points.add(new Point(169,512));
    	 points.add(new Point(168,504));
    	 points.add(new Point(170,490));
    	 points.add(new Point(170,476));
    	 points.add(new Point(171,437));
    	 points.add(new Point(173,428));
    	 points.add(new Point(170,427));
    	 points.add(new Point(169,419));
    	 points.add(new Point(172,414));
    	 points.add(new Point(174,388));
    	 points.add(new Point(177,345));
    	 points.add(new Point(179,332));
    	 points.add(new Point(181,319));
    	 points.add(new Point(189,307));
    	 points.add(new Point(196,300));
    	 points.add(new Point(205,294));
    	 points.add(new Point(209,287));
    	 points.add(new Point(210,277));
    	 points.add(new Point(208,273));
    	 points.add(new Point(207,263));
    	 points.add(new Point(205,253));
    	 points.add(new Point(205,243));
    	 points.add(new Point(204,234));
    	 points.add(new Point(206,220));
    	 points.add(new Point(211,211));
    	 points.add(new Point(217,206));
    	 points.add(new Point(221,206));
    	 points.add(new Point(225,211));
    	 points.add(new Point(228,213));
    	 points.add(new Point(233,217));
    	 points.add(new Point(234,221));
    	 points.add(new Point(234,226));
    	 points.add(new Point(234,236));
    	 points.add(new Point(233,268));
    	 points.add(new Point(231,271));
    	 points.add(new Point(229,277));
    	 points.add(new Point(230,286));
    	 points.add(new Point(231,290));
    	 points.add(new Point(235,298));
    	 points.add(new Point(238,304));
    	 points.add(new Point(245,312));
    	 points.add(new Point(251,319));
    	 points.add(new Point(255,330));
    	 points.add(new Point(259,353));
    	 points.add(new Point(260,373));
    	 points.add(new Point(260,417));
    	 points.add(new Point(263,420));
    	 points.add(new Point(266,436));
    	 points.add(new Point(263,438));
    	 points.add(new Point(264,525));
    	 points.add(new Point(262,532));
    	 points.add(new Point(255,530));
    	 points.add(new Point(254,523));
    	 points.add(new Point(253,510));
    	 points.add(new Point(253,498));
    	 points.add(new Point(255,476));
    	 points.add(new Point(252,473));
    	 points.add(new Point(245,416));
    	 points.add(new Point(243,417));
    	 points.add(new Point(250,509));
    	 points.add(new Point(249,508));
    	 points.add(new Point(249,536));
    	 points.add(new Point(249,539));
    	 points.add(new Point(188,540));
    	
	  
     	path= new Path();
  		path.reset();
  		path.moveTo(points.get(0).x, points.get(0).y);
  		
    	
    }
    public void onDraw(Canvas canvas)
    {
    	super.onDraw(canvas);
      
	    if(isAnimate)
	    {
	    	if((System.currentTimeMillis()-lastindex_time)>(animationTime/points.size())&&currentindex!=points.size()-1)
	    		{
	    			currentindex++;
	    			lastindex_time=System.currentTimeMillis();
	    		}
	    	
	    
	    }
	    path.lineTo(points.get(currentindex).x, points.get(currentindex).y);
		  
	    
	    canvas.drawBitmap(imageViewBitmap, 0, 0,paint); 	   
	    canvas.drawPath(path, paint);
	    invalidate();
	    
	    
    	
    }
    public boolean onTouchEvent(MotionEvent event) {
    	   // TODO Auto-generated method stub
    	   
    	Log.d("X = "+event.getX(),"Y = "+event.getY());
    	   return true; //processed
    	  }
    
    
}