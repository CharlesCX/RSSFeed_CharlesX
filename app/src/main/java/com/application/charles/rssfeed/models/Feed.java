package com.application.charles.rssfeed.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Feed implements Parcelable {

    private String mTitle;
    private String mUpdated;
    private String mIcon;
    private List<Entry> mEntries;

    public Feed() {
    }

    public Feed(
            String mTitle,
            String mUpdated,
            String mIcon,
            List<Entry> mEntries) {
        this.mTitle = mTitle;
        this.mUpdated = mUpdated;
        this.mIcon = mIcon;
        this.mEntries = mEntries;
    }

    protected Feed(Parcel in) {
        mTitle = in.readString();
        mUpdated = in.readString();
        mIcon = in.readString();
        mEntries = in.createTypedArrayList(Entry.CREATOR);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUpdated() {
        return mUpdated;
    }

    public void setmUpdated(String mUpdated) {
        this.mUpdated = mUpdated;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public List<Entry> getmEntries() {
        return mEntries;
    }

    public void setmEntries(List<Entry> mEntries) {
        this.mEntries = mEntries;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mUpdated);
        dest.writeString(mIcon);
        dest.writeTypedList(mEntries);
    }
}
