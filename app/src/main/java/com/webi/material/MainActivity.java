/*

Ramankit Singh

http://www.github.com/webianks

*/


package com.webi.material;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener {
	Button bt;
    ProgressBar pb;
    URLExistAsyncTask task;
    RelativeLayout rl,cl,rlBottom;
    TextView connectedTxt;
    ImageView iv;
    
    SpannableString connected,noAccess,connect,checking;
   
    
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
        	 getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2c3e50")));
       }
        
        
         bt=(Button) findViewById(R.id.but);
         pb=(ProgressBar) findViewById(R.id.pBar);  
         rl=(RelativeLayout) findViewById(R.id.rl);
         cl=(RelativeLayout) findViewById(R.id.colorRl);
         rlBottom=(RelativeLayout) findViewById(R.id.rlBottom);
         
         iv=(ImageView) findViewById(R.id.changeImg);
         //connectedImg=(ImageView) findViewById(R.id.connectedLogo);
         connectedTxt=(TextView) findViewById(R.id.connectedText);
         
         bt.setOnClickListener(this);
         
         connect = new SpannableString("Check");
         connect.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, connect.length(),
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
         
         checking = new SpannableString("Checking");
         checking.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, checking.length(),
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
         
         
         
         
         
         bt.setText(connect);
    }


	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {
	
		     
		      pb.setVisibility(View.VISIBLE);
		      rlBottom.setVisibility(View.INVISIBLE);
		      
		      
		      bt.setText(checking);
		      
		     
		      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
      			iv.setBackground(new ColorDrawable(Color.parseColor("#00ffffff")));
            }else {
            	 iv.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
            }
		      
		      
		     task= new URLExistAsyncTask();
	        String URL = "http://www.google.com";
	        task.execute(new String[]{URL});
		      
		      
	        connected = new SpannableString("Connected");
	        connected.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, connected.length(),
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	              

	        noAccess = new SpannableString("No Connection");
	        noAccess.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, noAccess.length(),
	                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
	        
	        
		      
		
	}
	
	
	

	
	
	public class URLExistAsyncTask extends AsyncTask<String, Void, Boolean> {
       // AsyncTaskCompleteListenere<Boolean> callback;

       // public URLExistAsyncTask(AsyncTaskCompleteListenere<Boolean> callback) {
       //    this.callback = callback;
       //}
	
		public URLExistAsyncTask(){
		}
		

        protected Boolean doInBackground(String... params) {
         	
        	
        	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
        	    if (netInfo != null && netInfo.isConnected()) {
        	        
        	    	URL url;
					
        	        try {
        	        	url = new URL(params[0]);
        	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        	            connection.setConnectTimeout(2000);
        	            connection.setReadTimeout(3000);
        	            connection.setRequestMethod("HEAD");
        	            int responseCode = connection.getResponseCode();
        	            return (200 <= responseCode && responseCode <= 399);
        	        } 
        	        catch (IOException exception) {
        	            return false;
        	        }

        	         
        	 }
        	    return false; 
        	    
                    	
           
            
        }

        @SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		protected void onPostExecute(Boolean result){
              //callback.onTaskComplete(result);
        	
        	pb.setVisibility(View.INVISIBLE);
        	rlBottom.setVisibility(View.VISIBLE);

        	if(result==true)
        	{
        		
        		bt.setText(connected);
        		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        			iv.setBackground(getResources().getDrawable(R.drawable.tick));
              }else {
        		iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.tick));
              }
        		
        		
        		cl.setBackgroundColor(Color.parseColor("#4CAF50"));
        		
        		if(isConnectedWifi(getApplicationContext())){
        			
        			
        			rlBottom.setBackgroundColor(Color.parseColor("#4CAF50"));
        			getApplicationContext();
					WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        			String ssid = wifiInfo.getSSID();
        			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        				//connectedImg.setBackground(getResources().getDrawable(R.drawable.wifi));
                  }else {
                	  //connectedImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi));
                  }
        			
        			
        			SpannableString ssidText = new SpannableString(ssid);
        			ssidText.setSpan(new TypefaceSpan(getApplicationContext(), "Roboto-Thin.ttf"), 0, ssidText.length(),
        		                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
        			
        		
        			connectedTxt.setText(ssidText);
        			
        		}else if(isConnectedMobile(getApplicationContext())){
        			
        			rlBottom.setBackgroundColor(Color.parseColor("#4CAF50"));
        			
        			TelephonyManager manager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        			String carrierName = manager.getNetworkOperatorName();
        			
        			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        				//connectedImg.setBackground(getResources().getDrawable(R.drawable.mdata));
                  }else {
                	  //connectedImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.mdata));
                  }
        			
        			
        			SpannableString carrierText = new SpannableString(carrierName);
        			carrierText.setSpan(new TypefaceSpan(getApplicationContext(), "Roboto-Thin.ttf"), 0, carrierText.length(),
        		                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
        			
        			connectedTxt.setText(carrierText);
        		}else{
        			rlBottom.setBackgroundColor(Color.parseColor("#4CAF50"));
        			SpannableString blueText = new SpannableString("via Bluetooth");
        			blueText.setSpan(new TypefaceSpan(getApplicationContext(), "Roboto-Thin.ttf"), 0, blueText.length(),
        		                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
        			
        			connectedTxt.setText(blueText);
        		}
        			
        		
        		
        	}
 
        	else
        	{
        		rlBottom.setVisibility(View.INVISIBLE);
        		bt.setText(noAccess);
        		cl.setBackgroundColor(Color.parseColor("#B71C1C"));
        		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        			iv.setBackground(getResources().getDrawable(R.drawable.disconnect));
              }else {
        		iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.disconnect));
              }
        		
        	}
        }
    }
	
	
	
	
	

	public class TypefaceSpan extends MetricAffectingSpan {
	    private final LruCache<String, Typeface> sTypefaceCache =
	            new LruCache<String, Typeface>(12);

	    private Typeface mTypeface;
	    public TypefaceSpan(Context context, String typefaceName) {
	        mTypeface = sTypefaceCache.get(typefaceName);

	        if (mTypeface == null) {
	            mTypeface = Typeface.createFromAsset(context.getApplicationContext()
	                    .getAssets(), String.format("fonts/%s", typefaceName));
	            sTypefaceCache.put(typefaceName, mTypeface);
	        }
	    }

	    @Override
	    public void updateMeasureState(TextPaint p) {
	        p.setTypeface(mTypeface);
	        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
	    }

	    @Override
	    public void updateDrawState(TextPaint tp) {
	        tp.setTypeface(mTypeface);
	        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
	    }
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		   MenuInflater inflater=getMenuInflater();
		    inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		if(item.getItemId()== R.id.about){
			Intent intent=new Intent(this,About.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
    
	public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
	
	
	 public static boolean isConnectedWifi(Context context){
	        NetworkInfo info = MainActivity.getNetworkInfo(context);
	        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
	    }

	    public static boolean isConnectedMobile(Context context){
	        NetworkInfo info = MainActivity.getNetworkInfo(context);
	        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	    }

}
