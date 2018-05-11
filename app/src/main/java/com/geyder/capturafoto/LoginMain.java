package com.geyder.capturafoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginMain extends Activity {
    private EditText editTextUser;
    private EditText editTextPass;
    private Button btnIniciar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmain);

        editTextUser = (EditText)findViewById(R.id.user);
        editTextPass = (EditText) findViewById(R.id.pass);
        btnIniciar = (Button)findViewById(R.id.login);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
    }

    private void gotoMain(){

        Intent intent = new Intent(this, Captura.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
