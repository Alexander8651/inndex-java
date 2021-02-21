package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.PuntosPagoAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.SegurosAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.TiendaAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.bancosEdsotrosServiciosAdapter;
import com.inndex.car.personas.model.Bancos;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.PuntoPago;
import com.inndex.car.personas.model.Soat;
import com.inndex.car.personas.model.Tiendas;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.ResponseServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterEdsOtrsServiciosFragment implements IPresenterEdsOtrosServicios {

    Context context;
    IEdsOtrosServiciosFragment iEdsOtrosServiciosFragment;
    Estaciones estaciones;
    ArrayList<Bancos> bancos;
    ArrayList<PuntoPago> puntoPagos;
    ArrayList<Tiendas> tiendas;
    ArrayList<Soat> soats;

    public PresenterEdsOtrsServiciosFragment(Context context, IEdsOtrosServiciosFragment iEdsOtrosServiciosFragment, Estaciones estaciones) {
        this.context = context;
        this.iEdsOtrosServiciosFragment = iEdsOtrosServiciosFragment;
        this.estaciones = estaciones;
        setearOtrosServicosData();
        setearChecbox();
        obtenerBancos();
        obtenerPuntosPago();
        obtenerTiendasConvivencia();
        obtenerSeguros();
        guardarUsuario();
    }

    @Override
    public void obtenerBancos() {


        Call<List<Bancos>> bancosCall = MedidorApiAdapter.getApiService().getBancos();
        bancosCall.enqueue(new Callback<List<Bancos>>() {
            @Override
            public void onResponse(Call<List<Bancos>> call, Response<List<Bancos>> response) {
                if (response.isSuccessful()) {
                    bancos = (ArrayList<Bancos>) response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Bancos>> call, Throwable t) {
            }
        });
    }

    @Override
    public void obtenerPuntosPago() {
        Call<List<PuntoPago>> puntoPagoCall = MedidorApiAdapter.getApiService().getPuntosPago();

        puntoPagoCall.enqueue(new Callback<List<PuntoPago>>() {
            @Override
            public void onResponse(Call<List<PuntoPago>> call, Response<List<PuntoPago>> response) {

                if (response.isSuccessful()) {
                    puntoPagos = (ArrayList<PuntoPago>) response.body();
                }
            }

            @Override
            public void onFailure(Call<List<PuntoPago>> call, Throwable t) {

            }
        });
    }

    @Override
    public void obtenerTiendasConvivencia() {
        Call<List<Tiendas>> tiendasCall = MedidorApiAdapter.getApiService().getTiendas();

        tiendasCall.enqueue(new Callback<List<Tiendas>>() {
            @Override
            public void onResponse(Call<List<Tiendas>> call, Response<List<Tiendas>> response) {

                if (response.isSuccessful()) {
                    tiendas = (ArrayList<Tiendas>) response.body();
                }

            }

            @Override
            public void onFailure(Call<List<Tiendas>> call, Throwable t) {

            }
        });
    }

    @Override
    public void obtenerSeguros() {
        Call<List<Soat>> soatCall = MedidorApiAdapter.getApiService().getSoat();

        soatCall.enqueue(new Callback<List<Soat>>() {
            @Override
            public void onResponse(Call<List<Soat>> call, Response<List<Soat>> response) {

                if (response.isSuccessful()) {
                    soats = (ArrayList<Soat>) response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Soat>> call, Throwable t) {

            }
        });
    }

    @Override
    public void mostrarDialogoCajeros() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        bancosEdsotrosServiciosAdapter cajerosEdsotrosServicios = new bancosEdsotrosServiciosAdapter(bancos, (ArrayList<Bancos>) estaciones.getListCajeros());

        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(cajerosEdsotrosServicios);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListCajeros(cajerosEdsotrosServicios.obtenerListaBancos());
            Log.d("bancorr", String.valueOf(estaciones.getListCajeros().size()));
            iEdsOtrosServiciosFragment.cajerosSeleccionados().setText("");
            for (Bancos e : estaciones.getListCajeros()) {

                iEdsOtrosServiciosFragment.cajerosSeleccionados().append(e.getNombre() + "," + " ");
                iEdsOtrosServiciosFragment.cajerosSeleccionados().setGravity(Gravity.CENTER);

            }
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();
    }

    @Override
    public void mostrarDialogoCorresponsales() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        bancosEdsotrosServiciosAdapter bancosEdsotrosServiciosAdapter = new bancosEdsotrosServiciosAdapter(bancos, (ArrayList<Bancos>) estaciones.getListCorresponsales());


        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(bancosEdsotrosServiciosAdapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListCorresponsales(bancosEdsotrosServiciosAdapter.obtenerListaBancos());
            Log.d("bancorr", String.valueOf(estaciones.getListCajeros().size()));

            iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setText("");
            for (Bancos e : estaciones.getListCorresponsales()) {

                if (estaciones.getListCorresponsales().size() == 1){
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre());
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setGravity(Gravity.CENTER);
                }else {
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre() + "," + " ");
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setGravity(Gravity.CENTER);
                }

            }
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();

    }

    @Override
    public void mostrarDialogoPuntosPago() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        PuntosPagoAdapter puntosPagoAdapter = new PuntosPagoAdapter(puntoPagos, (ArrayList<PuntoPago>) estaciones.getListPuntosPago());


        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(puntosPagoAdapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListPuntosPago(puntosPagoAdapter.obtenerListapuntosPago());
            Log.d("bancorr", String.valueOf(estaciones.getListCajeros().size()));

            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText("");
            for (PuntoPago e : estaciones.getListPuntosPago()) {

                if (estaciones.getListPuntosPago().size() == 1) {
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre());
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setGravity(Gravity.CENTER);
                } else {
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre() + "," + " ");
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setGravity(Gravity.CENTER);
                }
            }
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();

    }

    @Override
    public void mostrarDialogoTiendas() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        TiendaAdapter tiendaAdapter = new TiendaAdapter(tiendas, (ArrayList<Tiendas>) estaciones.getListTiendas());

        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(tiendaAdapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListTiendas(tiendaAdapter.obtenerListaTiendas());
            Log.d("bancorr", String.valueOf(estaciones.getListCajeros().size()));

            iEdsOtrosServiciosFragment.tiendasSeleccionados().setText("");
            for (Tiendas e : estaciones.getListTiendas()) {
                if (estaciones.getListTiendas().size() ==1){
                    iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre());
                }else {
                    iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre() + "," + " ");
                }
                iEdsOtrosServiciosFragment.tiendasSeleccionados().setGravity(Gravity.CENTER);

            }
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();

    }

    @Override
    public void mostrarDialogoSoat() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);
        ArrayList<Soat> soata = new ArrayList<>();

        SegurosAdapter soat = new SegurosAdapter(soats, estaciones.getSoat());


        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(soat);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setSoat(soat.obtenerSoat());
            Log.d("bancorr", String.valueOf(estaciones.getListCajeros().size()));

            iEdsOtrosServiciosFragment.segurosSeleccionados().setText("");

            iEdsOtrosServiciosFragment.segurosSeleccionados().append(estaciones.getSoat().getNombre());
            iEdsOtrosServiciosFragment.segurosSeleccionados().setGravity(Gravity.CENTER);
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();

    }

    @Override
    public void setearChecbox() {

        if (estaciones.getListRestaurantes() != null) {
            if (estaciones.getListRestaurantes().size() > 0) {
                iEdsOtrosServiciosFragment.restaurante().setChecked(true);
            }
        }

        if (estaciones.getListHoteles() != null) {
            if (estaciones.getListHoteles().size() > 0) {
                iEdsOtrosServiciosFragment.restaurante().setChecked(true);
            }
        }

        if (estaciones.getTieneBanios() != null) {
            if (estaciones.getTieneBanios()) {
                iEdsOtrosServiciosFragment.baniosPublicos().setChecked(true);
            }

        }

        if (estaciones.getTieneVentaLubricante() != null) {
            if (estaciones.getTieneVentaLubricante()) {
                iEdsOtrosServiciosFragment.lubricantes().setChecked(true);
            }
        }

        if (estaciones.getTieneLlanteria() != null) {
            if (estaciones.getTieneLavadero()) {
                iEdsOtrosServiciosFragment.llanteria().setChecked(true);
            }
        }

        if (estaciones.getTieneLavadero() != null) {
            if (estaciones.getTieneLavadero()) {
                iEdsOtrosServiciosFragment.lavadero().setChecked(true);
            }
        }
    }

    @Override
    public void guardarUsuario() {
        iEdsOtrosServiciosFragment.botonGuardar().setOnClickListener(v -> {

            /*
            if (estaciones.getListRestaurantes() != null){
                if (estaciones.getListRestaurantes().size() > 0){
                    iEdsOtrosServiciosFragment.restaurante().setChecked(true);
                }
            }

            if (estaciones.getListHoteles() != null){
                if (estaciones.getListHoteles().size() > 0){
                    iEdsOtrosServiciosFragment.restaurante().setChecked(true);
                }
            }
             */

            estaciones.setTieneBanios(iEdsOtrosServiciosFragment.baniosPublicos().isChecked());

            estaciones.setTieneVentaLubricante(iEdsOtrosServiciosFragment.lubricantes().isChecked());

            estaciones.setTieneLlanteria(iEdsOtrosServiciosFragment.llanteria().isChecked());
            estaciones.setTieneLavadero(iEdsOtrosServiciosFragment.lavadero().isChecked());

            Call<ResponseServices> guardar = MedidorApiAdapter.getApiService().updateStationOtherServices(estaciones);
            guardar.enqueue(new Callback<ResponseServices>() {
                @Override
                public void onResponse(Call<ResponseServices> call, Response<ResponseServices> response) {

                    if (response.isSuccessful()) {
                        Navigation.findNavController(v).navigateUp();
                    }
                }

                @Override
                public void onFailure(Call<ResponseServices> call, Throwable t) {

                }
            });

        });
    }

    @Override
    public void setearOtrosServicosData() {
        iEdsOtrosServiciosFragment.cajerosSeleccionados().setText("");
        for (Bancos e : estaciones.getListCajeros()) {

            if (estaciones.getListCajeros().size() == 1){
                iEdsOtrosServiciosFragment.cajerosSeleccionados().append(e.getNombre());
            }else {
                iEdsOtrosServiciosFragment.cajerosSeleccionados().append(e.getNombre() + "," + " ");
            }
            iEdsOtrosServiciosFragment.cajerosSeleccionados().setGravity(Gravity.CENTER);
        }

        iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setText("");
        for (Bancos e : estaciones.getListCorresponsales()) {
            if (estaciones.getListCorresponsales().size() == 1){
                iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre());
            }else {
                iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre() + "," + " ");
            }
            iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setGravity(Gravity.CENTER);
        }

        iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText("");
        for (PuntoPago e : estaciones.getListPuntosPago()) {

            if (estaciones.getListPuntosPago().size() == 1) {
                iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre());
            } else {
                iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre() + "," + " ");
            }
            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setGravity(Gravity.CENTER);
        }

        iEdsOtrosServiciosFragment.tiendasSeleccionados().setText("");
        for (Tiendas e : estaciones.getListTiendas()) {

            if (estaciones.getListTiendas().size() == 0){
                iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre());
            }else {
                iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre() + "," + " ");
            }
            iEdsOtrosServiciosFragment.tiendasSeleccionados().setGravity(Gravity.CENTER);

        }

        iEdsOtrosServiciosFragment.segurosSeleccionados().setText("");
        iEdsOtrosServiciosFragment.segurosSeleccionados().append(estaciones.getSoat().getNombre());
        iEdsOtrosServiciosFragment.segurosSeleccionados().setGravity(Gravity.CENTER);
    }
}
