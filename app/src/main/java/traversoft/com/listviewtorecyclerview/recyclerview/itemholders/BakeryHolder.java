package traversoft.com.listviewtorecyclerview.recyclerview.itemholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import traversoft.com.listviewtorecyclerview.R;
import traversoft.com.listviewtorecyclerview.models.Bakery;

public class BakeryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // 1. Define the bindings to the ViewHolder's views
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

        // 2. Bind Butter knife to this view holder
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bindBakery(Bakery bakery) {

        this.bakery = bakery;
        this.bakeryName.setText(bakery.bakeryName);
        this.address.setText(bakery.address);
        this.phone.setText(bakery.phoneNumber);
        this.website.setText(bakery.websiteUrl);
        this.description.setText(bakery.description);
        this.bakeryLogo.setImageBitmap(bakery.logo);
    }

    // 3. Handle clicks on the bakery website
    @OnClick(R.id.bakery_website)
    public void launchWebsite(TextView url) {

        String websiteUrl = url.getText().toString();
        if (URLUtil.isValidUrl(websiteUrl)) {

            Intent browserIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
            context.startActivity(browserIntent);
        }
    }

    @Override
    public void onClick(View v) {

        if (this.bakery != null) {

            Toast.makeText(this.context, "Clicked on " + this.bakery.bakeryName, Toast.LENGTH_SHORT ).show();
        }
    }
}
