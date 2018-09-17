package com.example.user.beatz_final.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.user.beatz_final.MainActivity;
import com.example.user.beatz_final.R;

public class SplashScreenActivity extends Activity {

    ImageView imageView;
    EditText txtAppName;
    RelativeLayout relativeLayout;
    Thread SplashThread;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg)
        {
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(i);
            SplashScreenActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtAppName = (EditText) findViewById(R.id.txtAppName);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("first_time", false))
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_time", true);
            editor.commit();
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            this.startActivity(i);
            this.finish();
        }
        else
        {
            this.setContentView(R.layout.activity_splash_screen);
            handler.sendEmptyMessageDelayed(0, 2000);
        }

            startAnimations();

    }


    private void startAnimations() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);

        rotate.reset();
        translate.reset();
        relativeLayout.clearAnimation();

        imageView.startAnimation(rotate);
        txtAppName.startAnimation(translate);
        SplashThread = new Thread() {
            @Override
            public void run() {
                super.run();
                int waited = 0;
                while (waited < 3500) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                SplashScreenActivity.this.finish();
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        };
        SplashThread.start();
    }
}
