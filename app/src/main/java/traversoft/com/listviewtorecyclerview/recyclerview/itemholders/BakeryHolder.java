package traversoft.com.listviewtorecyclerview.recyclerview.itemholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import traversoft.com.listviewtorecyclerview.models.Bakery;
import traversoft.com.listviewtorecyclerview.R;

public class BakeryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView bakeryLogo;
    private final TextView bakeryName;
    private final TextView address;
    private final TextView description;
    private final TextView phone;
    private final TextView website;

    private Bakery bakery;
    private Context context;

    public BakeryHolder(Context context, View itemView) {

        super(itemView);

        // 1. Set the context
        this.context = context;

        // 2. Inflate the UI widgets of the holder
        this.bakeryLogo = (ImageView) itemView.findViewById(R.id.bakery_logo);
        this.bakeryName = (TextView) itemView.findViewById(R.id.bakery_name);
        this.address = (TextView) itemView.findViewById(R.id.bakery_address);
        this.description = (TextView) itemView.findViewById(R.id.bakery_description);
        this.phone = (TextView) itemView.findViewById(R.id.bakery_phone);
        this.website = (TextView) itemView.findViewById(R.id.bakery_website);

        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);
    }

    public void bindBakery(Bakery bakery) {

        // 4. Bind the data to the ViewHolder
        this.bakery = bakery;
        this.bakeryName.setText(bakery.bakeryName);
        this.address.setText(bakery.address);
        this.phone.setText(bakery.phoneNumber);
        this.website.setText(bakery.websiteUrl);
        this.description.setText(bakery.description);
        this.bakeryLogo.setImageBitmap(bakery.logo);
    }

    @Override
    public void onClick(View v) {

        // 5. Handle the onClick event for the ViewHolder
        if (this.bakery != null) {

            Toast.makeText(this.context, "Clicked on " + this.bakery.bakeryName, Toast.LENGTH_SHORT ).show();
        }
    }
}
