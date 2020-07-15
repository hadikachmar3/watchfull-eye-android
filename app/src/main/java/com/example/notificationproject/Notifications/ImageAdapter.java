package com.example.notificationproject.Notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.notificationproject.R;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;



    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }



    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);

        holder.Mtime.setText(uploadCurrent.getMtime());

      //  .placeholder(R.drawable.progress_animationn)
//        holder.textViewName.setText(uploadCurrent.getName());
//
//        Picasso.get()
//                .load(uploadCurrent.getImageUrl())
//                .placeholder(R.drawable.loading)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);
        Picasso.get().load(uploadCurrent.getImageUrl()).fit()
            .centerCrop().into(holder.imageView,
                new Callback.EmptyCallback(){
                    @Override
                    public void onSuccess(){
//                        holder.progressBar.setVisibility(View.GONE);
//                        MaterialImageLoading.animate(holder.imageView).setDuration(5000).start();
                    }
                });


    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //        public TextView textViewName;
        public ImageView imageView;
        public TextView Mtime;
        private ImageButton OpenMenu;
        private ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);

        progressBar =itemView.findViewById(R.id.homeprogress);
//            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            Mtime = itemView.findViewById(R.id.timetxt);
            OpenMenu =itemView.findViewById(R.id.openMenu);
//            DeletePic = itemView.findViewById(R.id.onDeletePic);
            OpenMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

}
