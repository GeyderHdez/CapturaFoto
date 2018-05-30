package com.geyder.capturafoto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

public class Splash extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        if (!TextUtils.isEmpty(Util.getPrefs(preferences, "user")) && !TextUtils.isEmpty(Util.getPrefs(preferences, "pass"))){
            Intent intent = new Intent(this, Captura.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginMain.class);
            startActivity(intent);
        }
        finish();
    }

}
