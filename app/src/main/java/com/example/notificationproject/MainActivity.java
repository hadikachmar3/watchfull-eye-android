package com.example.notificationproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.notificationproject.Bookmarks.BookmarksActivity;
import com.example.notificationproject.Notifications.ImagesActivity;
import com.example.notificationproject.Notifications.ImagesUpload;
import com.example.notificationproject.Requests.RequestUploads;
import com.example.notificationproject.Requests.RequestedImagesDisplay;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rhexgomez.typer.roboto.TyperRoboto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainBottomSheet.ItemClickListener {


    TextView text1;
    GridLayout mainGrid;
//    CardView cardviewLayout;
    CoordinatorLayout coordinatorLayout;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
//
//        //Set Event
//        setSingleEvent(mainGrid);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        TextView textgrid = findViewById(R.id.textGrid);
//
//Change Icons colors

//        navigationView.getMenu()
//                .findItem(R.id.nav_home)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_Bookmarks)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_notificationss)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_Requets)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_Requets)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_emergency)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_FeedBack)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_share)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.grey_600), PorterDuff.Mode.SRC_IN);
//        navigationView.getMenu()
//                .findItem(R.id.nav_exit)
//                .getIcon()
//                .setColorFilter(getResources().getColor(R.color.red_600), PorterDuff.Mode.SRC_IN);



//        final Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/aclonica");
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.CollapsingBar);
//        collapsingToolbar.setCollapsedTitleTypeface(TyperRoboto.ROBOTO_THIN_ITALIC());

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbar.setExpandedTitleMarginStart(60);

//        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
//        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
//        collapsingToolbar.setExpandedTitleTypeface(TyperRoboto.ROBOTO_LIGHT_ITALIC());
    }

    // CardView Function
    public void Notification(View v){
        LoadImages();
    }
    public void CallSomeone(View v){
        Call();
    }

    public void SendRequest(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseRef = database.getReference();
        mDatabaseRef.child("request").setValue("t");
//                        Toast.makeText(MainActivity.this, "Request sent", Toast.LENGTH_SHORT).show();
        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.main1);
//        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main1);
        Snackbar snackbar = Snackbar.make(scrollView, "Request sent", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Snackbar snackbar1 = Snackbar.make(cardviewLayout, "Undo successful", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
                        RequestIntent();
                    }
                })
                .setActionTextColor(Color.RED);


        scrollView.post(new Runnable() {
            @Override
            public void run() {
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                scrollView.scrollTo(0, scrollView.getBottom());
            }
        });

        View snackView = snackbar.getView();
        TextView textView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setDuration(20000);
        snackbar.show();
    }

    public void BookmarksIntent(){
        Intent i = new Intent(getApplicationContext(),BookmarksActivity.class);
        startActivity(i);
    }
    public void Bookmarksbt (View v){
        Intent i = new Intent(getApplicationContext(),BookmarksActivity.class);
        startActivity(i);
    }
    public void ViewRequest(View v){
        RequestIntent();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

//    private void setSingleEvent(GridLayout mainGrid) {
//        //Loop all child item of Main Grid
//        for (int i = 0; i < mainGrid.getChildCount(); i++) {
//            //You can see , all child item is CardView , so we just cast object to CardView
//            CardView cardView = (CardView) mainGrid.getChildAt(i);
//            final int finalI = i;
//
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (finalI == 0) {
//                        LoadImages();
//                    } else if (finalI == 1) {
//                        Call();
//                    } else if (finalI == 2) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference mDatabaseRef = database.getReference();
//                        mDatabaseRef.child("request").setValue("t");
////                        Toast.makeText(MainActivity.this, "Request sent", Toast.LENGTH_SHORT).show();
//                        showSnackbar();
//
//                    } else {
//                        RequestIntent();
//                    }
//
//                }
//            });
//        }
//    }

//    public void showSnackbar() {
//
//        cardviewLayout = findViewById(R.id.main1);
//        Snackbar snackbar = Snackbar.make(cardviewLayout, "Request sent", Snackbar.LENGTH_INDEFINITE)
//                .setAction("OK", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        Snackbar snackbar1 = Snackbar.make(cardviewLayout, "Undo successful", Snackbar.LENGTH_SHORT);
////                        snackbar1.show();
//                        RequestIntent();
//                    }
//                })
//                .setActionTextColor(Color.RED);
//
//        View snackView = snackbar.getView();
//        TextView textView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//
//        snackbar.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        MainBottomSheet addPhotoBottomDialogFragment =
//                MainBottomSheet.newInstance();
//        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
//                MainBottomSheet.TAG);

        if (id == R.id.action_settings) {
            SettingsActivity();
        }
        if (id == R.id.action_about_us) {
            AboutUs();
        }
        if (id == R.id.action_UploadTest) {
            UplaodImages();
        }
        if (id == R.id.action_RequestTest) {
            RequestsActivity();
        }
        if (id == R.id.Token_menu) {
            startActivity(new Intent(this , Tokenn.class));
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_notificationss) {
            LoadImages();
        } else if (id == R.id.nav_testing) {
            UplaodImages();
        } if (id == R.id.nav_Requets) {
            RequestIntent();
        } if (id == R.id.nav_Bookmarks) {
            BookmarksIntent();
        } else if (id == R.id.nav_emergency) {

            EmergencyCall();
        } else if (id == R.id.nav_FeedBack) {
            Feedback();
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_exit) {
            exit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void RequestsActivity(){
        Intent i = new Intent(getApplicationContext(), RequestUploads.class);
        startActivity(i);
    }
    public void exit() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void LoadImages() {
        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
        startActivity(i);
    }

    public void UplaodImages() {
        Intent i = new Intent(getApplicationContext(), ImagesUpload.class);
        startActivity(i);
    }

    public void Feedback() {
        Intent i = new Intent(getApplicationContext(), Feedback.class);
        startActivity(i);
    }

    public void share() {
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "My App Link");
//        sendIntent.setType("text/plain");
//        startActivity(sendIntent);

        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "My App Link";
        String shareSub = "My App Link";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
        startActivity(Intent.createChooser(myIntent, "Share using"));

    }

    public void EmergencyCall() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Emergency call");
        builder.setMessage("Do you really wanna call the emergency?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", String.valueOf(112), null)));
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public void Call() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Make Call");
        alertDialog.setMessage("Type a phone number");

        final EditText input = new EditText(MainActivity.this);
        input.setHint("Phone Number");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.telephone);

        alertDialog.setPositiveButton("Call",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString() != "") {
                            String PhoneNumber = input.getText().toString();

                            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                String dial = "tel:" + PhoneNumber;
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            } else {

                                Toast.makeText(MainActivity.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 15);
                            }

//                            String PhoneNumber = input.getText().toString();
//                            Intent intent = new Intent(Intent.ACTION_CALL);
//
//                            intent.setData(Uri.parse("tel:" + PhoneNumber));
//                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("Contacts",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_DEFAULT, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });

        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        alertDialog.show();
       


    }


    public void RequestIntent() {

        startActivity(new Intent(this, RequestedImagesDisplay.class));
    }


    @Override
    public void onItemClick(String item) {

    }

    public void AboutUs() {
        Intent i = new Intent(getApplicationContext(), AboutApp.class);
        startActivity(i);
    }
    public void SettingsActivity() {
        Intent i = new Intent(getApplicationContext(), Settings.class);
        startActivity(i);
    }
}