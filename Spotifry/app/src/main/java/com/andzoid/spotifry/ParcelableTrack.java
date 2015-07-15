package com.andzoid.spotifry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabuzant on 13/07/2015.
 */
public class ParcelableTrack implements Parcelable {

    public String Name;
    public String Album;
    public String ImageUrl;

    public ParcelableTrack(String sName, String sAlbum, String sImageUrl) {

        this.Name = sName;
        this.Album = sAlbum;
        this.ImageUrl = sImageUrl;
    }

    private ParcelableTrack(Parcel p) {

        this.Name = p.readString();
        this.Album = p.readString();
        this.ImageUrl = p.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel p, int i) {

        p.writeString(this.Name);
        p.writeString(this.Album);
        p.writeString(this.ImageUrl);
    }

    public final Parcelable.Creator<ParcelableTrack> CREATOR = new Parcelable.Creator<ParcelableTrack>() {

        @Override
        public ParcelableTrack createFromParcel(Parcel p) { return new ParcelableTrack(p); }

        @Override
        public ParcelableTrack[] newArray(int i) { return new ParcelableTrack[i]; }
    };
}
