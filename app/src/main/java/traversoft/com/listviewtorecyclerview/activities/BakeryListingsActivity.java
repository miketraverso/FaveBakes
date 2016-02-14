package traversoft.com.listviewtorecyclerview.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import traversoft.com.listviewtorecyclerview.utilities.CustomTypefaceSpan;
import traversoft.com.listviewtorecyclerview.R;
import traversoft.com.listviewtorecyclerview.models.Bakery;
import traversoft.com.listviewtorecyclerview.adapters.BakeryAdapter;

public class BakeryListingsActivity extends AppCompatActivity {

    private ListView listingsView;
    private List<Bakery> bakeries;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inflate the activity & perform app styling
        setContentView(R.layout.activity_bakery_listings);
        setupActionBarTheme();
        this.context = this;

        // 2. Load the Bakery data
        loadBakeries();

        // 3. Initialize the Bakery adapter
        BakeryAdapter adapter = new BakeryAdapter(this, R.layout.list_item_bakery, bakeries);

        // 4. Inflate our ListView and set the adapter on it
        listingsView = (ListView)findViewById(R.id.listings_view);
        listingsView.setAdapter(adapter);

        // 5. Handle the click
        listingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListView listView = (ListView) view.getParent();
                if (listView != null) {

                    Bakery bakery = bakeries.get(position);
                    if (bakery != null) {

                        Toast.makeText(context, "Clicked on " + bakery.bakeryName, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Loads bakery data into List<Bakery>
    private void loadBakeries() {

        bakeries = new ArrayList<>();

        Bitmap alessiIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.alessi);
        Bitmap floridaIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.florida);
        Bitmap housewifeIcon =BitmapFactory.decodeResource(getResources(), R.mipmap.piece);
        Bitmap mauricioIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.mauricio);
        Bitmap pieIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.pie);

        Bakery floridaBakery = new Bakery("Florida Bakery", "For the past 40 years, the Florida Bakery has been a family owned and operated business. Located in the heart of West Tampa (where the Latin culture has flourished), Florida Bakery continues a Cuban style tradition that was brought here from the recipes of Cuba and Europe. ", "718-555-1212", "http://floridabakerytampa.com/", "3320 W Columbus Dr, Tampa, FL");
        floridaBakery.logo = floridaIcon;
        bakeries.add(floridaBakery);

        Bakery alessiBakery = new Bakery("Alessi Bakery", "It has been over 100 years since Alessi Bakery’s first inception in 1912. Nicolo’s insistence on quality, hard-work, consistency and customer service inherited by 4 generations has been their perfect recipe for success. Their love, experience, and knowledge have made the Alessi family bakery the most successful and well-known bakery in Tampa.", "(813) 879-4544", "www.bakery.com", "2909 W Cypress St, Tampa, FL 33609");
        alessiBakery.logo = alessiIcon;
        bakeries.add(alessiBakery);

        Bakery housewifeBakery = new Bakery("Housewife Bake Shop", "Welcome to Housewife Bake Shop, a family owned and operated bakery since 1959. We specialize in Italian favorites such as cannolis, scacciata and various Italian cookies as well as a complete line of pastries, pies, cakes and breads.  Our sandwiches and catering lines are sure to satisfy any appetite.", "(813) 935-5106", "www.housewifebakeshop.com", "6821 N Armenia Ave, Tampa, FL");
        housewifeBakery.logo = housewifeIcon;
        bakeries.add(housewifeBakery);

        Bakery mauricioBakery = new Bakery("Mauricio Faedo's Bakery", "", "(813) 237-2377", "", "5150 N Florida Ave, Tampa, FL ");
        mauricioBakery.logo = mauricioIcon;
        bakeries.add(mauricioBakery);

        Bakery cakeBakery = new Bakery("A Piece of Cake & Desserts", "A Piece of Cake & Desserts, a local Tampa bakery specializing in Wedding Cakes, Celebration Cakes, Dessert Bars and Cookies.  We offer homemade and handmade desserts and bars.", "(813) 510-4907", "www.cakestampa.com", "290911284 W Hillsborough Ave, Tampa, FL");
        cakeBakery.logo = pieIcon;
        bakeries.add(cakeBakery);

        bakeries.add(floridaBakery);
        bakeries.add(alessiBakery);
        bakeries.add(mauricioBakery);
        bakeries.add(housewifeBakery);
        bakeries.add(cakeBakery);
    }

    // Sets the app title to use a custom font
    private void setupActionBarTheme() {

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/lobster.ttf");
        SpannableStringBuilder spannedTitlte = new SpannableStringBuilder(this.getTitle());
        spannedTitlte.setSpan(new CustomTypefaceSpan("", font), 0, spannedTitlte.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(spannedTitlte);
        }
    }
}