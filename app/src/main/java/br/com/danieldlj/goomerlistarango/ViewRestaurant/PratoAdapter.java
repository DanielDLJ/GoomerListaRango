package br.com.danieldlj.goomerlistarango.ViewRestaurant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.danieldlj.goomerlistarango.R;
import br.com.danieldlj.goomerlistarango.Util.Utils;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.PratoModel;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.TituloModel;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder.PratosViewHolder;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewHolder.TituloViewHolder;

public class PratoAdapter  extends ExpandableRecyclerViewAdapter<TituloViewHolder, PratosViewHolder> {

    private String Tag = "PratoAdapter";
    private Context context;
    List<? extends ExpandableGroup> groups;
    public PratoAdapter(List<? extends ExpandableGroup> groups , Context context) {
        super(groups);
        this.groups = groups;
        this.context = context;
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
    public void onBindChildViewHolder(final PratosViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final PratoModel pratos = ((TituloModel) group).getItems().get(childIndex);
        holder.setPratoName(pratos.getName());
        holder.setValor(String.valueOf(pratos.getPrice()),pratos.getSales(),pratos.getName());
        holder.setPhoto(pratos.getImage());


        //Declare the timer
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (context != null) {
                    ViewRestaurantActivity viewRestaurantActivity = ((ViewRestaurantActivity) context);
                    viewRestaurantActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //change View Data
                            holder.setValor(String.valueOf(pratos.getPrice()), pratos.getSales(), pratos.getName());
                        }
                    });
                  }
                }
        },
        //Set how long before to start calling the TimerTask (in milliseconds)
        Utils.getMiliSegundos(),
        //Set the amount of time between each execution (in milliseconds)
        60000); //15 minutos


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Tag,pratos.getName());
                if(context != null) {
                    holder.openDialog(pratos, context);
                }
            }
        });
    }

    public void updateMenuList(ArrayList<TituloModel> newlist) {
        this.groups = newlist;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindGroupViewHolder(TituloViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
    }
}
