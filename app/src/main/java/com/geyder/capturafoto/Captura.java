package com.geyder.capturafoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.AbsSavedState;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import static android.graphics.BitmapFactory.decodeFile;

public class Captura extends AppCompatActivity {

    private SharedPreferences preferences;
    private Button btnCapturar;
    private ImageView img;
    Guardar guardar = new Guardar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        img = (ImageView) this.findViewById(R.id.foto);
        btnCapturar = (Button) this.findViewById(R.id.capturar);

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dirCaptura = new File(Environment.getExternalStorageDirectory(), "Capturas");
                dirCaptura.mkdir();

                File imagen = new File(dirCaptura, "foto.jpg" );
                Uri uriSavedImage;

                if (Build.VERSION.SDK_INT >= 24){
                    uriSavedImage = FileProvider.getUriForFile(Captura.this, BuildConfig.APPLICATION_ID + ".provider",imagen);
                }
                else {
                    uriSavedImage = Uri.fromFile(imagen);
                }

                camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(camaraIntent, 1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){

            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Capturas/"+"foto.jpg");
            Bitmap verticalImage = Bitmap.createBitmap(image,0,0, image.getWidth(), image.getHeight(), matrix, true);
            img.setImageBitmap(verticalImage);
            guardar.SaveImage(this.getBaseContext(), verticalImage );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                LogOut();
                return true;

            case  R.id.forgte_logout:
                RemovePreferences();
                LogOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void LogOut(){
        Intent intent = new Intent(this, LoginMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void RemovePreferences(){
        //preferences.edit().clear().apply();
        Util.RemovePrefs(preferences, "user");
        Util.RemovePrefs(preferences, "pass");
    }
}
