package it.jaschke.alexandria.services;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import it.jaschke.alexandria.activity.MainActivity;

/**
 * Created by saj on 11/01/15.
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    String path = "/data/data/it.jaschke.alexandria/app_images/";
    Context context;

    public DownloadImage(ImageView bmImage, Context c) {
        this.bmImage = bmImage;
        context = c;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        String booktitle = urls[1];
        Bitmap bookCover = loadfromStorage(booktitle);
        if(bookCover == null){
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bookCover = BitmapFactory.decodeStream(in);
                savetoStorage(bookCover, booktitle);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        return bookCover;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

    private Bitmap loadfromStorage(String image_name){
        Bitmap bookcover = null;
        try {
            File f = new File(path, image_name);
            bookcover = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            Log.d("DEBUG", "FileNotFound");
        }
        return bookcover;
    }

    private void savetoStorage(Bitmap bookcover, String image_name){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(path, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, image_name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bookcover.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

