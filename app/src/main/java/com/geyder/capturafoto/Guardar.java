package com.geyder.capturafoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Guardar {

    private Context context;
    private String directorio = "/Capturas";
    private String archivo = "foto";

    public void SaveImage(Context context, Bitmap ImageToSave) {
        this.context = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + directorio;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, archivo + CurrentDateAndTime + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }
        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }
    }
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(context,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    private void UnableToSave() {
        Toast.makeText(context, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }
    private void AbleToSave() {
        Toast.makeText(context, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
