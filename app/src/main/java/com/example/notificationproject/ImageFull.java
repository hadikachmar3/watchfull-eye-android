package com.example.notificationproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.imid.swipebacklayout.lib.SwipeBackLayout;


public class ImageFull extends  AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private View optionsView;
    private AlertDialog optionsDialog;
    ZoomageView imageView;
    RelativeLayout relativeLayout;
    private SwipeBackLayout mSwipeBackLayout;
 //   private SlidrInterface slidr;
    ProgressBar loader;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);
        Toolbar toolbar = findViewById(R.id.toolbar_fullImage);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        SwipeBack.attach(this, Position.LEFT)
                .setDrawOverlay(true)

                .setDividerEnabled(true) // Must be called to enable, setDivider() is not enough
                .setSwipeBackTransformer(new SlideSwipeBackTransformer())
                .setContentView(R.layout.activity_image_full)
                .setSwipeBackView(R.layout.swipeback_default);


        Intent intent = getIntent();
         str = intent.getStringExtra("ImageUrl");
        loader=findViewById(R.id.loader);
      //  mSwipeBackLayout = getSwipeBackLayout();
        imageView = (ZoomageView) findViewById(R.id.image_Full);
        prepareOptions();
        Picasso.get()
                .load(str).fit()
//                .placeholder(R.drawable.progress_animationn)
                .into(imageView);


//        Glide.with(this)
//                .load(str).into(imageView);
       // loader.setVisibility(View.GONE);




        //        linearLayout=findViewById(R.id.full_screen_layout);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });


    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (!optionsDialog.isShowing()) {
            optionsDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareOptions() {
        optionsView = getLayoutInflater().inflate(R.layout.zoomage_options, null);
        setSwitch(R.id.zoomable, imageView.isZoomable());
        setSwitch(R.id.translatable, imageView.isTranslatable());
        setSwitch(R.id.animateOnReset, imageView.getAnimateOnReset());
        setSwitch(R.id.autoCenter, imageView.getAutoCenter());
        setSwitch(R.id.restrictBounds, imageView.getRestrictBounds());
        optionsView.findViewById(R.id.reset).setOnClickListener(this);
        optionsView.findViewById(R.id.autoReset).setOnClickListener(this);

//        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/amaranth.xml");

        optionsDialog = new AlertDialog.Builder(this).setTitle("Zoom setting")
                .setView(optionsView)

                .setPositiveButton("Close", null)
                .create();
        optionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }

    public void Zoom(View view) {
//        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
//        imageView.startAnimation(aniSlide);
    }
    private void setSwitch(int id, boolean state) {
        final SwitchCompat switchView = optionsView.findViewById(id);
        switchView.setOnCheckedChangeListener(this);
        switchView.setChecked(state);
    }



    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.zoomable:
                imageView.setZoomable(isChecked);
                break;
            case R.id.translatable:
                imageView.setTranslatable(isChecked);
                break;
            case R.id.restrictBounds:
                imageView.setRestrictBounds(isChecked);
                break;
            case R.id.animateOnReset:
                imageView.setAnimateOnReset(isChecked);
                break;
            case R.id.autoCenter:
                imageView.setAutoCenter(isChecked);
                break;
        }
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.reset) {
            imageView.reset();
        }
        else {
            showResetOptions();
        }
    }

    private void showResetOptions() {
        CharSequence[] options = new CharSequence[]{"Under", "Over", "Always", "Never"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                imageView.setAutoResetMode(which);
            }
        });

        builder.create().show();
    }

    public void backFull(View view) {
        super.onBackPressed();
    }

    public void FullImageSettings(View view) {
        optionsDialog.show();
    }




    public void FullImageSave(View view) {



    }
}