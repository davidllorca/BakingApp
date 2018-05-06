package me.example.davidllorca.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david on 22/4/18.
 */

/**
 * Model representing a Step data.
 */
public class Step implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnail;

    public Step() {
    }

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnail = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Step)) return false;

        Step step = (Step) o;

        return getId() == step.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    class Columns {
        static final String ID = "id";
        static final String SHORT_DESCRIPTION = "shortDescription";
        static final String DESCRIPTION = "description";
        static final String VIDEO_URL = "videoURL";
        static final String THUMBNAIL_URL = "thumbnailURL";
    }
}
