package com.example.notificationproject.Bookmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notificationproject.DirectoryHelper;
import com.example.notificationproject.DownloadSongService;
import com.example.notificationproject.ImageFull;
import com.example.notificationproject.Notifications.ImageAdapter;
import com.example.notificationproject.Notifications.ImagesActivity;
import com.example.notificationproject.Notifications.Upload;
import com.example.notificationproject.R;
import com.example.notificationproject.bottom;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener, Bookmarks_Bottom.ItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRefreq;
    private ValueEventListener mDBListener;


    private DatabaseReference mDatabaseRefBookmarks;
    private DatabaseReference mDatabaseRefRequests;


    //private List<Upload> mUploads;
    private List<Upload> mBookmarks;

    SwipeRefreshLayout swipeRefreshLayout;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        SwipeBack.attach(this, Position.LEFT)
                .setDrawOverlay(true)

                .setDividerEnabled(true) // Must be called to enable, setDivider() is not enough
                .setSwipeBackTransformer(new SlideSwipeBackTransformer())
                .setContentView(R.layout.activity_bookmarks)
                .setSwipeBackView(R.layout.swipeback_default);

        mDatabaseRefBookmarks = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRefRequests = FirebaseDatabase.getInstance().getReference("requestedimages");


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mBookmarks = new ArrayList<>();

        mAdapter = new ImageAdapter(getBaseContext(), mBookmarks);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        mStorage = FirebaseStorage.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRefreq=FirebaseDatabase.getInstance().getReference("requestedimages");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mBookmarks.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    if (upload.getBookmarks() == true) {
                        mBookmarks.add(upload);
                    }


                }


                Collections.reverse(mBookmarks);


