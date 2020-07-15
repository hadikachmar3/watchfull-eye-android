package com.example.notificationproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class ShareTask2 extends AsyncTask<String, Void, File> {

    private  Context context;

    public ShareTask2(Context context) {
        this.context = context;
    }
    public ShareTask2() {

    }


    @Override
    protected File doInBackground(String... params) {
        String url = params[0]; // should be easy to extend to share multiple images at once
        try {
            return Glide
                    .with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get() // needs to be called on background thread
                    ;
        } catch (Exception ex) {
            Log.w("SHARE", "Sharing " + " failed", ex);
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        Uri uri = FileProvider.getUriForFile(context, "com.juanito21.getPic.fileprovider", result);
        share(uri); // startActivity probably needs UI thread
    }

    public  void share(Uri result) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Shared image");
        intent.putExtra(Intent.EXTRA_TEXT, "Look what I found!");
        intent.putExtra(Intent.EXTRA_STREAM, result);
        context.startActivity(Intent.createChooser(intent, "Share image"));
    }


}