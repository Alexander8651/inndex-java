package com.inndex.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.inndex.R;
import com.inndex.database.DataBaseHelper;
import com.inndex.fragments.estaciones.admin.adapters.AccesoriosYrepuestosAdapter;
import com.inndex.fragments.estaciones.admin.adapters.CompraYventaAdapter;
import com.inndex.fragments.estaciones.admin.adapters.MensajeriaAdapter;
import com.inndex.fragments.estaciones.admin.adapters.MetodosPagoAdapter;
import com.inndex.fragments.estaciones.admin.adapters.PuntosPagoAdapter;
import com.inndex.fragments.estaciones.admin.adapters.TiendaAdapter;
import com.inndex.fragments.estaciones.admin.adapters.BancosEdsotrosServiciosAdapter;
import com.inndex.model.AccesoriosYrepuestos;
import com.inndex.model.Bancos;
import com.inndex.model.CompraYventa;
import com.inndex.model.Estaciones;
import com.inndex.model.Hotel;
import com.inndex.model.Mensajeria;
import com.inndex.model.MetodoPago;
import com.inndex.model.PuntoPago;
import com.inndex.model.Restaurante;
import com.inndex.model.Soat;
import com.inndex.model.Tiendas;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.utils.ResponseServices;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<Mensajeria> listMensajeria;
    ArrayList<PuntoPago> puntoPagos;
    ArrayList<Tiendas> tiendas;
    ArrayList<Soat> soats;
    List<MetodoPago> metodoPagoList;
    List<AccesoriosYrepuestos> listAccesorios;
    List<CompraYventa> listCompraYventa;

    DataBaseHelper helper;

    public PresenterEdsOtrsServiciosFragment(Context context, IEdsOtrosServiciosFragment iEdsOtrosServiciosFragment, Estaciones estaciones) {
        this.context = context;
        this.iEdsOtrosServiciosFragment = iEdsOtrosServiciosFragment;
        this.estaciones = estaciones;

        if (estaciones.getListAccesoriosYrepuestos() == null)
            estaciones.setListAccesoriosYrepuestos(new ArrayList<>());
        if (estaciones.getListCajeros() == null)
            estaciones.setListCajeros(new ArrayList<>());
        if (estaciones.getListCompraYventa() == null)
            estaciones.setListCompraYventa(new ArrayList<>());
        if (estaciones.getListCorresponsales() == null)
            estaciones.setListCorresponsales(new ArrayList<>());
        if (estaciones.getListTiendas() == null)
            estaciones.setListTiendas(new ArrayList<>());
        if (estaciones.getListPuntosPago() == null)
            estaciones.setListPuntosPago(new ArrayList<>());
        if (estaciones.getListMetodosPago() == null)
            estaciones.setListMetodosPago(new ArrayList<>());
        if (estaciones.getListMensajeria() == null)
            estaciones.setListMensajeria(new ArrayList<>());

        helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);

        setearChecbox();
        obtenerBancos();
        obtenerPuntosPago();
        obtenerTiendasConvivencia();
        obtenerSeguros();
        obtenerMetodosPago();
        obtenerMensajeria();
        obtenerAccesoriosYRepuestos();
        obtenerCompraYventa();
        iEdsOtrosServiciosFragment.botonGuardar().setOnClickListener(this::guardarCambios
        );
    }

    @Override
    public void obtenerBancos() {

        try {
            Dao<Bancos, Long> daoBancos = helper.getDaoBancos();
            bancos = daoBancos.queryForAll();
            Collections.sort(bancos, (b1, b2) -> b1.getNombre().compareTo(b2.getNombre()));
            validateCorresponsales();
            validateCajeros();
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
                    validatePuntosPago();
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
                    if (tiendas != null)
                        Collections.sort(tiendas, (b1, b2) -> Normalizer.normalize(b1.getNombre(), Normalizer.Form.NFD).compareTo(Normalizer.normalize(b2.getNombre(), Normalizer.Form.NFD)));
                    validateTiendas();
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
                    if (soats != null)
                        Collections.sort(soats, (b1, b2) -> b1.getNombre().compareTo(b2.getNombre()));

                    validateSoat();
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
            Collections.sort(metodoPagoList, (b1, b2) -> Normalizer.normalize(b1.getNombre(), Normalizer.Form.NFD).compareTo(Normalizer.normalize(b2.getNombre(), Normalizer.Form.NFD)));

            validateMetodosPago();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void obtenerMensajeria() {
        Call<List<Mensajeria>> mensajeriaCall = MedidorApiAdapter.getApiService().getMensajeria();
        mensajeriaCall.enqueue(new Callback<List<Mensajeria>>() {
            @Override
            public void onResponse(Call<List<Mensajeria>> call, Response<List<Mensajeria>> response) {
                if (response.isSuccessful()) {
                    listMensajeria = response.body();
                    validateMensajeria();
                }
            }

            @Override
            public void onFailure(Call<List<Mensajeria>> call, Throwable t) {

            }
        });
    }

    @Override
    public void obtenerAccesoriosYRepuestos() {
        Call<List<AccesoriosYrepuestos>> accesoriosCall = MedidorApiAdapter.getApiService().getAccesoriosYrepuestos();
        accesoriosCall.enqueue(new Callback<List<AccesoriosYrepuestos>>() {
            @Override
            public void onResponse(Call<List<AccesoriosYrepuestos>> call, Response<List<AccesoriosYrepuestos>> response) {
                if (response.isSuccessful()) {
                    listAccesorios = response.body();
                    validateAccesoriosYRepuestos();
                } else
                    Toast.makeText(context, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<AccesoriosYrepuestos>> call, Throwable t) {
                Toast.makeText(context, "ERROR " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void obtenerCompraYventa() {
        Call<List<CompraYventa>> tiendasCall = MedidorApiAdapter.getApiService().getCompraYventa();
        tiendasCall.enqueue(new Callback<List<CompraYventa>>() {
            @Override
            public void onResponse(Call<List<CompraYventa>> call, Response<List<CompraYventa>> response) {
                if (response.isSuccessful()) {
                    listCompraYventa = response.body();
                    Collections.sort(listCompraYventa, (b1, b2) -> Normalizer.normalize(b1.getNombre(), Normalizer.Form.NFD).compareTo(Normalizer.normalize(b2.getNombre(), Normalizer.Form.NFD)));
                    validateCompraYventa();
                }
            }

            @Override
            public void onFailure(Call<List<CompraYventa>> call, Throwable t) {
                Toast.makeText(context, "ERROR " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void mostrarDialogoCajeros() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);

        BancosEdsotrosServiciosAdapter cajerosEdsotrosServicios = new BancosEdsotrosServiciosAdapter(bancos, (ArrayList<Bancos>) estaciones.getListCajeros());

        RecyclerView cajeros = v.findViewById(R.id.rvCajerosEds);
        cajeros.setAdapter(cajerosEdsotrosServicios);

        //cajerosEdsOtroServiciosAdapter.notifyDataSetChanged();


        RecyclerView cajeross = v.findViewById(R.id.rvCajerosEds);
        //cajeros.getRecycledViewPool().setMaxRecycledViews(0,0);
        cajeross.setAdapter(cajerosEdsotrosServicios);

        builder.setView(v);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {

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

        BancosEdsotrosServiciosAdapter bancosEdsotrosServiciosAdapter = new BancosEdsotrosServiciosAdapter(bancos, (ArrayList<Bancos>) estaciones.getListCorresponsales());
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BlackDialogTheme);
        String[] soatNombres = new String[soats.size()];
        int checkedItem = -1;
        for (int i = 0; i < soats.size(); i++) {
            soatNombres[i] = soats.get(i).getNombre();
            if (estaciones.getSoat() != null && soats.get(i).getId().equals(estaciones.getSoat().getId()))
                checkedItem = i;
        }
        builder.setSingleChoiceItems(soatNombres, checkedItem, (dialog, which) -> {
            Soat seguroSelected = soats.get(which);
            estaciones.setSoat(seguroSelected);
        });
        builder.setPositiveButton("Aceptar", (dialogInterface, i) ->
                validateSoat()
        );
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.setNeutralButton("Eliminar", (dialogInterface, i) -> {
            estaciones.setSoat(null);
            validateSoat();
        });
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
    public void mostrarDialogoMensajeria() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);
        MensajeriaAdapter adapter = new MensajeriaAdapter(listMensajeria, estaciones.getListMensajeria());

        RecyclerView rvMensajeria = v.findViewById(R.id.rvCajerosEds);
        rvMensajeria.setAdapter(adapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListMensajeria(adapter.obtenerListaMensajeria());
            validateMensajeria();
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();
    }

    @Override
    public void mostrarDialogoAccesorios() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);
        AccesoriosYrepuestosAdapter adapter = new AccesoriosYrepuestosAdapter(listAccesorios, estaciones.getListAccesoriosYrepuestos());

        RecyclerView rvAccesorios = v.findViewById(R.id.rvCajerosEds);
        rvAccesorios.setAdapter(adapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListAccesoriosYrepuestos(adapter.obtenerListaaccesorios());
            validateAccesoriosYRepuestos();
        });
        builder.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        builder.show();
    }

    @Override
    public void mostrarDialogoCompraYventa() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogcajeroselectronicos, null);
        CompraYventaAdapter adapter = new CompraYventaAdapter(listCompraYventa, estaciones.getListCompraYventa());

        RecyclerView rvCompraYventa = v.findViewById(R.id.rvCajerosEds);
        rvCompraYventa.setAdapter(adapter);

        builder.setView(v);

        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            estaciones.setListCompraYventa(adapter.obtenerListaCompraYventas());
            validateCompraYventa();
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

        if (estaciones.getTieneMecanicaGeneral() != null && estaciones.getTieneMecanicaGeneral()) {
            iEdsOtrosServiciosFragment.cbMecanicaGeneral().setChecked(true);
        }

        if (estaciones.getTieneLicoreria() != null && estaciones.getTieneLicoreria()) {
            iEdsOtrosServiciosFragment.cbLicoreria().setChecked(true);
        }

        if (estaciones.getTieneFerreteria() != null && estaciones.getTieneFerreteria()) {
            iEdsOtrosServiciosFragment.cbFerreteria().setChecked(true);
        }

        if (estaciones.getTieneVentaBaterias() != null && estaciones.getTieneVentaBaterias()) {
            iEdsOtrosServiciosFragment.cbVentaBaterias().setChecked(true);
        }

        if (estaciones.getTieneVentaLLantas() != null && estaciones.getTieneVentaLLantas()) {
            iEdsOtrosServiciosFragment.cbVentaLlantas().setChecked(true);
        }

        if (estaciones.getTieneCambioAceite() != null && estaciones.getTieneCambioAceite()) {
            iEdsOtrosServiciosFragment.cbCambioAceite().setChecked(true);
        }

        if (estaciones.getTieneCafeteriaPanaderia() != null && estaciones.getTieneCafeteriaPanaderia()) {
            iEdsOtrosServiciosFragment.cbCafeteria().setChecked(true);
        }

        if (estaciones.getTieneBebidas() != null && estaciones.getTieneBebidas()) {
            iEdsOtrosServiciosFragment.cbBebidas().setChecked(true);
        }

        if (estaciones.getTieneCda() != null && estaciones.getTieneCda()) {
            iEdsOtrosServiciosFragment.cbCda().setChecked(true);
        }

    }

    @Override
    public void guardarCambios(View v) {

        Estaciones estacionOnlyId = new Estaciones();
        estacionOnlyId.setId(estaciones.getId());

        //restauurants have been removed
        if (estaciones.getListRestaurantes() != null && estaciones.getListRestaurantes().size() > 0 && !iEdsOtrosServiciosFragment.restaurante().isChecked()) {

            estaciones.setListRestaurantes(null);
        } else if (iEdsOtrosServiciosFragment.restaurante().isChecked()) {
            Restaurante restaurante = new Restaurante();
            restaurante.setEstaciones(estacionOnlyId);
            restaurante.setNombre("AUTO-GENERATED");
            List<Restaurante> listRestaurante = new ArrayList<>();
            listRestaurante.add(restaurante);
            estaciones.setListRestaurantes(listRestaurante);
        }

        //hotels have been removed
        if (estaciones.getListHoteles() != null && estaciones.getListHoteles().size() > 0 && !iEdsOtrosServiciosFragment.hotel().isChecked()) {
            estaciones.setListHoteles(null);
        } else if (iEdsOtrosServiciosFragment.hotel().isChecked()) {
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

        estaciones.setTieneBebidas(iEdsOtrosServiciosFragment.cbBebidas().isChecked());
        estaciones.setTieneCafeteriaPanaderia(iEdsOtrosServiciosFragment.cbCafeteria().isChecked());
        estaciones.setTieneCambioAceite(iEdsOtrosServiciosFragment.cbCambioAceite().isChecked());
        estaciones.setTieneCda(iEdsOtrosServiciosFragment.cbCda().isChecked());
        estaciones.setTieneFerreteria(iEdsOtrosServiciosFragment.cbFerreteria().isChecked());
        estaciones.setTieneLicoreria(iEdsOtrosServiciosFragment.cbLicoreria().isChecked());
        estaciones.setTieneVentaBaterias(iEdsOtrosServiciosFragment.cbVentaBaterias().isChecked());
        estaciones.setTieneVentaLLantas(iEdsOtrosServiciosFragment.cbVentaLlantas().isChecked());
        estaciones.setTieneMecanicaGeneral(iEdsOtrosServiciosFragment.cbMecanicaGeneral().isChecked());

        Gson gson = new Gson();
        Log.e("EST", gson.toJson(estaciones));

        System.out.println(gson.toJson(estaciones));

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
    }

    @Override
    public void setearOtrosServicosData() {

    }

    private void validateCompraYventa() {
        iEdsOtrosServiciosFragment.tvCompraYventaSeleccionados().setText(context.getString(R.string.selecciona_compra_y_venta));
        if (estaciones.getListCompraYventa() != null && estaciones.getListCompraYventa().size() > 0) {
            iEdsOtrosServiciosFragment.tvCompraYventaSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (CompraYventa e : estaciones.getListCompraYventa()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListCompraYventa().indexOf(e) == estaciones.getListCompraYventa().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.tvCompraYventaSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.tvCompraYventaSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validateAccesoriosYRepuestos() {
        iEdsOtrosServiciosFragment.tvAccesoriosSeleccionados().setText(context.getString(R.string.selecciona_accesorios_y_repuestos));
        if (estaciones.getListAccesoriosYrepuestos() != null && estaciones.getListAccesoriosYrepuestos().size() > 0) {
            iEdsOtrosServiciosFragment.tvAccesoriosSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (AccesoriosYrepuestos e : estaciones.getListAccesoriosYrepuestos()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListAccesoriosYrepuestos().indexOf(e) == estaciones.getListAccesoriosYrepuestos().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.tvAccesoriosSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.tvAccesoriosSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validateSoat() {
        iEdsOtrosServiciosFragment.segurosSeleccionados().setText("");
        if (estaciones.getSoat() != null) {
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
                if (estaciones.getListCajeros().indexOf(e) == estaciones.getListCajeros().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
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
            StringBuilder stringBuilder = new StringBuilder();
            for (Bancos e : estaciones.getListCorresponsales()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListCorresponsales().indexOf(e) == estaciones.getListCorresponsales().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.corresponsalesSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validateMetodosPago() {
        iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setText(
                context.getString(R.string.selecciona_los_metodos_de_pago)
        );
        if (estaciones.getListMetodosPago() != null && estaciones.getListMetodosPago().size() > 0) {
            iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (MetodoPago e : estaciones.getListMetodosPago()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListMetodosPago().indexOf(e) == estaciones.getListMetodosPago().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.metodosPagoSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validateTiendas() {
        iEdsOtrosServiciosFragment.tiendasSeleccionados().setText(
                context.getString(R.string.selecciona_las_tiendas)
        );
        if (estaciones.getListTiendas() != null && estaciones.getListTiendas().size() > 0) {
            iEdsOtrosServiciosFragment.tiendasSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (Tiendas e : estaciones.getListTiendas()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListTiendas().indexOf(e) == estaciones.getListTiendas().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.tiendasSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.tiendasSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validatePuntosPago() {
        iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText(context.getString(R.string.selecciona_los_puntos_de_pagos));

        if (estaciones.getListPuntosPago() != null && estaciones.getListPuntosPago().size() > 0) {
            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (PuntoPago e : estaciones.getListPuntosPago()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListPuntosPago().indexOf(e) == estaciones.getListPuntosPago().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.puntosPagoSeleccionados().setGravity(Gravity.CENTER);
        }
    }

    private void validateMensajeria() {
        iEdsOtrosServiciosFragment.mensajeriaSeleccionados().setText(context.getString(R.string.selecciona_las_empresas_de_mensajer_a));

        if (estaciones.getListMensajeria() != null && estaciones.getListMensajeria().size() > 0) {
            iEdsOtrosServiciosFragment.mensajeriaSeleccionados().setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (Mensajeria e : estaciones.getListMensajeria()) {
                stringBuilder.append(e.getNombre()).append(",").append(" ");
                if (estaciones.getListMensajeria().indexOf(e) == estaciones.getListMensajeria().size() - 1) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }
            }
            iEdsOtrosServiciosFragment.mensajeriaSeleccionados().setText(stringBuilder.toString());
            iEdsOtrosServiciosFragment.mensajeriaSeleccionados().setGravity(Gravity.CENTER);
        }
    }
}
