package com.example.animationtest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.twotoasters.android.horizontalimagescroller.widget.HorizontalImageScroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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


@SuppressLint("DrawAllocation")
public class PointsImageView extends ImageView {
	 
	
	Bitmap imageViewBitmap,imageViewBitmap2;
	Paint paint;
	Path path;
	Random randomGenerator = new Random();
	static List<Point> points = new ArrayList<Point>();
	static int count =0;
	static boolean isAnimate=false;
	int animationTime;
	long start_time,lastindex_time;
	int currentindex=0;
	XmlPullParserFactory pullParserFactory;
	private final String TAG ="XMLParser";
	
	HorizontalImageScroller scroller;
	
	public PointsImageView(Context context, AttributeSet set) {         
		 super(context, set);     
		 
		 imageViewBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.leftbg);
		 imageViewBitmap2=BitmapFactory.decodeResource(getResources(),R.drawable.leftfg);
		 
		 loadandparseXML();

		 
		 configurePaintandPath();
		 
		 animationTime=3000;
		 
		 start_time=lastindex_time=System.currentTimeMillis();
		} 
	
	 public static void startAnimation(boolean isStart)
	{

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
	
    public PointsImageView(Context context) {
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
        
        
        initializePath();
        
    }
    
    public void initializePath()
    {
     
    	// This should be loaded from the XML ... 
	    

    	Log.d(TAG,"size"+points.size());
	  
     	path= new Path();
  		path.reset();
 		path.moveTo(points.get(0).x, points.get(0).y);
  		
    	
    }
    
    public void loadandparseXML()
    {

    	try 
    	{
    	      pullParserFactory = XmlPullParserFactory.newInstance();
//    	      pullParserFactory.setNamespaceAware(true);
    	      
    	      XmlPullParser parser = pullParserFactory.newPullParser();
    	       parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
    	    
    	      InputStream in_s = getContext().getAssets().open("points.xml");
    	            parser.setInput(in_s, null);
    	     
    	              
    	      parseXML(parser);
    	      
    	      Log.d("1","1");
    	      
    	    } catch (XmlPullParserException e) {
    	      e.printStackTrace();
    	    } catch (IOException e) {
    	      // TODO Auto-generated catch block
    	      e.printStackTrace();
    	}
    	
    }
    
    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
     
      int eventType = parser.getEventType();
      Point currentPoint = null;
          
      Log.d("2.1","1"+eventType);
    
      while( eventType != XmlPullParser.END_DOCUMENT)
      {

          String name ="";
          
          switch (eventType)
          {
              case XmlPullParser.START_DOCUMENT:
                
            	  points = new ArrayList<Point>();
            	  Log.d(TAG,"1.11");
                
                  break;
                  
              case XmlPullParser.START_TAG:
                  
            	  name = parser.getName();
                  
                  if (name.equals("point"))
                  {
                	   Log.d(TAG,"start");
                      currentPoint = new Point();
                      
                  } else if (currentPoint != null)
                  {
                      if (name.equals("x"))
                        currentPoint.x =Integer.parseInt(parser.nextText().toString());
                      else if (name.equals("y"))
                        currentPoint.y = Integer.parseInt( parser.nextText().toString());
                      
                  }
                  break;
                  
              case XmlPullParser.END_TAG:
                  name = parser.getName();
                  if (name.equalsIgnoreCase("point") && currentPoint != null){
                    points.add(currentPoint);
                  }
          }
          eventType = parser.next();
          Log.d(TAG,"Event :"+eventType);
      
      }
      Log.d(TAG,"Size :"+points.size()); // I get the correct size here :64
 //   printpoints(points);
          
    }
    
    
    
    private void printpoints(ArrayList<Point> points)
    {
      
    	Log.d(TAG,"sd");
    	
      String content = "";

      
      Log.d(TAG,"size"+points.size());
     
      int i=0;
     
      for(;i<points.size();i++)
      {
    	  Point currPoint  = points.get(i);
    	  content = content + "x : " +  currPoint.x + " ";
          content = content + "y : " +  currPoint.y + " ";
          
         // points.add(new Point(currPoint.x,currPoint.y));
          
      Log.d(TAG, "Val"+i);
    	  
      }
    	  
      
      Log.d("The XML String","content: "+content);
       
    }
   
    public void onDraw(Canvas canvas)
    {
    	super.onDraw(canvas);
      
	    if(isAnimate)
	    {
	    	if((System.currentTimeMillis()-lastindex_time)>(animationTime/points.size())&&currentindex<points.size()-1)
	    		{
	    			currentindex++;
	    			lastindex_time=System.currentTimeMillis();
	    		}
	    	
	    	else if(currentindex==points.size()-1)
	    		{
//	    			MainActivity.strokeFinished();
	    			isAnimate=!isAnimate;
	    			Intent camera_intent=new Intent(getContext(),AnimationActivity.class);
	    		    getContext().startActivity(camera_intent);
	    		}
	    	
	    
	    }
	    
	    path.lineTo(points.get(currentindex).x, points.get(currentindex).y);
	  
	    
	    canvas.drawBitmap(imageViewBitmap, 0, 0,paint); 
	    canvas.drawBitmap(imageViewBitmap2, 0, 0,paint); 
	    canvas.drawPath(path, paint);
	    invalidate();
	    
	    
    	
    }
    public boolean onTouchEvent(MotionEvent event) {
    	   // TODO Auto-generated method stub
    	   
    	Log.d("X = "+event.getX(),"Y = "+event.getY());
    	   return true; //processed
    	  }
    
    
}