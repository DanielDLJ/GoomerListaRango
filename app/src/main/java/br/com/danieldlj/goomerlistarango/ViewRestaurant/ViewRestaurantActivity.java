package br.com.danieldlj.goomerlistarango.ViewRestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.danieldlj.goomerlistarango.Model.HourModel;
import br.com.danieldlj.goomerlistarango.Model.RestaurantMenuModel;
import br.com.danieldlj.goomerlistarango.Model.RestaurantModel;
import br.com.danieldlj.goomerlistarango.R;
import br.com.danieldlj.goomerlistarango.Rest.ApiClient;
import br.com.danieldlj.goomerlistarango.Rest.ApiInterface;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.PratoModel;
import br.com.danieldlj.goomerlistarango.ViewRestaurant.Models.TituloModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRestaurantActivity extends AppCompatActivity {

    private String Tag = "ViewRestaurantActivity";

    private RestaurantModel restaurantModel;
    private Toolbar mToolbar;
    private TextView nome, descricao, horario;
    private ImageView restaurant_photo;
    private EditText buscar;

    private ApiInterface apiInterface;
    private ArrayList<RestaurantMenuModel> pratos = new ArrayList<>();
    private ArrayList<TituloModel> tituloModels = new ArrayList<>();
    private ArrayList<TituloModel> tituloModels_sort = new ArrayList<>();


    private RecyclerView recyclerView;
    public PratoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);

        Intent intent = getIntent();
        restaurantModel = (RestaurantModel)intent.getSerializableExtra("restaurant");
        initializaFields();


        buscar.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tituloModels_sort.clear();
                if(TextUtils.isEmpty(buscar.getText().toString())) {
                    adapter = new PratoAdapter(tituloModels, ViewRestaurantActivity.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    for (TituloModel titulo : tituloModels) {
                        ArrayList<PratoModel> tempListPrato = new ArrayList<>();
                        for (PratoModel prato :  titulo.getItems()) {
                            if(prato.getName().toLowerCase().trim().contains(buscar.getText().toString().toLowerCase().trim())) {
                                tempListPrato.add(prato);
                            }
                        }
                        if(tempListPrato.size() > 0){
                            tituloModels_sort.add(new TituloModel(tempListPrato.get(0).getGroup(), tempListPrato));
                        }else {
                            if(titulo.getTitle().toLowerCase().trim().contains(buscar.getText().toString().toLowerCase().trim())) {
                                tituloModels_sort.add(new TituloModel(titulo.getTitle(), titulo.getItems() ));
                            }
                        }

                    }
                    adapter = new PratoAdapter(tituloModels_sort, ViewRestaurantActivity.this);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

    }

    private void initializaFields() {

        nome = findViewById(R.id.restaurant_name);
        descricao = findViewById(R.id.restaurant_description);
        horario = findViewById(R.id.restaurant_horary);
        restaurant_photo = findViewById(R.id.restaurant_photo);
        buscar = findViewById(R.id.busca);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

        nome.setText(restaurantModel.getName());
        descricao.setText("Descrição: ");
        horario.setText(restaurantModel.getName());
        Picasso.get().load(restaurantModel.getImage()).placeholder(R.drawable.restaurant_photo_default).into(restaurant_photo);
        initializaDate();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case ExpandableListPosition.GROUP:
                        return 2;
                    default:
                        return 1;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        getPratos();

    }

    private void getPratos() {

        Call<ArrayList<RestaurantMenuModel>> call = apiInterface.getPreatos(restaurantModel.getId());

        call.enqueue(new Callback<ArrayList<RestaurantMenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RestaurantMenuModel>> call, Response<ArrayList<RestaurantMenuModel>> response) {
                if(!response.isSuccessful()){
                    Log.d(Tag,"Code : "+ response.code());
                    return;
                }

                pratos = response.body();
                setData();

            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantMenuModel>> call, Throwable t) {
                Log.d(Tag,"Failure Message : "+ t.getMessage());
            }
        });
    }

    private void setData() {

        Map<String, ArrayList<PratoModel>> tituloAndPrato = new HashMap<>();
        for(RestaurantMenuModel prato : pratos){
            addValues(prato.getGroup(), new PratoModel(prato),tituloAndPrato);
        }
        mostrarData(tituloAndPrato);


        for (Object o : tituloAndPrato.keySet()) {
            String key = o.toString();
            tituloModels.add(new TituloModel(key, tituloAndPrato.get(key)));
        }

        adapter = new PratoAdapter(tituloModels, this);
        recyclerView.setAdapter(adapter);

    }

    public void addValues(String key, PratoModel value,  Map<String, ArrayList<PratoModel>> hashMap) {
        ArrayList<PratoModel> tempList = null;
        if (hashMap.containsKey(key)) {
            tempList =  hashMap.get(key);
            if(tempList == null)
                tempList = new ArrayList<>();
            tempList.add(value);
        } else {
            tempList = new ArrayList<>();
            tempList.add(value);
        }
        hashMap.put(key,tempList);
    }

    public void mostrarData( Map<String, ArrayList<PratoModel>> hashMap) {
        Iterator it = hashMap.keySet().iterator();
        ArrayList<PratoModel> tempList = null;

        while (it.hasNext()) {
            String key = it.next().toString();
            tempList = hashMap.get(key);
            if (tempList != null) {
                for (PratoModel value: tempList) {
                    Log.d(Tag,"Key : "+key+ " , Value : "+value.getName());
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializaDate(){
        for (HourModel horas : restaurantModel.getHours()){
            for(int i = 0 ; i < horas.getDays().size() ; i++){
                Log.d(Tag,diaSemana(horas.getDays().get(i)));
            }
        }
    }

    private String diaSemana(int dia){
        switch (dia){
            case 1:
                return "Domingo";
            case 2:
                return "Segunda";
            case 3:
                return "Terça";
            case 4:
                return "Quarta";
            case 5:
                return "Quinta";
            case 6:
                return "Sexta";
            case 7:
                return "Sábado";
            default:
                return "";
        }
    }

}
