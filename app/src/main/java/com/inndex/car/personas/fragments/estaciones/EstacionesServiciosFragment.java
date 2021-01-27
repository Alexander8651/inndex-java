package com.inndex.car.personas.fragments.estaciones;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.mainactivity.MainActivity;
import com.inndex.car.personas.adapter.ExpLAdapter;
import com.inndex.car.personas.enums.EBancos;
import com.inndex.car.personas.enums.ECombustibles;
import com.inndex.car.personas.enums.EPuntoPago;
import com.inndex.car.personas.enums.ESoat;
import com.inndex.car.personas.enums.ETiendas;
import com.inndex.car.personas.model.Bancos;
import com.inndex.car.personas.model.EstacionCombustibles;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Horario;
import com.inndex.car.personas.model.PuntoPago;
import com.inndex.car.personas.model.Tiendas;
import com.inndex.car.personas.utils.Constantes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EstacionesServiciosFragment extends Fragment {

    Estaciones estaciones;
    float distancia;
    private MainActivity mainActivity;
    private Typeface light;
    private Typeface robotoRegular;

    public EstacionesServiciosFragment(MainActivity mainActivity, Typeface light) {
        this.mainActivity = mainActivity;
        this.light = light;
        this.robotoRegular = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public EstacionesServiciosFragment() {
        // Required empty public constructor
    }

    public static EstacionesServiciosFragment newInstance(String param1, String param2) {
        EstacionesServiciosFragment fragment = new EstacionesServiciosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estaciones = getArguments().getParcelable(Constantes.ESTACION_SELECCOINADA_KEY);
            this.distancia = getArguments().getFloat("distancia");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estaciones_servicios, container, false);

        final TextView direccion = root.findViewById(R.id.direccionBomba);
        final TextView marca = root.findViewById(R.id.marcaBomba);
        final TextView nombre = root.findViewById(R.id.nombreBomba);
        final TextView tvDistancia = root.findViewById(R.id.tv_estacion_servicios_distancia);
        final ImageView botonBack = root.findViewById(R.id.botonbackdetallebomba);
        botonBack.setOnClickListener(v -> {
            this.onDestroy();
            mainActivity.clickHome();
        });

        if (distancia < 1000) {
            tvDistancia.setText(String.format(Locale.ENGLISH, "%.2f m", distancia));
        } else {
            distancia = distancia / 1000;
            tvDistancia.setText(String.format(Locale.ENGLISH, "%.2f km", distancia));
        }

        final TextView titulocajeros = root.findViewById(R.id.titulocajero);
        final TextView tituloCorresponsales = root.findViewById(R.id.titulo_corresponsales);
        final TextView titulopuntopagos = root.findViewById(R.id.titulopuntospago);
        final TextView titulotiendaconvivencia = root.findViewById(R.id.titulotiendaconvivencia);
        final TextView titulosoat = root.findViewById(R.id.titulosoat);
        final TextView titulorestaurantes = root.findViewById(R.id.titulorestaurantes);
        final TextView titulohoteles = root.findViewById(R.id.titulohoteles);
        final TextView titulobanios = root.findViewById(R.id.titulobanios);
        final TextView titulolubricantes = root.findViewById(R.id.titulolubricantes);
        final TextView titulollanteria = root.findViewById(R.id.titulollanteria);
        final TextView titulolavaderos = root.findViewById(R.id.titulolavadero);
        final ImageView menuBomba = root.findViewById(R.id.img_menu_estacion_servicios);
        final TextView tvPreciosActualizados = root.findViewById(R.id.tv_estacion_servicios_precios_actualizados);

        final TextView tvVerOpiniones = root.findViewById(R.id.tv_estacion_servicios_ver_opiniones);
        final TextView tvCalificar = root.findViewById(R.id.tv_estacion_servicios_calificar);
        final TextView tvCalificacion = root.findViewById(R.id.tv_estacion_servicios_calificacion);
        menuBomba.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), menuBomba);
            popupMenu.getMenuInflater().inflate(R.menu.menu_estacion_servicios, popupMenu.getMenu());
            popupMenu.show();
        });

        tvVerOpiniones.setTypeface(light);
        tvCalificar.setTypeface(light);
        tvCalificacion.setTypeface(light);

        tvCalificacion.setText(String.format(Locale.ENGLISH, "%.1f", estaciones.getCalificacion()));
        float cal = 3.8f;
        //float cal =  (float)estaciones.getCalificacion()

        tvPreciosActualizados.setTypeface(light);
        titulocajeros.setTypeface(robotoRegular);
        tituloCorresponsales.setTypeface(robotoRegular);
        titulopuntopagos.setTypeface(robotoRegular);
        titulotiendaconvivencia.setTypeface(robotoRegular);
        titulosoat.setTypeface(robotoRegular);
        titulorestaurantes.setTypeface(robotoRegular);
        titulohoteles.setTypeface(robotoRegular);
        titulobanios.setTypeface(robotoRegular);
        titulolubricantes.setTypeface(robotoRegular);
        titulollanteria.setTypeface(robotoRegular);
        titulolavaderos.setTypeface(robotoRegular);

        direccion.setTypeface(light);
        tvDistancia.setTypeface(light);

        direccion.setText(estaciones.getDireccion());
        marca.setText(estaciones.getMarca());
        nombre.setText(estaciones.getNombre());
        combustibles(root);
        horariosycontacto(root);
        cajeros(root);
        initCorresponsales(root);
        puntosPago(root);
        tiendasConvivencia(root);
        soat(root);
        restaurantes(root);
        hotel(root);
        bano(root);
        lubricantes(root);
        llanteria(root);
        lavaderos(root);
        return root;
    }


    private void combustibles(View root) {

        View layCombustibles = root.findViewById(R.id.lay_combustibles);

        if (estaciones.getListEstacionCombustibles() != null && estaciones.getListEstacionCombustibles().size() > 0) {
            for (EstacionCombustibles bomba : estaciones.getListEstacionCombustibles()) {
                TextView nombreCombustible = null;
                TextView precioCombustible = null;

                if (bomba.getCombustible().getId().equals(ECombustibles.CORRIENTE.getId())) {
                    final LinearLayout corriente = root.findViewById(R.id.lay_corriente);
                    corriente.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreCorriente);
                    precioCombustible = root.findViewById(R.id.precioCorriente);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.EXTRA.getId())) {
                    final LinearLayout extra = root.findViewById(R.id.lay_extra);
                    extra.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreExtra);
                    precioCombustible = root.findViewById(R.id.precioExtra);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.DIESEL.getId())) {
                    final LinearLayout diesel = root.findViewById(R.id.DIESEL);
                    diesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreDiesel);
                    precioCombustible = root.findViewById(R.id.precioDiesel);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.DIESEL.getId())) {
                    final LinearLayout diesel = root.findViewById(R.id.DIESEL);
                    diesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreDiesel);
                    precioCombustible = root.findViewById(R.id.precioDiesel);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.BIODIESEL.getId())) {
                    final LinearLayout bioDiesel = root.findViewById(R.id.lay_bio_diesel);
                    bioDiesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombre_bio_diesel);
                    precioCombustible = root.findViewById(R.id.precio_bio_diesel);
                }

                if (nombreCombustible != null && precioCombustible != null) {
                    nombreCombustible.setTypeface(light);
                    precioCombustible.setTypeface(light);
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    DecimalFormat formatter = new DecimalFormat("###,###");

                    String sPrecio = formatter.format(Double.valueOf(bomba.getPrecio().intValue()));

                    //    precioCombustible.setText(getString(R.string.precio_combustible_placeholder, bomba.getPrecio().intValue()));
                    precioCombustible.setText(getString(R.string.precio_combustible_placeholder, sPrecio.replace(",", ".")));
                }
            }
        } else {
            layCombustibles.setVisibility(View.GONE);
        }
    }

    private void horariosycontacto(View root) {
        ExpandableListView spin = root.findViewById(R.id.expanded_horarios);
        ArrayList<String> listCategorias = new ArrayList<>();
        Map<String, ArrayList<Horario>> mapChild = new HashMap<>();
        View layHorarios = root.findViewById(R.id.horariosycontacto);

        if (estaciones.getListHorarios() != null && estaciones.getListHorarios().size() > 0) {

            ArrayList<Horario> horarios = new ArrayList<>(estaciones.getListHorarios());
            listCategorias.add("Horarios y contacto");
            mapChild.put(listCategorias.get(0), horarios);
            ExpLAdapter adapter = new ExpLAdapter(mapChild, listCategorias, requireContext());
            spin.setAdapter(adapter);
            spin.setIndicatorBounds(610, 0);
            spin.setOnGroupClickListener((parent, v, groupPosition, id) -> {
                View layout = root.findViewById(R.id.horariosycontacto);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                if (parent.isGroupExpanded(groupPosition)) {
                    params.height = 100;
                } else {
                    params.height = 500;
                }
                return false;
            });
        } else {
            layHorarios.setVisibility(View.GONE);
        }
    }

    private void cajeros(View root) {

        if (estaciones.getListCajeros() != null && estaciones.getListCajeros().size() > 0) {
            int counter = 0;
            ImageView imgCajero = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));
            for (Bancos cajero : estaciones.getListCajeros()) {
                if (cajero.getId().equals(EBancos.ATH.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoAth);
                }

                if (cajero.getId().equals(EBancos.CITIBANK.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroCitiBank);
                }

                if (cajero.getId().equals(EBancos.BANCOOMEVA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoomeva);
                }

                if (cajero.getId().equals(EBancos.BANCO_AGRARIO_DE_COLOMBIA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoAgrarioColombia);
                }

                if (cajero.getId().equals(EBancos.BANCOLOMBIA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancolombia);
                }

                if (cajero.getId().equals(EBancos.BANCO_PROCREDIT.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoProcredit);
                }

                if (cajero.getId().equals(EBancos.BANCO_POPULAR.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoPopular);
                }

                if (cajero.getId().equals(EBancos.BANCO_PICHINCHA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoPichincha);
                }

                if (cajero.getId().equals(EBancos.BANCO_OLD_MUTUAL.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoOldMutual);
                }

                if (cajero.getId().equals(EBancos.BANCO_GNB_SUDAMERIS.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoBngSudameris);
                }

                if (cajero.getId().equals(EBancos.BANCO_FALABELLA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroFalabella);
                }

                if (cajero.getId().equals(EBancos.BANCO_DE_OCCIDENTE.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoOccidente);
                }

                if (cajero.getId().equals(EBancos.BANCO_DE_BOGOTA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoBogota);
                }

                if (cajero.getId().equals(EBancos.BANCO_DAVIVIENDA.getId())) {
                    imgCajero = root.findViewById(R.id.cajeroBancoDavivienda);
                }

                if (cajero.getId().equals(EBancos.BANCO_ITAU.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroItau);
                }

                if (cajero.getId().equals(EBancos.BANCO_CORPBANCA.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoCorpBanca);
                }

                if (cajero.getId().equals(EBancos.BANCO_COOPERATIVO_COOPCENTRAL.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoCooperativoCoopcentral);
                }

                if (cajero.getId().equals(EBancos.BANCO_COLPATRIA.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoColpatria);
                }

                if (cajero.getId().equals(EBancos.BANCO_CAJA_SOCIAL.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoCajaSocial);
                }

                if (cajero.getId().equals(EBancos.BANCO_BBVA.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoBbva);
                }

                if (cajero.getId().equals(EBancos.BANCO_AV_VILLAS.getId())) {

                    imgCajero = root.findViewById(R.id.cajeroBancoAvvillas);
                }

                if (cajero.getId().equals(EBancos.SERVY_BANCA.getId())) {

                    imgCajero = root.findViewById(R.id.cajero_servy_banca);
                }

                if (imgCajero != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgCajero.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgCajero.setLayoutParams(params);
                    }
                    imgCajero.setVisibility(View.VISIBLE);
                    counter++;
                }
            }

        } else {
            ConstraintLayout cajeros = root.findViewById(R.id.cajeros);
            cajeros.setVisibility(View.GONE);
        }
    }

    private void initCorresponsales(View root) {
        if (estaciones.getListCorresponsales() != null && estaciones.getListCorresponsales().size() > 0) {
            ImageView imgCorresponsal = null;
            int counter = 0;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));

            for (Bancos corresponsal : estaciones.getListCorresponsales()) {

                if (corresponsal.getId().equals(EBancos.ATH.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoAth);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.CITIBANK.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalCitiBank);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCOOMEVA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoomeva);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_AGRARIO_DE_COLOMBIA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoAgrarioColombia);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCOLOMBIA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancolombia);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_PROCREDIT.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoProcredit);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_POPULAR.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoPopular);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_PICHINCHA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoPichincha);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_OLD_MUTUAL.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoOldMutual);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_GNB_SUDAMERIS.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoBngSudameris);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_FALABELLA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalFalabella);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_DE_OCCIDENTE.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoOccidente);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_DE_BOGOTA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoBogota);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_DAVIVIENDA.getId())) {

                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoDavivienda);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_ITAU.getId())) {

                    imgCorresponsal = root.findViewById(R.id.corresponsalItau);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_CORPBANCA.getId())) {

                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoCorpBanca);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_COOPERATIVO_COOPCENTRAL.getId())) {

                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoCooperativoCoopcentral);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }

                if (corresponsal.getId().equals(EBancos.BANCO_COLPATRIA.getId())) {

                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoColpatria);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }
                if (corresponsal.getId().equals(EBancos.BANCO_CAJA_SOCIAL.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoCajaSocial);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }
                if (corresponsal.getId().equals(EBancos.BANCO_BBVA.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoBbva);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }
                if (corresponsal.getId().equals(EBancos.BANCO_AV_VILLAS.getId())) {
                    imgCorresponsal = root.findViewById(R.id.corresponsalBancoAvvillas);
                    imgCorresponsal.setVisibility(View.VISIBLE);
                }
                if (counter == 0 && imgCorresponsal != null) {
                    params.setMargins(0, 0, 20, 0);
                    imgCorresponsal.setLayoutParams(params);
                } else if (counter > 0) {
                    params.setMargins(20, 0, 20, 0);
                    imgCorresponsal.setLayoutParams(params);
                }
                counter++;

            }
        } else {
            ConstraintLayout cajeros = root.findViewById(R.id.cajeros);
            cajeros.setVisibility(View.GONE);
        }
    }

    private void puntosPago(View root) {
        if (estaciones.getListPuntosPago() != null && estaciones.getListPuntosPago().size() > 0) {
            int counter = 0;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));
            ImageView imgPuntoPago = null;
            for (PuntoPago puntoPago : estaciones.getListPuntosPago()) {
                //
                if (puntoPago.getId().equals(EPuntoPago.BALOTO.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoPagoBaloto);
                }

                if (puntoPago.getId().equals(EPuntoPago.EFECTY.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoPagoEfecty);
                }

                if (puntoPago.getId().equals(EPuntoPago.SU_RED.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoPagoSuRed);
                }

                if (puntoPago.getId().equals(EPuntoPago.GIROS_Y_FINANZAS.getId())) {
                    imgPuntoPago = root.findViewById(R.id.punto_giros_y_finanzas);
                }

                if (puntoPago.getId().equals(EPuntoPago.MOVII_RED.getId())) {
                    imgPuntoPago = root.findViewById(R.id.punto_moviired);
                }

                if (puntoPago.getId().equals(EPuntoPago.PAGA_FACIL.getId())) {
                    imgPuntoPago = root.findViewById(R.id.punto_pagafacil);
                }

                if (puntoPago.getId().equals(EPuntoPago.WESTERN_UNION.getId())) {
                    imgPuntoPago = root.findViewById(R.id.punto_western_union);
                }

                if (puntoPago.getId().equals(EPuntoPago.REDEBAN.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoredeban);
                }

                if (puntoPago.getId().equals(EPuntoPago.SERVY_PAGOS.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoservypagos);
                }
                if (puntoPago.getId().equals(EPuntoPago.SUPER_GIROS.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntosupergiros);
                }
                if (puntoPago.getId().equals(EPuntoPago.PUNTO_RED.getId())) {
                    imgPuntoPago = root.findViewById(R.id.punto_red);
                }

                if (imgPuntoPago != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgPuntoPago.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgPuntoPago.setLayoutParams(params);
                    }
                    imgPuntoPago.setVisibility(View.VISIBLE);
                }


                counter++;

            }
        } else {
            ConstraintLayout puntosPagos = root.findViewById(R.id.puntospago);
            puntosPagos.setVisibility(View.GONE);
        }
    }

    private void tiendasConvivencia(View root) {
        if (estaciones.getListTiendas() != null && estaciones.getListTiendas().size() > 0) {
            int counter = 0;
            ImageView imgTienda = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));
            for (Tiendas tienda : estaciones.getListTiendas()) {
                //
                if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                    imgTienda = root.findViewById(R.id.puntoPagoBaloto);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaTigerMarket);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.OLIMPICA.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaOlimpica);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.JUSTO_Y_BUENO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaJustoBueno);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.EXITO_EXPRESS.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaExitoExpress);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.D_UNO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaD1);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.ALTOQUE.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaAltoque);
                    imgTienda.setVisibility(View.VISIBLE);
                }
                if (counter == 0 && imgTienda != null) {

                    params.setMargins(0, 0, 20, 0);
                    imgTienda.setLayoutParams(params);
                } else if (counter > 0) {
                    params.setMargins(20, 0, 20, 0);
                    imgTienda.setLayoutParams(params);
                }
                counter++;
            }

        } else {
            ConstraintLayout tiendasConvivencia = root.findViewById(R.id.tiendasconvivencia);
            tiendasConvivencia.setVisibility(View.GONE);
        }
    }

    private void soat(View root) {

        if (estaciones.getSoat() != null) {

            if (estaciones.getSoat().getId().equals(ESoat.ALLIANZ.getId())) {
                ImageView soatAllianz = root.findViewById(R.id.soatAllianz);
                soatAllianz.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.AXA_COLPATRIA.getId())) {
                ImageView soatAxa = root.findViewById(R.id.soatAxaColpatria);
                soatAxa.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.LIBERTY_SEGUROS.getId())) {
                ImageView soatLiberty = root.findViewById(R.id.soatLibertySeguros);
                soatLiberty.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.MAPRE_SEGUROS.getId())) {
                ImageView soatMapfre = root.findViewById(R.id.soatMapreSeguros);
                soatMapfre.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.MUNDIAL_DE_SEGUROS.getId())) {
                ImageView soatMundial = root.findViewById(R.id.soatMundialDeSeguros);
                soatMundial.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_BOLIVAR.getId())) {
                ImageView soatBolivar = root.findViewById(R.id.soatSegurosBolivar);
                soatBolivar.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_DEL_ESTADO.getId())) {
                ImageView soatEstado = root.findViewById(R.id.soatSegurosEstado);
                soatEstado.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_EXITO.getId())) {
                ImageView soatExito = root.findViewById(R.id.soatSegurosExito);
                soatExito.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_FALABELLA.getId())) {
                ImageView soatFalabella = root.findViewById(R.id.soatSegurosFalaBella);
                soatFalabella.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_LA_EQUIDAD.getId())) {
                ImageView soatEquidad = root.findViewById(R.id.soatSegurosEquidad);
                soatEquidad.setVisibility(View.VISIBLE);
            }
            if (estaciones.getSoat().getId().equals(ESoat.SURA_SEGUROS.getId())) {
                ImageView soatSura = root.findViewById(R.id.soatSuraSeguros);
                soatSura.setVisibility(View.VISIBLE);
            }
        } else {
            ConstraintLayout soat = root.findViewById(R.id.soat);
            soat.setVisibility(View.GONE);
        }
    }

    private void restaurantes(View root) {

        if (estaciones.getListRestaurantes() != null && estaciones.getListRestaurantes().size() > 0) {
            ImageView restaurante = root.findViewById(R.id.estacionRestaurante);
            restaurante.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout restaurante = root.findViewById(R.id.restautantes);
            restaurante.setVisibility(View.GONE);
        }
    }

    private void hotel(View root) {
        if (estaciones.getListHoteles() != null && estaciones.getListHoteles().size() > 0) {
            ImageView hotel = root.findViewById(R.id.estacionHotel);
            hotel.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout restaurante = root.findViewById(R.id.hoteles);
            restaurante.setVisibility(View.GONE);
        }
    }

    private void bano(View root) {

        if (estaciones.getTieneBanios() != null && estaciones.getTieneBanios()) {
            ImageView hotel = root.findViewById(R.id.estacionbanios);
            hotel.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout bano = root.findViewById(R.id.banios);
            bano.setVisibility(View.GONE);
        }
    }

    private void lubricantes(View root) {

        if (estaciones.getTieneVentaLubricante() != null) {
            if (estaciones.getTieneVentaLubricante()) {
                ImageView lubricantes = root.findViewById(R.id.estacionLubricantes);
                lubricantes.setVisibility(View.VISIBLE);
            } else {
                ConstraintLayout lubricantes = root.findViewById(R.id.lubricantes);
                lubricantes.setVisibility(View.GONE);
            }
        } else {
            ConstraintLayout lubricantes = root.findViewById(R.id.lubricantes);
            lubricantes.setVisibility(View.GONE);
        }
    }

    private void llanteria(View root) {
        if (estaciones.getTieneLlanteria() != null) {
            if (estaciones.getTieneLlanteria()) {
                ImageView llanteria = root.findViewById(R.id.estacionLlanteria);
                llanteria.setVisibility(View.VISIBLE);
            } else {

                ConstraintLayout llanteria = root.findViewById(R.id.llanteria);
                llanteria.setVisibility(View.GONE);
            }
        } else {

            ConstraintLayout llanteria = root.findViewById(R.id.llanteria);
            llanteria.setVisibility(View.GONE);
        }
    }

    private void lavaderos(View root) {

        if (estaciones.getTieneLavadero() != null) {
            if (estaciones.getTieneLavadero()) {
                ImageView lavadero = root.findViewById(R.id.estacionLavaderos);
                lavadero.setVisibility(View.VISIBLE);
            } else {
                ConstraintLayout lavadero = root.findViewById(R.id.lavaderos);
                lavadero.setVisibility(View.GONE);
            }
        } else {
            ConstraintLayout lavadero = root.findViewById(R.id.lavaderos);
            lavadero.setVisibility(View.GONE);
        }
    }

}