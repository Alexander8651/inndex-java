package com.inndex.car.personas.fragments.vehiculos.presenters;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.type.Color;
import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterSpinner;
import com.inndex.car.personas.adapter.AdapterSpinnerCombustible;
import com.inndex.car.personas.adapter.AdapterSpinnerLinea;
import com.inndex.car.personas.model.Combustibles;
import com.inndex.car.personas.model.LineasVehiculos;
import com.inndex.car.personas.model.MarcaVehiculos;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterAgregarVehiculos implements IPresenterAgregarVehiculos {
    IAgregaVehiculosFragment iAgregaVehiculosFragment;
    ArrayList<MarcaVehiculos> marcaVehiculos;
    ArrayList<LineasVehiculos> lineasVehiculos;
    ArrayList<Combustibles> combustibles;
    Context context;
    Vehiculo vehiculo = new Vehiculo();


    Spinner marcas, lineas, modelo, combustible;


    public PresenterAgregarVehiculos(IAgregaVehiculosFragment iAgregaVehiculosFragment, Context context) {
        this.context = context;
        this.iAgregaVehiculosFragment = iAgregaVehiculosFragment;
        obtenerMarcas();
    }

    @Override
    public void obtenerMarcas() {

        Call<List<MarcaVehiculos>> marcaVehiculosCall = MedidorApiAdapter.getApiService().getMarcasVehiculos();

        marcaVehiculosCall.enqueue(new Callback<List<MarcaVehiculos>>() {
            @Override
            public void onResponse(Call<List<MarcaVehiculos>> call, Response<List<MarcaVehiculos>> response) {
                if (response.isSuccessful()){
                    Log.d("marcasss", response.body().get(0).getNombre());
                    marcaVehiculos = (ArrayList<MarcaVehiculos>) response.body();
                    marcaVehiculos.set(0, new MarcaVehiculos(0, "Selecciona una marca"));
                    mostrarSpinnerMarca();
                    obtenerCombustibles();
                }
            }
            @Override
            public void onFailure(Call<List<MarcaVehiculos>> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarSpinnerMarca() {
        marcas = iAgregaVehiculosFragment.crearSpinerMarca();
        AdapterSpinner adapterSpinner = iAgregaVehiculosFragment.crearAdapterMarca(marcaVehiculos);
        marcas.setAdapter(adapterSpinner);

        marcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MarcaVehiculos marcaVehiculos = (MarcaVehiculos) adapterView.getItemAtPosition(i);
                Log.d("idmarcaa", String.valueOf(marcaVehiculos.getId()));

                if (marcaVehiculos.getId() != 0){
                    obtenerLinea(marcaVehiculos.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void obtenerLinea(int id) {

        Call<List<LineasVehiculos>> lineaVehiculo = MedidorApiAdapter.getApiService().getLineasVehiculosByMarca((long)id);

        lineaVehiculo.enqueue(new Callback<List<LineasVehiculos>>() {
            @Override
            public void onResponse(Call<List<LineasVehiculos>> call, Response<List<LineasVehiculos>> response) {

                if (response.isSuccessful()){
                    lineasVehiculos = (ArrayList<LineasVehiculos>) response.body();
                    mostrarSpinnerLinea();
                }
            }

            @Override
            public void onFailure(Call<List<LineasVehiculos>> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarSpinnerLinea() {

        lineas = iAgregaVehiculosFragment.crearSpinerLinea();
        AdapterSpinnerLinea adapterSpinner = iAgregaVehiculosFragment.crearAdapterLinea(lineasVehiculos);
        lineas.setAdapter(adapterSpinner);

        lineas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LineasVehiculos lineasVehiculos = (LineasVehiculos) adapterView.getItemAtPosition(i);
                Log.d("idmarcaa", String.valueOf(lineasVehiculos.getId()));
                mostrarSpinnerModelo();
                vehiculo.setLinea(lineasVehiculos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void mostrarSpinnerModelo() {

        modelo = iAgregaVehiculosFragment.crearSpinerModelo();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.modelo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelo.setAdapter(adapter);

        modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String modelo = (String) adapterView.getItemAtPosition(i);
                Log.d("modeloss", modelo);
                //vehiculo.setAnio(modelo);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void obtenerCombustibles() {
        Call<List<Combustibles>> combustiblesAll = MedidorApiAdapter.getApiService().getCombustiblesAll();

        combustiblesAll.enqueue(new Callback<List<Combustibles>>() {
            @Override
            public void onResponse(Call<List<Combustibles>> call, Response<List<Combustibles>> response) {

                if (response.isSuccessful()){
                    combustibles = (ArrayList<Combustibles>) response.body();
                    mostrarSpinnerCombustibles();
                }
            }

            @Override
            public void onFailure(Call<List<Combustibles>> call, Throwable t) {
            }
        });
    }

    @Override
    public void mostrarSpinnerCombustibles() {

        combustible = iAgregaVehiculosFragment.crearSpinerCombuatible();
        AdapterSpinnerCombustible adapterSpinner = iAgregaVehiculosFragment.crearAdapterCombustibles(combustibles);
        combustible.setAdapter(adapterSpinner);

        combustible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Combustibles combustible = adapterSpinner.getItem(i);
                vehiculo.setCombustible(combustible);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void obtenerColorVehiculo(int color, String colorHex) {
        Log.d("coloressss",  colorHex);
    }

    @Override
    public Boolean agregarVehiculo() {
        return vehiculo.getLinea() != null;
    }
}


