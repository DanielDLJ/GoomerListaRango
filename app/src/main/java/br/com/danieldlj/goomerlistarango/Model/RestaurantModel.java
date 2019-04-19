package br.com.danieldlj.goomerlistarango.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("hours")
    private ArrayList<HourModel> hours = new ArrayList<HourModel>();

    @SerializedName("image")
    private String image;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public ArrayList<HourModel> getHours() { return hours; }

    public void setHours(ArrayList<HourModel> hours) { this.hours = hours; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

}
