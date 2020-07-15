package com.example.notificationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class Tokenn extends AppCompatActivity {

     String token;
    TextView TokenTxtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokenn);
        TokenTxtView=findViewById(R.id.TokentxtView);
        token = FirebaseInstanceId.getInstance().getToken();
        TokenTxtView.setText(token);
    }

    public void TokenCopyBtn(View view) {
        ClipboardManager cm = (ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(TokenTxtView.getText());
        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}