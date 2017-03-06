package com.traversoft.favebakes.recyclerview.itemholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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

import com.traversoft.favebakes.models.Bakery;
import com.traversoft.favebakes.R;


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
        }
    }
}
