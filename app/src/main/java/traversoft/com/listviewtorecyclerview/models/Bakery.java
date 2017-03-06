package traversoft.com.listviewtorecyclerview.models;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;

public class Bakery {

    @Getter @Setter private String bakeryName;
    @Getter @Setter private String description;
    @Getter @Setter private String phoneNumber;
    @Getter @Setter private String websiteUrl;
    @Getter @Setter private String address;
    @Getter @Setter private Bitmap logo;
    @Getter @Setter private String imgUrl;

    public Bakery () {}

    public Bakery(String bakeryName,
                String description,
                String phoneNumber,
                String websiteUrl,
                String address,
                String imgUrl) {
        this.bakeryName = bakeryName;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.imgUrl = imgUrl;
    }
}
