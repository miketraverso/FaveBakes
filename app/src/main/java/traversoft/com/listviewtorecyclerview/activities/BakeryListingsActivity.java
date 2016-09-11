package traversoft.com.listviewtorecyclerview.activities;

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
import traversoft.com.listviewtorecyclerview.R;
import traversoft.com.listviewtorecyclerview.adapters.BakeryAdapter;
import traversoft.com.listviewtorecyclerview.models.Bakery;
import traversoft.com.listviewtorecyclerview.recyclerview.itemdecorators.ShadowVerticalSpaceItemDecorator;
import traversoft.com.listviewtorecyclerview.recyclerview.itemdecorators.VerticalSpaceItemDecorator;
import traversoft.com.listviewtorecyclerview.utilities.CustomTypefaceSpan;

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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                bakeries.clear();

                for (DataSnapshot bakeriesSnapshot : dataSnapshot.getChildren()){

                    Bakery value = bakeriesSnapshot.getValue(Bakery.class);
                    bakeries.add(value);
                    Log.d("BAKERY", "Bakery: " + value);
                }
                listingsView.getAdapter().notifyDataSetChanged();
            }
        });

//        Bakery floridaBakery = new Bakery("Florida Bakery", "For the past 40 years, the Florida Bakery has been a family owned and operated business. Located in the heart of West Tampa (where the Latin culture has flourished), Florida Bakery continues a Cuban style tradition that was brought here from the recipes of Cuba and Europe. ", "718-555-1212", "http://floridabakerytampa.com/", "3320 W Columbus Dr, Tampa, FL");
//        floridaBakery.logo = floridaIcon;
//        bakeries.add(floridaBakery);
//
//        Bakery alessiBakery = new Bakery("Alessi Bakery", "It has been over 100 years since Alessi Bakery’s first inception in 1912. Nicolo’s insistence on quality, hard-work, consistency and customer service inherited by 4 generations has been their perfect recipe for success. Their love, experience, and knowledge have made the Alessi family bakery the most successful and well-known bakery in Tampa.", "(813) 879-4544", "http://www.bakery.com", "2909 W Cypress St, Tampa, FL 33609");
//        alessiBakery.logo = alessiIcon;
//        bakeries.add(alessiBakery);
//
//        Bakery housewifeBakery = new Bakery("Housewife Bake Shop", "Welcome to Housewife Bake Shop, a family owned and operated bakery since 1959. We specialize in Italian favorites such as cannolis, scacciata and various Italian cookies as well as a complete line of pastries, pies, cakes and breads.  Our sandwiches and catering lines are sure to satisfy any appetite.", "(813) 935-5106", "http://housewifebakeshop.com", "6821 N Armenia Ave, Tampa, FL");
//        housewifeBakery.logo = housewifeIcon;
//        bakeries.add(housewifeBakery);
//
//        Bakery mauricioBakery = new Bakery("Mauricio Faedo's Bakery", "Mauricio's fills the air with the smell of fresh bread in the morning. In he afternoon. Practically all day. Except on Saturdays. It happens to be right around the corner from where we stay when visiting Cigar City & I'm kicking myself for not stopping in sooner. \n", "(813) 237-2377", "", "5150 N Florida Ave, Tampa, FL ");
//        mauricioBakery.logo = mauricioIcon;
//        bakeries.add(mauricioBakery);
//
//        Bakery cakeBakery = new Bakery("A Piece of Cake & Desserts", "A Piece of Cake & Desserts, a local Tampa bakery specializing in Wedding Cakes, Celebration Cakes, Dessert Bars and Cookies.  We offer homemade and handmade desserts and bars.", "(813) 510-4907", "http://www.cakestampa.com", "290911284 W Hillsborough Ave, Tampa, FL");
//        cakeBakery.logo = pieIcon;
//        bakeries.add(cakeBakery);
//
//        bakeries.add(floridaBakery);
//        bakeries.add(alessiBakery);
//        bakeries.add(mauricioBakery);
//        bakeries.add(housewifeBakery);
//        bakeries.add(cakeBakery);
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
