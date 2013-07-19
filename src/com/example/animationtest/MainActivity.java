package com.example.animationtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class MainActivity extends Activity {
	ImageButton IsolateButton;
	private Animation animFadeOut;
	Paint paint;
	static ImageView mImageView;
	Bitmap imageViewBitmap;
	private final static String TAG = "MainActivity";
	ProgressDialog conversionProgress;

	String[] imageFilters = { "sepia", "stark", "sunnyside", "cool", "worn",
			"grayscale" };

	File seperatedLayersFolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		mImageView = (ImageView) findViewById(R.id.customImageView);

		conversionProgress = new ProgressDialog(this);

		IsolateButton = (ImageButton) findViewById(R.id.isolatebutton);

		animFadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);

		// Create that many ImageViews as much as there are in the
		// Tesseract/Layers
		seperatedLayersFolder = new File(
				Environment.getExternalStorageDirectory()
						+ "/Tesseract/Layers/");

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

			new ProgressDialogClass().execute("");

			break;

		}
	}

	public static void strokeFinished() {
		Log.d(TAG, "reached");

	}

	// /////////

	public class ProgressDialogClass extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			conversionProgress
					.setMessage("Please wait while we process your image ...");
			// OpenCV Stuff.

			// Instagram Filter conversion,Browse the seperated layers directory
			// and convert ..
			conversionProgress
			.setMessage("Applying Filters ...");
			applyFilterstoLayers();

			return "";

		}

		@Override
		protected void onPostExecute(String result) {

			conversionProgress.dismiss();
			// startanimation

			Log.d("done", "done");

			PointsImageView.startAnimation(true);

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
		}

		@Override
		protected void onPreExecute() {
			conversionProgress.setTitle("Processing Image");
			conversionProgress
					.setMessage("Please wait while we process your image ...");
			conversionProgress.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	// // /// / // / / / / / / / / / // / / / / / / / // / / /

	public void applyFilterstoLayers() {

		File sdCard = Environment.getExternalStorageDirectory();
		File filtersdir = new File(Environment.getExternalStorageDirectory()
				+ "/Tesseract/Layers/Filters/");

		filtersdir.mkdirs();

		// Find all the files in the folder..

		File[] files = seperatedLayersFolder.listFiles();

		Log.d(TAG, "Number of files:" + files.length);
		int count = 0;

		for (File file : files) {

			// Log.d("File path:","Path="+file.getPath());

			if (file.getName().toUpperCase().endsWith(("JPG"))
					|| file.getName().toUpperCase().endsWith(("PNG"))) {
				
				File dir = new File(sdCard.getAbsolutePath()
						+ "/Tesseract/Layers/Filters/" + count + "/");

				Log.d(TAG, "path" + dir.getPath());
				dir.mkdirs();

				applyInstagramEffectstoImage(file.getPath(),count);

				count++;
			}
		}
	}

	public void applyInstagramEffectstoImage(String imgFile,int folderNum) {
		// TODO Auto-generated method stub

		// Make an instagram version of the filters ..
		for (int i = 0; i < imageFilters.length; i++) {

			Bitmap tempbitmap = BitmapFactory.decodeFile(imgFile);

			// Apply Different Filters and save

			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			Bitmap mutableBitmap = tempbitmap.copy(Bitmap.Config.ARGB_8888,
					true);

			Canvas canvas = new Canvas(mutableBitmap);

			applySpecificFiltertoimage(imageFilters[i], mutableBitmap, canvas,
					cm, paint, folderNum);

		}

	}

	public void applySpecificFiltertoimage(String filterName,
			Bitmap canvas_bitmap, Canvas canvas, ColorMatrix cm, Paint paint,
			int i) {

		canvas.drawBitmap(canvas_bitmap, 0, 0, new Paint());

		if (filterName.equalsIgnoreCase("stark")) {

			Paint spaint = new Paint();
			ColorMatrix scm = new ColorMatrix();

			scm.setSaturation(0);

			final float m[] = scm.getArray();
			final float c = 1;

			scm.set(new float[] { m[0] * c, m[1] * c, m[2] * c, m[3] * c,
					m[4] * c + 15, m[5] * c, m[6] * c, m[7] * c, m[8] * c,
					m[9] * c + 8, m[10] * c, m[11] * c, m[12] * c, m[13] * c,
					m[14] * c + 10, m[15], m[16], m[17], m[18], m[19] });

			spaint.setColorFilter(new ColorMatrixColorFilter(scm));
			Matrix smatrix = new Matrix();
			canvas.drawBitmap(canvas_bitmap, smatrix, spaint);

			cm.set(new float[] { 1, 0, 0, 0, -90, 0, 1, 0, 0, -90, 0, 0, 1, 0,
					-90, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("sunnyside")) {

			cm.set(new float[] { 1, 0, 0, 0, 10, 0, 1, 0, 0, 10, 0, 0, 1, 0,
					-60, 0, 0, 0, 1, 0 });
		} else if (filterName.equalsIgnoreCase("worn")) {

			cm.set(new float[] { 1, 0, 0, 0, -60, 0, 1, 0, 0, -60, 0, 0, 1, 0,
					-90, 0, 0, 0, 1, 0 });
		} else if (filterName.equalsIgnoreCase("grayscale")) {

			float[] matrix = new float[] { 0.3f, 0.59f, 0.11f, 0, 0, 0.3f,
					0.59f, 0.11f, 0, 0, 0.3f, 0.59f, 0.11f, 0, 0, 0, 0, 0, 1,
					0, };

			cm.set(matrix);

		} else if (filterName.equalsIgnoreCase("cool")) {

			cm.set(new float[] { 1, 0, 0, 0, 10, 0, 1, 0, 0, 10, 0, 0, 1, 0,
					60, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter0")) {

			cm.set(new float[] { 1, 0, 0, 0, 30, 0, 1, 0, 0, 10, 0, 0, 1, 0,
					20, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter1")) {

			cm.set(new float[] { 1, 0, 0, 0, -33, 0, 1, 0, 0, -8, 0, 0, 1, 0,
					56, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter2")) {

			cm.set(new float[] { 1, 0, 0, 0, -42, 0, 1, 0, 0, -5, 0, 0, 1, 0,
					-71, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter3")) {

			cm.set(new float[] { 1, 0, 0, 0, -68, 0, 1, 0, 0, -52, 0, 0, 1, 0,
					-15, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter4")) {

			cm.set(new float[] { 1, 0, 0, 0, -24, 0, 1, 0, 0, 48, 0, 0, 1, 0,
					59, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter5")) {

			cm.set(new float[] { 1, 0, 0, 0, 83, 0, 1, 0, 0, 45, 0, 0, 1, 0, 8,
					0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter6")) {

			cm.set(new float[] { 1, 0, 0, 0, 80, 0, 1, 0, 0, 65, 0, 0, 1, 0,
					81, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter7")) {

			cm.set(new float[] { 1, 0, 0, 0, -44, 0, 1, 0, 0, 38, 0, 0, 1, 0,
					46, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("filter8")) {

			cm.set(new float[] { 1, 0, 0, 0, 84, 0, 1, 0, 0, 63, 0, 0, 1, 0,
					73, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("random")) {

			// pick an integer between -90 and 90 apply
			int min = -90;
			int max = 90;
			Random rand = new Random();

			int five = rand.nextInt(max - min + 1) + min;
			int ten = rand.nextInt(max - min + 1) + min;
			int fifteen = rand.nextInt(max - min + 1) + min;

			Log.d(TAG, "five " + five);
			Log.d(TAG, "ten " + ten);
			Log.d(TAG, "fifteen " + fifteen);

			cm.set(new float[] { 1, 0, 0, 0, five, 0, 1, 0, 0, ten, 0, 0, 1, 0,
					fifteen, 0, 0, 0, 1, 0 });

		} else if (filterName.equalsIgnoreCase("sepia")) {

			float[] sepMat = { 0.3930000066757202f, 0.7689999938011169f,
					0.1889999955892563f, 0, 0, 0.3490000069141388f,
					0.6859999895095825f, 0.1679999977350235f, 0, 0,
					0.2720000147819519f, 0.5339999794960022f,
					0.1309999972581863f, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 };
			cm.set(sepMat);
		}

		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		Matrix matrix = new Matrix();
		canvas.drawBitmap(canvas_bitmap, matrix, paint);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		canvas.drawText(filterName, (float) (100 - 5 * filterName.length()),
				80, paint);

		/* code... */

		String fileName = Environment.getExternalStorageDirectory()
				+ "/Tesseract/Layers/Filters/" + i + "/" + filterName + ".png";
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(fileName);
			/*
			 * Write bitmap to file using JPEG or PNG and 80% quality hint for
			 * JPEG.
			 */
			canvas_bitmap.compress(CompressFormat.PNG, 100, stream);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("Filter name", "path " + filterName);
	}

};