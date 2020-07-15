package com.example.notificationproject.Requests;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationproject.R;
import com.example.notificationproject.Notifications.Upload;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;


    public RequestAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public RequestAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RequestAdapter.ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);

        holder.Mtime.setText(uploadCurrent.getMtime());
//        holder.textViewName.setText(uploadCurrent.getName());

//        Picasso.get()
//                .load(uploadCurrent.getImageUrl())
//                .placeholder(R.drawable.loading)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);
        Picasso.get().load(uploadCurrent.getImageUrl()).fit()
                .centerCrop().placeholder(R.drawable.progress_animationn).into(holder.imageView,
                new Callback.EmptyCallback(){
                    @Override
                    public void onSuccess(){
                        MaterialImageLoading.animate(holder.imageView).setDuration(5000).start();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        public TextView textViewName;
        public ImageView imageView;
        public TextView Mtime;
        private ImageButton OpenMenu;

        public ImageViewHolder(View itemView) {
            super(itemView);


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

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.setHeaderTitle("Select Action");
//            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
//            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");
//
//            doWhatever.setOnMenuItemClickListener(this);
//            delete.setOnMenuItemClickListener(this);
//        }

//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            if (mListener != null) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//
//                    switch (item.getItemId()) {
//                        case 1:
//                            mListener.onWhatEverClick(position);
//                            return true;
//                        case 2:
//                            mListener.onDeleteClick(position);
//                            return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener =  listener;
    }
}
