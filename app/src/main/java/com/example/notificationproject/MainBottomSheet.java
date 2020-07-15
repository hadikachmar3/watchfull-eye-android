package com.example.notificationproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notificationproject.Requests.RequestUploads;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainBottomSheet extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private MainBottomSheet.ItemClickListener mListener;
    public static MainBottomSheet newInstance() {
        return new MainBottomSheet();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_bottom_sheet, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
        view.findViewById(R.id.ExitText).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                exit();
                dismiss();
            }
        });

    }


    public void exit() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    private void openImagesActivity() {
        Intent i = new Intent(getContext(), RequestUploads.class);
        startActivity(i);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainBottomSheet.ItemClickListener) {
            mListener = (MainBottomSheet.ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }
}