package com.inndex.retrofit.responseapifoursquare;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class PlaceVenusFourceSquare implements Serializable, Parcelable {
    List<LocationResposePlaceFourSquare> venues;

    protected PlaceVenusFourceSquare() {
    }

    public static final Creator<PlaceVenusFourceSquare> CREATOR = new Creator<PlaceVenusFourceSquare>() {
        @Override
        public PlaceVenusFourceSquare createFromParcel(Parcel in) {
            return new PlaceVenusFourceSquare();
        }

        @Override
        public PlaceVenusFourceSquare[] newArray(int size) {
            return new PlaceVenusFourceSquare[size];
        }
    };

    public List<LocationResposePlaceFourSquare> getPlaces() {
        return venues;
    }

    public void setPlaces(List<LocationResposePlaceFourSquare> places) {
        this.venues = places;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
