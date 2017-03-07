package com.traversoft.favebakes.models;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Bakery {

    @Getter @Setter private String bakeryId;
    @Getter @Setter private String bakeryName;
    @Getter @Setter private String description;
    @Getter @Setter private String phoneNumber;
    @Getter @Setter private String websiteUrl;
    @Getter @Setter private String address;
    @Getter @Setter private Bitmap logo;
    @Getter @Setter private String imgUrl;

    public Bakery () {}
}
