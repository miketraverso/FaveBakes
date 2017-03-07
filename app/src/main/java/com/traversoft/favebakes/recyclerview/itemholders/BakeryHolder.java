package com.traversoft.favebakes.recyclerview.itemholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.firebase.appindexing.Action;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.appindexing.builders.Indexables;
import com.traversoft.favebakes.models.Bakery;
import com.traversoft.favebakes.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class BakeryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.bakery_logo) ImageView bakeryLogo;
    @BindView(R.id.bakery_name) protected TextView bakeryName;
    @BindView(R.id.bakery_address) protected TextView address;
    @BindView(R.id.bakery_description) protected TextView description;
    @BindView(R.id.bakery_phone) protected TextView phone;
    @BindView(R.id.bakery_website) protected TextView website;

    private Bakery bakery;
    private Context context;

    public BakeryHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindBakery(Bakery bakery) {
        this.bakery = bakery;
        this.bakeryName.setText(bakery.getBakeryName());
        this.address.setText(bakery.getAddress());
        this.phone.setText(bakery.getPhoneNumber());
        this.website.setText(bakery.getWebsiteUrl());
        this.description.setText(bakery.getDescription());
        if (this.bakery.getImgUrl() != null && !this.bakery.getImgUrl().contentEquals("")) {
            Glide.with(this.context)
                    .load(this.bakery.getImgUrl())
                    .centerCrop()
                    .placeholder(R.mipmap.pie)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this.bakeryLogo);
        }
    }

    @OnClick(R.id.bakery_website) public void launchWebsite(TextView url) {
        String websiteUrl = url.getText().toString();
        if (URLUtil.isValidUrl(websiteUrl)) {
            Intent browserIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
            context.startActivity(browserIntent);
        }
    }

    @Override public void onClick(View v) {
        if (this.bakery != null) {
            Toast.makeText(this.context, "Clicked on " + this.bakery.getBakeryName(), Toast.LENGTH_SHORT ).show();
            indexBakery();
            FirebaseUserActions.getInstance().end(getBakeryViewAction());
            FirebaseUserActions.getInstance().end(getBakeryAction());
        }
    }

    private Action getBakeryAction() {
        return new Action.Builder(Action.Builder.LIKE_ACTION)
                .setObject(bakery.getBakeryName() + " Bakery", "https://traversoft.com/favebakes/")
                .build();
    }

    private Action getBakeryViewAction() {
        return new Action.Builder(Action.Builder.LIKE_ACTION)
                .setObject(bakery.getBakeryName() + " Bakery", "https://traversoft.com/favebakes/" + bakery.getBakeryId() + "/")
                .build();
    }

    private void indexBakery() {
        Indexable bakeryToIndex = new Indexable.Builder()
                .setName(bakery.getBakeryName())
                .setUrl("https://traversoft.com/favebakes/" + bakery.getBakeryId())
                .setImage(bakery.getImgUrl())
                .build();

        Task<Void> task = FirebaseAppIndex.getInstance().update(bakeryToIndex);
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
    }
}