package com.inndex.car.personas.retrofit.responseapifoursquare;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LocationResposePlaceFourSquare implements Serializable, Parcelable {

    Double lat;
    Double lng;
    String id;
    String name;
    String adrees;

    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAdrees() {
        return adrees;
    }

    public void setAdrees(String adrees) {
        this.adrees = adrees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationResposePlaceFourSquare() {

    }

    protected LocationResposePlaceFourSquare(Parcel in) {
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lng = null;
        } else {
            lng = in.readDouble();
        }
    }

    public static final Creator<LocationResposePlaceFourSquare> CREATOR = new Creator<LocationResposePlaceFourSquare>() {
        @Override
        public LocationResposePlaceFourSquare createFromParcel(Parcel in) {
            return new LocationResposePlaceFourSquare(in);
        }

        @Override
        public LocationResposePlaceFourSquare[] newArray(int size) {
            return new LocationResposePlaceFourSquare[size];
        }
    };

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (lat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lat);
        }
        if (lng == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lng);
        }
    }
}
