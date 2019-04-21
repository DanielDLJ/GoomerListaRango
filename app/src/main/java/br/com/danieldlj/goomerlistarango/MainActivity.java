package br.com.danieldlj.goomerlistarango;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

import br.com.danieldlj.goomerlistarango.Model.RestaurantModel;
import br.com.danieldlj.goomerlistarango.Rest.ApiClient;
import br.com.danieldlj.goomerlistarango.Rest.ApiInterface;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.ViewRestaurantActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String Tag = "MainActivity";

    private EditText buscar;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;

    private ApiInterface apiInterface;
    private ArrayList<RestaurantModel> restaurants = new ArrayList<>();
    private ArrayList<RestaurantModel> restaurants_sort = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializaFields();

        getRestaurants();

        buscar.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                restaurants_sort.clear();
                if(TextUtils.isEmpty(buscar.getText().toString())) {
                    adapter.updateRestaurantList(restaurants);
                }else {
                    for (int i = 0; i < restaurants.size(); i++) {
                        if (restaurants.get(i).getName().toLowerCase().trim().contains(buscar.getText().toString().toLowerCase().trim())) {
                            restaurants_sort.add(restaurants.get(i));
                        }
                    }
                    adapter.updateRestaurantList(restaurants_sort);
                }

            }
        });

    }

    private void initializaFields() {
        recyclerView = findViewById(R.id.recyclerView);
        buscar = findViewById(R.id.busca);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RestaurantAdapter(restaurants);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(restaurants_sort.size()> 0){
                    Log.d(Tag,restaurants_sort.get(position).getName());
                    goToViewRestaurantActivity(restaurants_sort.get(position));
                }else {
                    Log.d(Tag,restaurants.get(position).getName());
                    goToViewRestaurantActivity(restaurants.get(position));
                }
            }
        });

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
    }

    private void getRestaurants() {

        Call<ArrayList<RestaurantModel>> call = apiInterface.getRestaurants();

        call.enqueue(new Callback<ArrayList<RestaurantModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RestaurantModel>> call, Response<ArrayList<RestaurantModel>> response) {
                if(!response.isSuccessful()){
                    Log.d(Tag,"Code : "+ response.code());
                    return;
                }

                restaurants = response.body();
                adapter.updateRestaurantList(restaurants);

            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantModel>> call, Throwable t) {
                Log.d(Tag,"Failure Message : "+ t.getMessage());
            }
        });
    }

    public void goToViewRestaurantActivity(RestaurantModel restaurantModel){
        Intent intent = new Intent(this, ViewRestaurantActivity.class);
        intent.putExtra("restaurant",restaurantModel);
        startActivity(intent);
    }

}