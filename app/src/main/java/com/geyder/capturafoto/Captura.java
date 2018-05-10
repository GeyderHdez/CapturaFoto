package com.geyder.capturafoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.AbsSavedState;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import static android.graphics.BitmapFactory.decodeFile;

public class Captura extends Activity {

    private Button btnCapturar;
    private ImageView img;
    Guardar guardar = new Guardar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);

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

            Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Capturas/"+"foto.jpg");
            img.setImageBitmap(image);
            guardar.SaveImage(this.getBaseContext(), image );
        }
    }
}
