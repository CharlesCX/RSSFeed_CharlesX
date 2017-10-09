package com.application.charles.rssfeed.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Entry implements Parcelable {

    private String mId;
    private String mTitle;
    private String mName;
    private String mLink;
    private String mPreviewLink;
    private String mArtist;
    private String mPrice;
    private String mImage;
    private String mRight;
    private String mContent;
    private boolean isClicked;

    public Entry() {
    }

    public Entry(
            String mId,
            String mTitle,
            String mName,
            String mLink,
            String mPreviewLink,
            String mArtist,
            String mPrice,
            String mImage,
            String mRight,
            String mContent) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mName = mName;
        this.mLink = mLink;
        this.mPreviewLink = mPreviewLink;
        this.mArtist = mArtist;
        this.mPrice = mPrice;
        this.mImage = mImage;
        this.mRight = mRight;
        this.mContent = mContent;
    }

    public Entry(Entry entry) {
        this.mId = entry.getmId();
        this.mTitle = entry.getmTitle();
        this.mName = entry.getmName();
        this.mLink = entry.getmLink();
        this.mPreviewLink = entry.getmPreviewLink();
        this.mArtist = entry.getmArtist();
        this.mPrice = entry.getmPrice();
        this.mImage = entry.getmImage();
        this.mRight = entry.getmRight();
        this.mContent = entry.getmContent();
    }

    protected Entry(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mName = in.readString();
        mLink = in.readString();
        mPreviewLink = in.readString();
        mArtist = in.readString();
        mPrice = in.readString();
        mImage = in.readString();
        mRight = in.readString();
        mContent = in.readString();
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmPreviewLink() {
        return mPreviewLink;
    }

    public void setmPreviewLink(String mPreviewLink) {
        this.mPreviewLink = mPreviewLink;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmRight() {
        return mRight;
    }

    public void setmRight(String mRight) {
        this.mRight = mRight;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mName);
        dest.writeString(mLink);
        dest.writeString(mPreviewLink);
        dest.writeString(mArtist);
        dest.writeString(mPrice);
        dest.writeString(mImage);
        dest.writeString(mRight);
        dest.writeString(mContent);
    }
}
