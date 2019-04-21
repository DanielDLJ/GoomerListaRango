package br.com.danieldlj.goomerlistarango;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import br.com.danieldlj.goomerlistarango.Model.HourModel;
import br.com.danieldlj.goomerlistarango.Model.RestaurantModel;
import br.com.danieldlj.goomerlistarango.Util.Utils;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private String Tag = "RestaurantAdapter";
    private ArrayList<RestaurantModel> restaurantModels;
    private OnItemClickListener mListener;


    RestaurantAdapter(ArrayList<RestaurantModel> restaurantModels){
        this.restaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_restaurant, viewGroup, false);

        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view, mListener);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder restaurantViewHolder, final int i) {
        restaurantViewHolder.restaurant_name.setText(restaurantModels.get(i).getName());
        restaurantViewHolder.restaurant_address.setText(restaurantModels.get(i).getAddress());
        Picasso.get().load(restaurantModels.get(i).getImage()).placeholder(R.drawable.restaurant_photo_default).into(restaurantViewHolder.restaurant_photo);

        Utils.getMiliSegundos();



        int controle = 0;
        for(HourModel dias : restaurantModels.get(i).getHours()) {
            if (Utils.verifyHours(dias)) {
                restaurantViewHolder.restaurant_status.setImageResource(R.drawable.aberto);
                controle = 1;
                break;
            }
        }

        //Declare the timer
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                int controle = 0;
                for(HourModel dias : restaurantModels.get(i).getHours()) {
                    if (Utils.verifyHours(dias)) {
                        restaurantViewHolder.restaurant_status.setImageResource(R.drawable.aberto);
                        controle = 1;
                        break;
                    }
                }
                if(controle == 0)
                    restaurantViewHolder.restaurant_status.setImageResource(R.drawable.fechado);

            }

         },
        //Set how long before to start calling the TimerTask (in milliseconds)
        Utils.getMiliSegundos(),
        //Set the amount of time between each execution (in milliseconds)
        60000);


    }

    @Override
    public int getItemCount() {
        return restaurantModels.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateRestaurantList(ArrayList<RestaurantModel> newlist) {
        this.restaurantModels = newlist;
        this.notifyDataSetChanged();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView restaurant_name;
        TextView restaurant_address;
        ImageView restaurant_photo,restaurant_status;

        RestaurantViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            restaurant_name = (TextView)itemView.findViewById(R.id.restaurant_name);
            restaurant_address = (TextView)itemView.findViewById(R.id.restaurant_address);
            restaurant_photo = (ImageView)itemView.findViewById(R.id.restaurant_photo);
            restaurant_status = (ImageView)itemView.findViewById(R.id.restaurant_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
