//package com.example.notificationproject.Bookmarks;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.notificationproject.R;
//import com.github.florent37.materialimageloading.MaterialImageLoading;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class BookmarksAdapters extends RecyclerView.Adapter<BookmarksAdapters.ImageViewHolder> {
//    private Context mContext;
//    private List<BookmarksValues> mBookmarks;
//    private OnItemClickListener mListener;
//
//
//
//    public BookmarksAdapters(Context context, List<BookmarksValues> bookmarksValues) {
//        mContext = context;
//        mBookmarks = bookmarksValues;
//    }
//
//    @Override
//    public BookmarksAdapters.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
//        return new BookmarksAdapters.ImageViewHolder(v);
//
//    }
//
//    @Override
//    public void onBindViewHolder(final ImageViewHolder holder, int position) {
//        BookmarksValues uploadCurrent = mBookmarks.get(position);
//
//        holder.Mtime.setText(uploadCurrent.getMtime());
////        holder.textViewName.setText(uploadCurrent.getName());
////
////        Picasso.get()
////                .load(uploadCurrent.getImageUrl())
////                .placeholder(R.drawable.loading)
////                .fit()
////                .centerCrop()
////                .into(holder.imageView);
//        Picasso.get().load(uploadCurrent.getImageUrl()).fit()
//                .centerCrop().placeholder(R.drawable.progress_animationn).into(holder.imageView,
//                new Callback.EmptyCallback(){
//                    @Override
//                    public void onSuccess(){
//                        MaterialImageLoading.animate(holder.imageView).setDuration(5000).start();
//                    }
//                });
//
//
//    }
//
//
//    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        //        public TextView textViewName;
//        public ImageView imageView;
//        public TextView Mtime;
//        private ImageButton OpenMenu;
//
//        public ImageViewHolder(View itemView) {
//            super(itemView);
//
//
////            textViewName = itemView.findViewById(R.id.text_view_name);
//            imageView = itemView.findViewById(R.id.image_view_upload);
//            Mtime = itemView.findViewById(R.id.timetxt);
//            OpenMenu =itemView.findViewById(R.id.openMenu);
////            DeletePic = itemView.findViewById(R.id.onDeletePic);
//            OpenMenu.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mListener != null) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    mListener.onItemClick(position);
//                }
//            }
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mBookmarks.size();
//    }
//
//    public void setOnItemClickListener(OnItemClickListener  listener) {
//        mListener = listener;
//
//    }
//
//}