//                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookmarksActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);

                swipeRefreshLayout.setRefreshing(false);


            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return;
        }
        DirectoryHelper.createDirectory(this);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }


    @Override
    public void onViewFullPic(int position) {

        Upload selectedItem = mBookmarks.get(position);
        String Url = selectedItem.getImageUrl();

        Intent intent = new Intent(getApplicationContext(), ImageFull.class);
        intent.putExtra("ImageUrl", Url);

        startActivity(intent);
//        AlertDialog.Builder alert = new AlertDialog.Builder(BookmarksActivity.this);
////        alert.setTitle("Your Title");
//
//
//        EditText editText = new EditText(this);
//        alert.setView(editText);
//
//        ImageView imageView = new ImageView(this);
//
//
//        Picasso.get()
//                .load(Url).fit()
//                .placeholder(R.drawable.progress_animationn)
//                .into(imageView);
//
//        int width= imageView.getMaxWidth();
//        int height=imageView.getMaxHeight();

//        alert.setView(imageView);
//
//
//        AlertDialog alertDialog = alert.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        alertDialog.show();

//        alertDialog.getWindow().setLayout(width, height);


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onRemoveBookmark(final int position) {
        final Upload selectedItem = mBookmarks.get(position);
        final String selectedKey = selectedItem.getKey();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.drawable.warning);
        builder.setIcon(R.drawable.alarm);
        builder.setTitle("Alert!");
        builder.setMessage("this picture will be deleted permanently")
                .setCancelable(false)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Upload selectedItem = mBookmarks.get(position);
                        String bookmarksValues = selectedItem.getKey();


                        selectedItem.setBookmarks(false);
                        mDatabaseRefBookmarks.child(bookmarksValues).child("bookmarks").setValue(false);
                        new StyleableToast
                         .Builder(getApplicationContext())

                                .text("Image removed")
                                .stroke(2, Color.parseColor("#90EE90"))
                                .textColor(Color.BLACK)
                                .backgroundColor(Color.WHITE)
                                .iconEnd(R.drawable.checked)
                                .show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();

        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red_A700));

    }

    @Override
    public void onShare(int position) {
        Upload selectedItem = mBookmarks.get(position);
        String Url = selectedItem.getImageUrl();


//        Intent myIntent = new Intent(Intent.ACTION_SEND);
//        myIntent.setType("text/plain");
//        String shareBody =Url;
//        String shareSub = Url;
//        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
//        myIntent.putExtra(Intent.EXTRA_TEXT,shareSub);
//        startActivity(Intent.createChooser(myIntent, "Share using"));


        Picasso.get()
                .load(Url)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
                        if (isExternalStorageWritable()) {
                            Uri uri = saveImage(bitmap);

                            shareImageUri(uri);
                        }
//                String pack = "Helloooooo";
//
//                PackageManager pm = ImagesActivity.this.getPackageManager();
//                try {
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    String path = MediaStore.Images.Media.insertImage(ImagesActivity.this.getContentResolver(), bitmap, "Title", null);
//
//                    Uri imageUri = Uri.parse(path);
//
////                    @SuppressWarnings("unused")
////                    PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);
//
//                    Intent waIntent = new Intent(Intent.ACTION_SEND);
//                    waIntent.setType("image/*");
////                    waIntent.setPackage(pack);
//                    waIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
//                    waIntent.putExtra(Intent.EXTRA_TEXT, pack);
//                    ImagesActivity.this.startActivity(Intent.createChooser(waIntent, "Share with"));
//                } catch (Exception e) {
//                    Log.e("Error on sharing", e + " ");
//                }
                    }

                    //
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    /**
     * Saves the image as PNG to the app's private external storage folder.
     *
     * @param image Bitmap to save.
     * @return Uri of the saved file or null
     */
    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("asd", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    /**
     * Checks if the external storage is writable.
     *
     * @return true if storage is writable, false otherwise
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private boolean downloadImage(Bitmap image) {
//        getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        File imagesFolder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            Toast.makeText(BookmarksActivity.this, "picture downloaded", Toast.LENGTH_SHORT).show();
            return true;
        } catch (IOException e) {
            Log.d("hhh", "IOException while trying to write file for sharing: " + e.getMessage());
        }

        return false;
    }

    /**
     * Saves the image as PNG to the app's cache directory.
     *
     * @param image Bitmap to save.
     * @return Uri of the saved file or null
     */
    private Uri saveImage(Bitmap image) {
        //TODO - Should be processed in another thread

        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.example.notificationproject", file);

        } catch (IOException e) {
            Log.d("hhh", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    /**
     * Shares the PNG image from Uri.
     *
     * @param uri Uri of image to share.
     */
    private void shareImageUri(Uri uri) {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            int asdf = 0;
            asdf += 45;
        }

//                            Intent waIntent = new Intent(Intent.ACTION_SEND);
//                    waIntent.setType("image/*");
////                    waIntent.setPackage(pack);
//                    waIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
////                    waIntent.putExtra(Intent.EXTRA_TEXT, pack);
//                    ImagesActivity.this.startActivity(Intent.createChooser(waIntent, "Share with"));
    }

    @Override
    public void onCopy(int position) {

        Upload selectedItem = mBookmarks.get(position);
        String Url = selectedItem.getImageUrl();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(Url);
            Toast.makeText(BookmarksActivity.this, "link copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", Url);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(BookmarksActivity.this, "link copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDownload(int position) {
        Upload selectedItem = mBookmarks.get(position);
        String Url = selectedItem.getImageUrl();


        startService(DownloadSongService.getDownloadService(this,
                Url,
                DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/")));
//        Picasso.get()
//                .load(Url)
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
////
//                        if (isExternalStorageWritable()) {
//                            downloadImage(bitmap);
      //                  }
//                String pack = "Helloooooo";
//
//                PackageManager pm = ImagesActivity.this.getPackageManager();
//                try {
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    String path = MediaStore.Images.Media.insertImage(ImagesActivity.this.getContentResolver(), bitmap, "Title", null);
//
//                    Uri imageUri = Uri.parse(path);
//
////                    @SuppressWarnings("unused")
////                    PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);
//
//                    Intent waIntent = new Intent(Intent.ACTION_SEND);
//                    waIntent.setType("image/*");
////                    waIntent.setPackage(pack);
//                    waIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
//                    waIntent.putExtra(Intent.EXTRA_TEXT, pack);
//                    ImagesActivity.this.startActivity(Intent.createChooser(waIntent, "Share with"));
//                } catch (Exception e) {
//                    Log.e("Error on sharing", e + " ");
//                }
//                    }
//
                    //
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
    }

//    @Override
//    public void onBookmark(int position) {
//
//        Upload selectedItem = mBookmarks.get(position);
//        String bookmarksValues = selectedItem.getKey();
//
//
//        selectedItem.setBookmarks(false);
//        mDatabaseRefBookmarks.child(bookmarksValues).child("bookmarks").setValue(false);
//
//
//    }

    @Override
    public void onItemClick(int position) {
        Bookmarks_Bottom addPhotoBottomDialogFragment =
                Bookmarks_Bottom.newInstance(this,position);
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                bottom.TAG);
    }
}