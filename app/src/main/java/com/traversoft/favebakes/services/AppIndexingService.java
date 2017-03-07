package com.traversoft.favebakes.services;


import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.traversoft.favebakes.FaveBakesApplication;
import com.traversoft.favebakes.models.Bakery;

import java.util.ArrayList;

public class AppIndexingService extends IntentService {

    public AppIndexingService() {
        super("AppIndexingService");
    }

    @Override protected void onHandleIntent(Intent intent) {
        ArrayList<Indexable> indexableNotes = new ArrayList<>();

        for (Bakery bakery : FaveBakesApplication.getSharedInstance().getBakeries()) {
            Indexable bakeryToIndex = Indexables.noteDigitalDocumentBuilder()
                    .setName(bakery.getBakeryName())
                    .setText(bakery.getBakeryName())
                    .setUrl("https://traversoft.com/favebakes/"
                            + bakery.getBakeryId() + "/")
                    .setImage(bakery.getImgUrl())
                    .build();
            indexableNotes.add(bakeryToIndex);
        }

        if (!indexableNotes.isEmpty()) {
            Indexable[] notesArr = new Indexable[indexableNotes.size()];
            notesArr = indexableNotes.toArray(notesArr);
            FirebaseAppIndex.getInstance().update(notesArr);
        }
    }
}
