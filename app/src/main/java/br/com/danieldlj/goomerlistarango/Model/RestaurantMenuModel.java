package br.com.danieldlj.goomerlistarango.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RestaurantMenuModel  {
    private int restaurantId;
    private String name;
    private String image;
    private Float price;
    private String group;
    private ArrayList<OfertasModel> sales = new ArrayList<>();

    public RestaurantMenuModel() {
    }

    public RestaurantMenuModel(int restaurantId, String name, String image, Float price, String group, ArrayList<OfertasModel> sales) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.group = group;
        this.sales = sales;
    }

    public RestaurantMenuModel(Parcel in) {
        name = in.readString();
    }

    public int getRestaurantId() { return restaurantId; }

    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public Float getPrice() { return price; }

    public void setPrice(Float price) { this.price = price; }

    public String getGroup() { return group; }

    public void setGroup(String group) { this.group = group; }

    public ArrayList<OfertasModel> getSales() { return sales; }

    public void setSales(ArrayList<OfertasModel> sales) { this.sales = sales; }

}
