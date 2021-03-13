package com.inndex.retrofit.responseapifoursquare;

import java.io.Serializable;

public class ResponsePlaceApiFourSquare implements Serializable {

    PlaceVenusFourceSquare response;

    public ResponsePlaceApiFourSquare() {
    }

    public PlaceVenusFourceSquare getResponse() {
        return response;
    }

    public void setResponse(PlaceVenusFourceSquare response) {
        this.response = response;
    }
}
