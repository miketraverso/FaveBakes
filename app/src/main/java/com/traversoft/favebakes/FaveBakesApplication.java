package com.traversoft.favebakes;


import android.app.Application;

import com.traversoft.favebakes.models.Bakery;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class FaveBakesApplication
        extends Application {

    @Getter @Setter private List<Bakery> bakeries;
    @Getter private static FaveBakesApplication sharedInstance;

    public FaveBakesApplication() {
        sharedInstance = this;
    }

    @Override public void onCreate() {
        super.onCreate();
        bakeries = new ArrayList<>();
    }
}
