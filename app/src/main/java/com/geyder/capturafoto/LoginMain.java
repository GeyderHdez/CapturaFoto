package com.geyder.capturafoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class LoginMain extends Activity {
    private EditText editTextUser;
    private EditText editTextPass;
    private Button btnIniciar;
    private Switch recordar;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmain);

        editTextUser = (EditText)findViewById(R.id.user);
        editTextPass = (EditText) findViewById(R.id.pass);
        btnIniciar = (Button)findViewById(R.id.login);
        recordar = (Switch)findViewById(R.id.switchRecordar);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        String user = Util.getPrefs(preferences, "user");
        String pass = Util.getPrefs(preferences, "pass");

        editTextUser.setText(user);
        editTextPass.setText(pass);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
                saveOnPreferences(editTextUser.getText().toString(), editTextPass.getText().toString());
            }
        });
    }

    private void saveOnPreferences(String user, String pass){
        if (recordar.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user", user);
            editor.putString("pass", pass);
            editor.commit();
            editor.apply();
        }
    }

    private void gotoMain(){
        Intent intent = new Intent(this, Captura.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
