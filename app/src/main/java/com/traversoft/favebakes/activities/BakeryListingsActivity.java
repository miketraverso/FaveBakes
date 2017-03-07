package com.traversoft.favebakes.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.traversoft.favebakes.FaveBakesApplication;
import com.traversoft.favebakes.adapters.BakeryAdapter;
import com.traversoft.favebakes.models.Bakery;
import com.traversoft.favebakes.recyclerview.itemdecorators.ShadowVerticalSpaceItemDecorator;
import com.traversoft.favebakes.recyclerview.itemdecorators.VerticalSpaceItemDecorator;
import com.traversoft.favebakes.utilities.CustomTypefaceSpan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.traversoft.favebakes.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class BakeryListingsActivity extends AppCompatActivity {

    @BindView(R.id.listings_view) RecyclerView listingsView;
    private Context context;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_listings);

        // 2. Bind all annotated resources to their respective fields
        ButterKnife.bind(this);

        setupActionBarTheme();
        this.context = this;

        loadBakeries();

        BakeryAdapter adapter = new BakeryAdapter(this, R.layout.list_item_bakery, FaveBakesApplication.getSharedInstance().getBakeries());

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        VerticalSpaceItemDecorator itemDecorator = new VerticalSpaceItemDecorator((int) getResources().getDimension(R.dimen.spacer_20));
        ShadowVerticalSpaceItemDecorator shadowItemDecorator = new ShadowVerticalSpaceItemDecorator(this, R.drawable.drop_shadow);

        listingsView.setHasFixedSize(true);
        listingsView.setLayoutManager(layoutManager);
        listingsView.addItemDecoration(shadowItemDecorator);
        listingsView.addItemDecoration(itemDecorator);
        listingsView.setItemAnimator(itemAnimator);
        listingsView.setAdapter(adapter);

        onNewIntent(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String bakeryId = data.substring(data.lastIndexOf("/") + 1);
            Toast.makeText(this, "Loaded " + bakeryId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override protected void onStart() {
        super.onStart();
        FirebaseAppIndex.getInstance().update(getIndexableActivity());
        FirebaseUserActions.getInstance().start(getActivityAction());
    }

    @Override protected void onStop() {
        FirebaseUserActions.getInstance().end(getActivityAction());
        super.onStop();
    }

    private Action getActivityAction() {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject("FaveBakes Bakeries", "https://traversoft.com/favebakes/")

                .setMetadata(new Action.Metadata.Builder().setUpload(false))

                .build();
    }

    private Indexable getIndexableActivity() {
        Indexable activityViewToIndex = Indexables.noteDigitalDocumentBuilder()
                .setName("FaveBakes Viewed")
                .setText("FaveBakes Bakery Listing")
                .setUrl("https://traversoft.com/favebakes/")
                .setMetadata(new Indexable.Metadata.Builder().setWorksOffline(true))
                .build();

        Task<Void> task = FirebaseAppIndex.getInstance().update(activityViewToIndex);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override public void onSuccess(Void aVoid) {
                Log.d(TAG, "App Indexing API: Successfully added note to index");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "App Indexing API: Failed to add note to index. " + exception
                        .getMessage());
            }
        });

        return activityViewToIndex;
    }

    // Loads bakery data into List<Bakery>
    private void loadBakeries() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bakeries");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }

            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                FaveBakesApplication.getSharedInstance().getBakeries().clear();
                for (DataSnapshot bakerySnapshot : dataSnapshot.getChildren()){
                    Bakery bakery = bakerySnapshot.getValue(Bakery.class);
                    bakery.setBakeryId(bakerySnapshot.getKey());
                    FaveBakesApplication.getSharedInstance().getBakeries().add(bakery);
                    Log.d("BAKERY", "Bakery: " + bakery);
                }
                listingsView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void setupActionBarTheme() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");
        SpannableStringBuilder spannedTitle = new SpannableStringBuilder(this.getTitle());
        spannedTitle.setSpan(new CustomTypefaceSpan("", font), 0, spannedTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        //setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(spannedTitle);
        }
    }
}
