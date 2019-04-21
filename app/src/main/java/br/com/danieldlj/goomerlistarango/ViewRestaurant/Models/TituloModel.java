package br.com.danieldlj.goomerlistarango.ViewRestaurant.Models;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class TituloModel extends ExpandableGroup<PratoModel> {

    //private String titulo;


    public TituloModel(String title, List<PratoModel> items) {
        super(title, items);
    }

    protected TituloModel(Parcel in) {
        super(in);
    }

   // public String getTitulo() { return titulo; }

    //public void setTitulo(String titulo) { this.titulo = titulo; }


}
