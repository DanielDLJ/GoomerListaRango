package br.com.danieldlj.goomerlistarango.Rest;

import java.util.ArrayList;

import br.com.danieldlj.goomerlistarango.Model.RestaurantModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("restaurants")
    Call<ArrayList<RestaurantModel>> getRestaurants();
}
