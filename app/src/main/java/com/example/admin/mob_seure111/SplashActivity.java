package com.example.admin.mob_seure111;


import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final String is_Reg = Common.getPreferenceString(getApplicationContext(),"is_Reg","");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(is_Reg.equals("1")) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    return;
                } else {
                    Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        }, 3000);
    }
}
