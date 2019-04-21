package br.com.danieldlj.goomerlistarango.Rest;

import java.util.ArrayList;

import br.com.danieldlj.goomerlistarango.Model.RestaurantMenuModel;
import br.com.danieldlj.goomerlistarango.Model.RestaurantModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("restaurants/{id}/menu")
    Call<ArrayList<RestaurantMenuModel>> getPreatos(@Path("id") int id);

    @GET("restaurants")
    Call<ArrayList<RestaurantModel>> getRestaurants();
}
