package com.exxonmobil.mobapp.app;

import java.io.Serializable;

/**
 * Created by El Nakeeb on 8/8/2016.
 */
public class PromoObj implements Serializable{
    private String message;

    private String image;

    public PromoObj(String message, String image) {
        this.message = message;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
