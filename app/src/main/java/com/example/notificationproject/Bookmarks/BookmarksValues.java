//package com.example.notificationproject.Bookmarks;
//
//import com.google.firebase.database.Exclude;
//
//public class BookmarksValues {
//
//
//    private String mName;
//    private String mImageUrl;
//    private String mKey;
//    private String mtime;
//
//    public BookmarksValues() {
//        //empty constructor needed
//    }
//
//    public BookmarksValues(String name, String imageUrl) {
//        if (name.trim().equals("")) {
//            name = "No Name";
//        }
//
//        mName = name;
//        mImageUrl = imageUrl;
//    }
//
//    public String getName() {
//        return mName;
//    }
//
//    public void setName(String name) {
//        mName = name;
//    }
//
//    public String getImageUrl() {
//        return mImageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        mImageUrl = imageUrl;
//    }
//
//    @Exclude
//    public String getKey() {
//        return mKey;
//    }
//
//    @Exclude
//    public void setKey(String key) {
//        mKey = key;
//    }
//
//    public String getMtime() {
//        return mtime;
//    }
//
//    public void setMtime(String time) {
//        mtime = time;
//    }
//
//}
