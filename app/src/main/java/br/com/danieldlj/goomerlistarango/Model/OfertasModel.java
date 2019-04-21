package br.com.danieldlj.goomerlistarango.Model;

import java.util.ArrayList;

public class OfertasModel {
    private String description;
    private Float price;
    private ArrayList<HourModel> hours = new ArrayList<>();


    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Float getPrice() { return price; }

    public void setPrice(Float price) { this.price = price; }

    public ArrayList<HourModel> getHours() { return hours; }

    public void setHours(ArrayList<HourModel> hours) { this.hours = hours; }


}
