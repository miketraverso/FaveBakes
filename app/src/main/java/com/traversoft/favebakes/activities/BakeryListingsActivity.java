package com.traversoft.favebakes.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;

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


public class BakeryListingsActivity extends AppCompatActivity {

    // 1. Bind views on the activity with the @BindView annotation
    @BindView(R.id.listings_view) RecyclerView listingsView;

    @BindBitmap(R.mipmap.alessi) Bitmap alessiIcon;
    @BindBitmap(R.mipmap.florida) Bitmap floridaIcon;
    @BindBitmap(R.mipmap.piece) Bitmap housewifeIcon;
    @BindBitmap(R.mipmap.mauricio) Bitmap mauricioIcon;
    @BindBitmap(R.mipmap.pie) Bitmap pieIcon;

    private List<Bakery> bakeries;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bakery_listings);

        // 2. Bind all annotated resources to their respective fields
        ButterKnife.bind(this);

        setupActionBarTheme();
        this.context = this;

        loadBakeries();

        BakeryAdapter adapter = new BakeryAdapter(this, R.layout.list_item_bakery, bakeries);

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
    }

    // Loads bakery data into List<Bakery>
    private void loadBakeries() {

        bakeries = new ArrayList<>();

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bakeries");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bakeries.clear();
                for (DataSnapshot bakeriesSnapshot : dataSnapshot.getChildren()){
                    Bakery value = bakeriesSnapshot.getValue(Bakery.class);
                    bakeries.add(value);
                    Log.d("BAKERY", "Bakery: " + value);
                }
                listingsView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    // Sets the app title to use a custom font
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
