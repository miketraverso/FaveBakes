package traversoft.com.listviewtorecyclerview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import traversoft.com.listviewtorecyclerview.models.Bakery;
import traversoft.com.listviewtorecyclerview.R;

public class BakeryAdapter extends ArrayAdapter<Bakery> {

    private final List<Bakery> bakeries;
    private Context context;
    private int itemResource;

    public BakeryAdapter(Context context, int itemResource, List<Bakery> bakeries) {

        // 1. Initialize our adapter
        super(context, R.layout.list_item_bakery, bakeries);
        this.bakeries = bakeries;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 2. Have we inflated this view before?
        View itemView;
        if (convertView != null) {

            // 2a. We have so let's reuse.
            itemView = convertView;
        }
        else {

            // 2b. We have NOT so let's inflate
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(this.itemResource, parent, false);
        }

        // 3. Get the bakery to appear in this item
        Bakery bakery = this.bakeries.get(position);
        if (bakery != null) {

            // 4. Inflate the UI widgets
            ImageView bakeryLogo = (ImageView) itemView.findViewById(R.id.bakery_logo);
            TextView bakeryName = (TextView) itemView.findViewById(R.id.bakery_name);
            TextView bakeryAddress = (TextView) itemView.findViewById(R.id.bakery_address);
            TextView bakeryDescription = (TextView) itemView.findViewById(R.id.bakery_description);
            TextView bakeryPhone = (TextView) itemView.findViewById(R.id.bakery_phone);
            TextView bakeryWebsite = (TextView) itemView.findViewById(R.id.bakery_website);

            // 5. Set the UI widgets with appropriate data from the Bakery model
            bakeryName.setText(bakery.bakeryName);
            bakeryAddress.setText(bakery.address);
            bakeryPhone.setText(bakery.phoneNumber);
            bakeryWebsite.setText(bakery.websiteUrl);
            bakeryDescription.setText(bakery.description);
            bakeryLogo.setImageBitmap(bakery.logo);
        }

        return itemView;
    }
}