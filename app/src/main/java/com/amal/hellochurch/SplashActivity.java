package com.amal.hellochurch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splashscreen);
//    Intent intent = new Intent(SplashActivity.this, ActivatorActivity.class);
//    startActivity(intent);
//  }
//}
    int secondsDelayed = 3;
    new Handler().postDelayed(new Runnable() {
      public void run() {
        startActivity(new Intent(SplashActivity.this, ActivatorActivity.class));
        finish();
      }
    }, secondsDelayed * 1000);
  }
}