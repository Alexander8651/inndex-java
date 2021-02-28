package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.CajerosEdsOtroServiciosAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.MetodosPagoAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.PuntosPagoAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.SegurosAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.TiendaAdapter;
import com.inndex.car.personas.fragments.estaciones.admin.adapters.bancosEdsotrosServiciosAdapter;
import com.inndex.car.personas.model.Bancos;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Hotel;
import com.inndex.car.personas.model.MetodoPago;
import com.inndex.car.personas.model.PuntoPago;
import com.inndex.car.personas.model.Restaurante;
import com.inndex.car.personas.model.Soat;
import com.inndex.car.personas.model.Tiendas;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.ResponseServices;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterEdsOtrsServiciosFragment implements IPresenterEdsOtrosServicios {

    Context context;
    IEdsOtrosServiciosFragment iEdsOtrosServiciosFragment;
    Estaciones estaciones;
    List<Bancos> bancos;
    ArrayList<PuntoPago> puntoPagos;
    ArrayList<Tiendas> tiendas;
    ArrayList<Soat> soats;
    List<MetodoPago> metodoPagoList;
    DataBaseHelper helper;

    public PresenterEdsOtrsServiciosFragment(Context context, IEdsOtrosServiciosFragment iEdsOtrosServiciosFragment, Estaciones estaciones) {
        this.context = context;
        this.iEdsOtrosServiciosFragment = iEdsOtrosServiciosFragment;
        this.estaciones = estaciones;
        helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        setearOtrosServicosData();
        setearChecbox();
        obtenerBancos();
        obtenerPuntosPago();
        obtenerTiendasConvivencia();
        obtenerSeguros();
        obtenerMetodosPago();
        guardarCambios();

    }

    @Override
    public void obtenerBancos() {

        try {
            Dao<Bancos, Long> daoBancos = helper.getDaoBancos();
            bancos = daoBancos.queryForAll();
            Collections.sort(bancos, (b1, b2) -> b1.getNombre().compareTo(b2.getNombre()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                    Collections.sort(tiendas, (b1, b2) -> b1.getNombre().compareTo(b2.getNombre()));
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
    public void obtenerMetodosPago() {
        try {
            Dao<MetodoPago, Long> metodoPagoDao = helper.getDaoMetodoPago();
            metodoPagoList = metodoPagoDao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void mostrarDialogoCajeros() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        bancosEdsotrosServiciosAdapter cajerosEdsotrosServicios = new bancosEdsotrosServiciosAdapter(bancos, (ArrayList<Bancos>) estaciones.getListCajeros());
        //CajerosEdsOtroServiciosAdapter cajerosEdsOtroServiciosAdapter =new CajerosEdsOtroServiciosAdapter((ArrayList<Bancos>) bancos,(ArrayList<Bancos>) estaciones.getListCajeros(),context);

        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(cajerosEdsotrosServicios);

        //cajerosEdsOtroServiciosAdapter.notifyDataSetChanged();


        RecyclerView cajeross = v.findViewById(R.id.rvCajerosEds);
        //cajeros.getRecycledViewPool().setMaxRecycledViews(0,0);
        cajeross.setAdapter(cajerosEdsotrosServicios);


        builder.setView(v);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {

            Log.d("cajerosss", String.valueOf(cajerosEdsotrosServicios.obtenerListaBancos().size()));
            estaciones.setListCajeros(cajerosEdsotrosServicios.obtenerListaBancos());

            List<Bancos> listBancos = cajerosEdsotrosServicios.obtenerListaBancos();
            estaciones.setListCajeros(listBancos);
            validateCajeros();
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
        //CajerosEdsOtroServiciosAdapter cajerosEdsOtroServiciosAdapter = new CajerosEdsOtroServiciosAdapter((ArrayList<Bancos>) bancos,(ArrayList<Bancos>) estaciones.getListCorresponsales(),context);
        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(bancosEdsotrosServiciosAdapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListCorresponsales(bancosEdsotrosServiciosAdapter.obtenerListaBancos());
            validateCorresponsales();
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


        Log.d("estoooooo",estaciones.getListPuntosPago().get(0).getNombre());


        ListView cajeros = v.findViewById(R.id.rvCajerosEds);
        //cajeros.setAdapter(puntosPagoAdapter);
        RecyclerView puntosPago = v.findViewById(R.id.rvCajerosEds);
        puntosPago.setAdapter(puntosPagoAdapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListPuntosPago(puntosPagoAdapter.obtenerListapuntosPago());
            validatePuntosPago();
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
            validateTiendas();
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
        SegurosAdapter soat = new SegurosAdapter(soats, estaciones.getSoat());

        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(soat);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setSoat(soat.obtenerSoat());
            iEdsOtrosServiciosFragment.segurosSeleccionados().setText("");

            iEdsOtrosServiciosFragment.segurosSeleccionados().append(estaciones.getSoat().getNombre());
            iEdsOtrosServiciosFragment.segurosSeleccionados().setGravity(Gravity.CENTER);
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();
    }

    @Override
    public void mostrarDialogoMetodosPago() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);
        MetodosPagoAdapter adapter = new MetodosPagoAdapter(metodoPagoList, estaciones.getListMetodosPago());

        RecyclerView rvMetodosPago = v.findViewById(R.id.rvCajerosEds);
        rvMetodosPago.setAdapter(adapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListMetodosPago(adapter.obtenerListapuntosPago());
            validateMetodosPago();
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();
    }

    @Override
    public void setearChecbox() {

        if (estaciones.getListRestaurantes() != null && estaciones.getListRestaurantes().size() > 0) {
            iEdsOtrosServiciosFragment.restaurante().setChecked(true);
        }

        if (estaciones.getListHoteles() != null && estaciones.getListHoteles().size() > 0) {
            iEdsOtrosServiciosFragment.hotel().setChecked(true);
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

        if (estaciones.getTieneDroguerias() != null && estaciones.getTieneDroguerias()) {
            iEdsOtrosServiciosFragment.cbFarmacia().setChecked(true);
        }

        if (estaciones.getTieneServiteca() != null && estaciones.getTieneServiteca()) {
            iEdsOtrosServiciosFragment.cbServiteca().setChecked(true);
        }

    }

    @Override
    public void guardarCambios() {
        iEdsOtrosServiciosFragment.botonGuardar().setOnClickListener(v -> {

            Estaciones estacionOnlyId = new Estaciones();
            estacionOnlyId.setId(estaciones.getId());

            //restauurants have been removed
            if (!iEdsOtrosServiciosFragment.restaurante().isChecked() && estaciones.getListRestaurantes() != null && estaciones.getListRestaurantes().size() > 0) {
                estaciones.setListRestaurantes(null);
            } else {
                Restaurante restaurante = new Restaurante();
                restaurante.setEstaciones(estacionOnlyId);
                restaurante.setNombre("AUTO-GENERATED");
                List<Restaurante> listRestaurante = new ArrayList<>();
                listRestaurante.add(restaurante);
                estaciones.setListRestaurantes(listRestaurante);
            }

            //hotels have been removed
            if (!iEdsOtrosServiciosFragment.hotel().isChecked() && estaciones.getListHoteles() != null && estaciones.getListHoteles().size() > 0) {
                estaciones.setListHoteles(null);
            } else {
                Hotel hotel = new Hotel();
                hotel.setEstaciones(estacionOnlyId);
                hotel.setNombre("AUTO-GENERATED");
                List<Hotel> listHotel = new ArrayList<>();
                listHotel.add(hotel);
                estaciones.setListHoteles(listHotel);
            }

            estaciones.setTieneBanios(iEdsOtrosServiciosFragment.baniosPublicos().isChecked());
            estaciones.setTieneVentaLubricante(iEdsOtrosServiciosFragment.lubricantes().isChecked());
            estaciones.setTieneLlanteria(iEdsOtrosServiciosFragment.llanteria().isChecked());
            estaciones.setTieneLavadero(iEdsOtrosServiciosFragment.lavadero().isChecked());

            estaciones.setTieneDroguerias(iEdsOtrosServiciosFragment.cbFarmacia().isChecked());
            estaciones.setTieneServiteca(iEdsOtrosServiciosFragment.cbServiteca().isChecked());

            //Gson gson = new Gson();
            //Log.e("EST", gson.toJson(estaciones));

            Call<ResponseServices> guardar = MedidorApiAdapter.getApiService().updateStationOtherServices(estaciones);
            guardar.enqueue(new Callback<ResponseServices>() {
                @Override
                public void onResponse(Call<ResponseServices> call, Response<ResponseServices> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(context, "INFORMACION ACTUALIZADA EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigateUp();
                    } else {
                        Toast.makeText(context, "CODE " + response.code(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseServices> call, Throwable t) {
                    Toast.makeText(context, "ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public void setearOtrosServicosData() {
        validateTiendas();
        validateCajeros();
        validateCorresponsales();
        validatePuntosPago();
        validateMetodosPago();
        if (estaciones.getSoat() != null) {
            iEdsOtrosServiciosFragment.segurosSeleccionados().setText("");
            iEdsOtrosServiciosFragment.segurosSeleccionados().append(estaciones.getSoat().getNombre());
            iEdsOtrosServiciosFragment.segurosSeleccionados().setGravity(Gravity.CENTER);
        } else {
            iEdsOtrosServiciosFragment.segurosSeleccionados().setText(context.getString(R.string.selecciona_soat));
        }
    }

    private void validateCajeros() {
        iEdsOtrosServiciosFragment.cajerosSeleccionados().setText(context.getString(R.string.selecciona_los_cajeros_electronicos));
        if (estaciones.getListCajeros() != null && estaciones.getListCajeros().size() > 0) {
            iEdsOtrosServiciosFragment.cajerosSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (Bancos e : estaciones.getListCajeros()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
//                iEdsOtrosServiciosFragment.cajerosSeleccionados().append(e.getNombre() + "," + " ");

                if (estaciones.getListCajeros().indexOf(e) == estaciones.getListCajeros().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 1);
                }
            }
            iEdsOtrosServiciosFragment.cajerosSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.cajerosSeleccionados().setGravity(Gravity.CENTER);

        }
    }

    private void validateCorresponsales() {
        iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setText(context.getString(R.string.selecciona_los_corresponsales));

        if (estaciones.getListCorresponsales() != null && estaciones.getListCorresponsales().size() > 0) {
            iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setText("");
            for (Bancos e : estaciones.getListCorresponsales()) {
                if (estaciones.getListCorresponsales().size() == 1) {
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre());
                } else {
                    iEdsOtrosServiciosFragment.corresponsalesSeleccionados().append(e.getNombre() + "," + " ");
                }
                iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setGravity(Gravity.CENTER);
            }
        }
    }

    private void validateMetodosPago() {
        iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setText(
                context.getString(R.string.selecciona_los_metodos_de_pago)
        );
        if (estaciones.getListMetodosPago() != null && estaciones.getListMetodosPago().size() > 0) {
            iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setText("");
            for (MetodoPago e : estaciones.getListMetodosPago()) {

                if (estaciones.getListMetodosPago().size() == 0) {
                    iEdsOtrosServiciosFragment.metodosPagoSeleccionados().append(e.getNombre());
                } else {
                    iEdsOtrosServiciosFragment.metodosPagoSeleccionados().append(e.getNombre() + "," + " ");
                }
                iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setGravity(Gravity.CENTER);
            }
        }
    }

    private void validateTiendas() {
        iEdsOtrosServiciosFragment.tiendasSeleccionados().setText(
                context.getString(R.string.selecciona_las_tiendas)
        );

        if (estaciones.getListTiendas() != null && estaciones.getListTiendas().size() > 0) {
            iEdsOtrosServiciosFragment.tiendasSeleccionados().setText("");
            for (Tiendas e : estaciones.getListTiendas()) {

                if (estaciones.getListTiendas().size() == 0) {
                    iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre());
                } else {
                    iEdsOtrosServiciosFragment.tiendasSeleccionados().append(e.getNombre() + "," + " ");
                }
                iEdsOtrosServiciosFragment.tiendasSeleccionados().setGravity(Gravity.CENTER);
            }
        }
    }

    private void validatePuntosPago() {
        iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText(context.getString(R.string.selecciona_los_puntos_de_pagos));

        if (estaciones.getListPuntosPago() != null && estaciones.getListPuntosPago().size() > 0) {
            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText("");
            for (PuntoPago e : estaciones.getListPuntosPago()) {
                if (estaciones.getListPuntosPago().size() == 1) {
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre());
                } else {
                    iEdsOtrosServiciosFragment.puntosPagoSeleccionados().append(e.getNombre() + "," + " ");
                }
                iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setGravity(Gravity.CENTER);
            }
        }
    }
}
