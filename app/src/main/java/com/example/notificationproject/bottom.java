package com.example.notificationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notificationproject.Notifications.ImagesActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottom extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    public static bottom newInstance(ItemClickListener mListener, int position) {
        return new bottom(mListener,position);
    }
    int position ;
    public bottom(ItemClickListener mListener, int position){
        this.mListener = mListener;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bottom, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.Bottom_FullPic).setOnClickListener(this );
        view.findViewById(R.id.Bottom_Share).setOnClickListener(this );


        view.findViewById(R.id.Bottom_Delete).setOnClickListener(this);
        view.findViewById(R.id.Bottom_Copy).setOnClickListener(this);
        view.findViewById(R.id.Bottom_Download).setOnClickListener(this);
        view.findViewById(R.id.Bottom_Bookmark).setOnClickListener(this);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
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

                switch(view.getId()){
                    case R.id.Bottom_FullPic:
                        mListener.onViewFullPic(position);
                        break;
                    case R.id.Bottom_Share:
                        mListener.onShare(position);
                        break;

                    case R.id.Bottom_Delete:
                        mListener.onDelete(position);
                        break;

                    case R.id.Bottom_Copy:
                        mListener.onCopy(position);
                        break;

                    case R.id.Bottom_Download:
                        mListener.onDownload(position);
                        break;
                    case R.id.Bottom_Bookmark:
                        mListener.onBookmark(position);
                        break;
                }
//        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }
    public interface ItemClickListener {
        void onViewFullPic(int position);
        void onDelete(int position);
        void onShare(int position);
        void onCopy(int position);
        void onDownload(int position);
        void onBookmark(int position);

    }
}