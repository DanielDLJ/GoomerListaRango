package br.com.danieldlj.goomerlistarango.ViewRestaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import br.com.danieldlj.goomerlistarango.R;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.PratoModel;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.TituloModel;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder.PratosViewHolder;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder.TituloViewHolder;

public class PratoAdapter  extends ExpandableRecyclerViewAdapter<TituloViewHolder, PratosViewHolder> {

    public PratoAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public TituloViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_view_prato, parent, false);
        return new TituloViewHolder(view);
    }

    @Override
    public PratosViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_prato, parent, false);
        return new PratosViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(PratosViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final PratoModel pratos = ((TituloModel) group).getItems().get(childIndex);
        holder.setPratoName(pratos.getName());
        holder.setValor(String.valueOf(pratos.getPrice()),pratos.getSales(),pratos.getName());
        holder.setPhoto(pratos.getImage());
    }

    @Override
    public void onBindGroupViewHolder(TituloViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
    }
}
