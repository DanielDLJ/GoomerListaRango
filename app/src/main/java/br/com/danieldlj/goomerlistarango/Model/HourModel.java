package br.com.danieldlj.goomerlistarango.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HourModel {

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("days")
    private ArrayList<Integer> days = new ArrayList<Integer>();


    public String getFrom() { return from; }

    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }

    public void setTo(String to) { this.to = to; }

    public ArrayList<Integer> getDays() { return days; }

    public void setDays(ArrayList<Integer> days) { this.days = days; }

}
