/*

Ramankit Singh

http://www.github.com/webianks

Manish Chaurasiya

http://www.github.com/manishSRM

*/


package com.webi.material;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    Button button;
    ProgressBar pb;
    URLExistAsyncTask task;
    RelativeLayout rl, cl, rlBottom;
    TextView connectedTxt,ctdTxtInCtd;
    ImageView iv;
    RelativeLayout mainContainerRl;
    Typeface font;

    SpannableString connected, noAccess, connect, checking;
    private RelativeLayout mainContainerErrorRl,mainContainerConnectedRl;
    Button bError;
    private Button bConnected;
    private Toolbar toolBar;
    private Toolbar toolBar2;
    private Toolbar toolBar3;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        toolBar=(Toolbar)findViewById(R.id.toolbar);
        toolBar2=(Toolbar)findViewById(R.id.toolbar2);
        toolBar3=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolBar);


        button = (Button) findViewById(R.id.but);
        button.setTransformationMethod(null);
        bError= (Button) findViewById(R.id.buttonError);
        bError.setTransformationMethod(null);
        bConnected= (Button) findViewById(R.id.buttonConnected);
        bConnected.setTransformationMethod(null);
        


        pb = (ProgressBar) findViewById(R.id.pBar);
        rl = (RelativeLayout) findViewById(R.id.rl);
        cl = (RelativeLayout) findViewById(R.id.colorRl);
        rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);

        mainContainerRl= (RelativeLayout) findViewById(R.id.main_containerRl);
        mainContainerErrorRl= (RelativeLayout) findViewById(R.id.main_container_errorRl);
        mainContainerConnectedRl= (RelativeLayout) findViewById(R.id.main_container_connectedRl);


        mainContainerRl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mainContainerRl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                circularReveal(mainContainerRl,700,00);
            }
        });



        iv = (ImageView) findViewById(R.id.changeImg);
        //connectedImg=(ImageView) findViewById(R.id.connectedLogo);
        connectedTxt = (TextView) findViewById(R.id.connectedText);
        ctdTxtInCtd=(TextView)findViewById(R.id.connectedTextInConnected);

        button.setOnClickListener(this);

        connect = new SpannableString("Check");
        connect.setSpan(new TypefaceSpan(this, "RobotoSlab-Regular.ttf"), 0, connect.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        checking = new SpannableString("Checking");
        checking.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, checking.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        font = Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab-Regular.ttf");
        bError.setTypeface(font);
        bConnected.setTypeface(font);
        button.setText(connect);
    }







    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void circularReveal(final View view, final long duration, long startDelay) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight());
        int cy = (view.getTop() + view.getBottom());

        // get the final radius for the clipping circle
        // int radius = Math.max(view.getWidth(), view.getHeight());
        int finalRadius = (int)Math.hypot(view.getRight(),view.getBottom());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        anim.setDuration(duration);
        anim.setStartDelay(startDelay);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        // make the view visible and start the animation

        anim.start();
    }





    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public void onClick(View arg0) {


        pb.setVisibility(View.VISIBLE);
        rlBottom.setVisibility(View.INVISIBLE);


        button.setText(checking);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            iv.setBackground(new ColorDrawable(Color.parseColor("#00ffffff")));
        } else {
            iv.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
        }


        task = new URLExistAsyncTask();
        String URL = "http://www.google.com";
        task.execute(new String[]{URL});


        connected = new SpannableString("Connected");
        connected.setSpan(new TypefaceSpan(this, "Roboto-Thin.ttf"), 0, connected.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




    }


    public class URLExistAsyncTask extends AsyncTask<String, Void, Boolean> {
        // AsyncTaskCompleteListenere<Boolean> callback;

        // public URLExistAsyncTask(AsyncTaskCompleteListenere<Boolean> callback) {
        //    this.callback = callback;
        //}

        public URLExistAsyncTask() {
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
                } catch (IOException exception) {
                    return false;
                }


            }
            return false;


        }

        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        protected void onPostExecute(Boolean result) {
            //callback.onTaskComplete(result);

            pb.setVisibility(View.INVISIBLE);
            rlBottom.setVisibility(View.VISIBLE);

            if (result == true) {

               /* button.setText(connected);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    iv.setBackground(getResources().getDrawable(R.drawable.tick));
                } else {
                    iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.tick));
                }


                cl.setBackgroundColor(Color.parseColor("#4CAF50"));*/

                circularReveal(mainContainerConnectedRl, 500, 00);
                setSupportActionBar(toolBar2);



                if (isConnectedWifi(getApplicationContext())) {


                    getApplicationContext();
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ssid = wifiInfo.getSSID();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        //connectedImg.setBackground(getResources().getDrawable(R.drawable.wifi));
                    } else {
                        //connectedImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi));
                    }


                    SpannableString ssidText = new SpannableString(ssid);
                    ssidText.setSpan(new TypefaceSpan(getApplicationContext(), "RobotoSlab-Regular.ttf"), 0, ssidText.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                    ctdTxtInCtd.setText(ssidText);

                } else if (isConnectedMobile(getApplicationContext())) {



                    TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    String carrierName = manager.getNetworkOperatorName();

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        //connectedImg.setBackground(getResources().getDrawable(R.drawable.mdata));
                    } else {
                        //connectedImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.mdata));
                    }


                    SpannableString carrierText = new SpannableString(carrierName);
                    carrierText.setSpan(new TypefaceSpan(getApplicationContext(), "RobotoSlab-Regular.ttf"), 0, carrierText.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    ctdTxtInCtd.setText(carrierText);




                } else {

                    SpannableString blueText = new SpannableString("via Bluetooth");
                    blueText.setSpan(new TypefaceSpan(getApplicationContext(), "RobotoSlab-Regular.ttf"), 0, blueText.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    ctdTxtInCtd.setText(blueText);
                }


            }

            else {

                //no internet access
                /*rlBottom.setVisibility(View.INVISIBLE);

                cl.setBackgroundColor(Color.parseColor("#B71C1C"));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    iv.setBackground(getResources().getDrawable(R.drawable.disconnect));
                } else {
                    iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.disconnect));
                }*/




                        circularReveal(mainContainerErrorRl,500,00);
                        setSupportActionBar(toolBar3);




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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub


        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = MainActivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = MainActivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

}
