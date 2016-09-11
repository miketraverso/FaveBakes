package traversoft.com.listviewtorecyclerview.models;

import android.graphics.Bitmap;

public class Bakery {

    public String bakeryName;
    public String description;
    public String phoneNumber;
    public String websiteUrl;
    public String address;
    public Bitmap logo;

    public Bakery () {

    }

    public Bakery(String bakeryName,
                  String description,
                  String phoneNumber,
                  String websiteUrl,
                  String address) {

        this.bakeryName = bakeryName;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
    }
}
