package com.andzoid.spotifry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nabuzant on 13/07/2015.
 */
public class ParcelableArtist implements Parcelable {

    public String ID;
    public String Name;
    public String ImageUrl;

    public ParcelableArtist(String sID, String sName, String sImageUrl) {

        this.ID = sID;
        this.Name = sName;
        this.ImageUrl = sImageUrl;
    }

    private ParcelableArtist(Parcel p) {

        this.ID = p.readString();
        this.Name = p.readString();
        this.ImageUrl = p.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel p, int i) {

        p.writeString(this.ID);
        p.writeString(this.Name);
        p.writeString(this.ImageUrl);
    }

    public final Parcelable.Creator<ParcelableArtist> CREATOR = new Parcelable.Creator<ParcelableArtist>() {

        @Override
        public ParcelableArtist createFromParcel(Parcel p) { return new ParcelableArtist(p); }

        @Override
        public ParcelableArtist[] newArray(int i) { return new ParcelableArtist[i]; }
    };
}
