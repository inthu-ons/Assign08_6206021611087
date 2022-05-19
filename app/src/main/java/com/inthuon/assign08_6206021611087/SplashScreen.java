package com.inthuon.assign08_6206021611087;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e) {}

                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        }).start();
    }
}
