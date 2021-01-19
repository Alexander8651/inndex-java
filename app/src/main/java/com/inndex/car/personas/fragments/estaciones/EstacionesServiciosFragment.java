package com.inndex.car.personas.fragments.estaciones;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EstacionesServiciosFragment extends Fragment {

    Estaciones estaciones;
    float distancia;
    private MainActivity mainActivity;
    private Typeface light;

    public EstacionesServiciosFragment(MainActivity mainActivity, Typeface light) {
        this.mainActivity = mainActivity;
        this.light = light;
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
            distancia = getArguments().getFloat("distancia");
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

        direccion.setText(estaciones.getDireccion());
        marca.setText(estaciones.getMarca());
        nombre.setText(estaciones.getNombre());
        tvDistancia.setText(String.format(Locale.ENGLISH, "%.2f m", distancia));
        combustibles(root);
        horaciosycontacto(root);
        cajeros(root);
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

        if(estaciones.getListEstacionCombustibles() != null ) {
            for (EstacionCombustibles bomba : estaciones.getListEstacionCombustibles()) {

                if (bomba.getCombustible().getId().equals(ECombustibles.CORRIENTE.getId())) {
                    Log.d("corriente", bomba.getCombustible().getNombre().toString());
                    TextView nombreCombustible = root.findViewById(R.id.nombreCorriente);
                    TextView precioCombustible = root.findViewById(R.id.precioCorriente);
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    precioCombustible.setText("$" + bomba.getPrecio().toString());

                }

                if (bomba.getCombustible().getId().equals(ECombustibles.EXTRA.getId())) {
                    Log.d("extra", bomba.getCombustible().getNombre().toString());
                    TextView nombreCombustible = root.findViewById(R.id.nombreExtra);
                    TextView precioCombustible = root.findViewById(R.id.precioExtra);
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    precioCombustible.setText("$" + bomba.getPrecio().toString());
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.DIESEL.getId())) {
                    final LinearLayout diesel = root.findViewById(R.id.DIESEL);
                    diesel.setVisibility(View.VISIBLE);
                    Log.d("diesel", bomba.getCombustible().getNombre());
                    TextView nombreCombustible = root.findViewById(R.id.nombreDiesel);
                    TextView precioCombustible = root.findViewById(R.id.precioDiesel);
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    precioCombustible.setText("$" + bomba.getPrecio().toString());
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.GNV.getId())) {
                    final LinearLayout gnv = root.findViewById(R.id.GNV);
                    gnv.setVisibility(View.VISIBLE);
                    Log.d("GNV", bomba.getCombustible().getNombre());
                    TextView nombreCombustible = root.findViewById(R.id.nombreGNV);
                    TextView precioCombustible = root.findViewById(R.id.precioGNV);
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    precioCombustible.setText("$" + bomba.getPrecio().toString());
                }
            }
        }
    }

    private void horaciosycontacto(View root) {
        Spinner spin = root.findViewById(R.id.spinnerHorarios);
        //spin.setOnItemSelectedListener(this);
        List<Horario> horarios = new ArrayList();


        if (estaciones.getListHorarios() != null) {
            for (Horario horario : estaciones.getListHorarios()) {
                //Log.d("horarios", String.valueOf((horario.getFin())));
                horarios.add(horario);
                /*
                Long dia = horario.getDia();
                //Log.d("horarios", dia.toString());
                //Log.d("horarios", EDias.DOMINGO.getId().toString());

                if (dia.equals(EDias.DOMINGO.getId())) {
                    if (horario.getAbiertoSiempre() != null){
                        Log.d("meejcuo", "domingo");
                        if (horario.getAbiertoSiempre()){
                            String horarioAbierto = "Abierto 24 horas";
                            horarios.add(horarioAbierto);
                        }
                    }


                }

                if (dia.equals(EDias.LUNES.getId())) {
                    Log.d("horarios", "lunes");
                    if (horario.getAbiertoSiempre() !=null){
                        if (horario.getAbiertoSiempre()){
                            Log.d("meejcuo", "lunes");
                            String horarioAbierto = "Lunes                 Abierto 24 horas";
                            horarios.add(horarioAbierto);
                        }else {
                            Log.d("meejcuo", "luneselse");

                        }
                    }else {
                        Log.d("meejcuo", "luneselse");

                    }
                }

                if (dia.equals(EDias.MARTES.getId())) {
                    Log.d("horarios", "marter");

                    if (horario.getAbiertoSiempre() !=null){
                        if (horario.getAbiertoSiempre()){
                            Log.d("meejcuo", "martes");
                            String horarioAbierto = "Martes                 Abierto 24 horas";
                            horarios.add(horarioAbierto);
                        }else {
                            Log.d("meejcuo", "luneselse");

                        }
                    }else {
                        Log.d("meejcuo", "marteselse");

                    }
                }

                if (dia.equals(EDias.MIERCOLES.getId())) {
                    Log.d("horarios", "miercoles");
                }

                if (dia.equals(EDias.JUEVES.getId())) {
                    Log.d("horarios", "jueves");


                }

                if (dia.equals(EDias.VIERNES.getId())) {
                    Log.d("horarios", "viernes");

                    if (horario.getAbiertoSiempre() !=null){
                        if (horario.getAbiertoSiempre()){
                            Log.d("meejcuo", "viernes");
                            String horarioAbierto = "Viernes                 Abierto 24 horas";
                            horarios.add(horarioAbierto);
                        }else {
                            Log.d("meejcuo", "luneselse");

                        }
                    }else {
                        Log.d("meejcuo", "vierneselse");
                        String horarioAbierto = "Viernes         " +"Inicio: " +horario.getInicio().toString()+"Fin: "+horario.getFin().toString();
                        horarios.add(horarioAbierto);

                    }
                }

                if (dia.equals(EDias.SABADO.getId())) {
                    Log.d("horarios", "sabado");
                    if (horario.getAbiertoSiempre() !=null){
                        if (horario.getAbiertoSiempre()){
                            Log.d("meejcuo", "sabado");
                            String horarioAbierto = "Sabado                 Abierto 24 horas";
                            horarios.add(horarioAbierto);
                        }else {
                            Log.d("meejcuo", "luneselse");

                        }
                    }else {
                        Log.d("meejcuo", "sabadoelse");
                        String horarioAbierto = "Sabado         " +"Inicio: " +horario.getInicio().toString()+"Fin: "+horario.getFin().toString();
                        horarios.add(horarioAbierto);

                    }
                }


                 */
            }
        }

        //Creating the ArrayAdapter instance having the country list
        //ArrayAdapter aa = new AdapterSpinner(getActivity(),  horarios);
        //aa.setDropDownViewResource(R.layout.itemspinners);
        //Setting the ArrayAdapter data on the Spinner
        //spin.setAdapter(aa);
    }

    private void cajeros(View root) {
        if (estaciones.getListCajeros() != null) {
            // Log.d("Banco", String.valueOf(estaciones.getListCajeros().get(0)));

            if (estaciones.getListCajeros() != null) {
                for (Bancos cajero : estaciones.getListCajeros()) {
                    //Log.d("Banco", cajero.getId().toString());
                    if (cajero.getId().equals(EBancos.ATH.getId())) {
                        Log.d("Banco", "Soy ath");
                        ImageView cajeroAth = root.findViewById(R.id.cajeroBancoAth);
                        cajeroAth.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.CITIBANK.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroCITIBANK = root.findViewById(R.id.cajeroCitiBank);
                        cajeroCITIBANK.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCOOMEVA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCOOMEVA = root.findViewById(R.id.cajeroBancoomeva);
                        cajeroBANCOOMEVA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_AGRARIO_DE_COLOMBIA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_AGRARIO_DE_COLOMBIA = root.findViewById(R.id.cajeroBancoAgrarioColombia);
                        cajeroBANCO_AGRARIO_DE_COLOMBIA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCOLOMBIA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCOLOMBIA = root.findViewById(R.id.cajeroBancolombia);
                        cajeroBANCOLOMBIA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_PROCREDIT.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_PROCREDIT = root.findViewById(R.id.cajeroBancoProcredit);
                        cajeroBANCO_PROCREDIT.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_POPULAR.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroAth = root.findViewById(R.id.cajeroBancoPopular);
                        cajeroAth.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_PICHINCHA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_PICHINCHA = root.findViewById(R.id.cajeroBancoPichincha);
                        cajeroBANCO_PICHINCHA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_OLD_MUTUAL.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_OLD_MUTUAL = root.findViewById(R.id.cajeroBancoOldMutual);
                        cajeroBANCO_OLD_MUTUAL.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_GNB_SUDAMERIS.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroAth = root.findViewById(R.id.cajeroBancoBngSudameris);
                        cajeroAth.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_FALABELLA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_GNB_SUDAMERIS = root.findViewById(R.id.cajeroFalabella);
                        cajeroBANCO_GNB_SUDAMERIS.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_DE_OCCIDENTE.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroAth = root.findViewById(R.id.cajeroBancoOccidente);
                        cajeroAth.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_DE_BOGOTA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_DE_BOGOTA = root.findViewById(R.id.cajeroBancoBogota);
                        cajeroBANCO_DE_BOGOTA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_DAVIVIENDA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_DAVIVIENDA = root.findViewById(R.id.cajeroBancoDavivienda);
                        cajeroBANCO_DAVIVIENDA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_ITAU.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_ITAU = root.findViewById(R.id.cajeroItau);
                        cajeroBANCO_ITAU.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_CORPBANCA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_CORPBANCA = root.findViewById(R.id.cajeroBancoCorpBanca);
                        cajeroBANCO_CORPBANCA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_COOPERATIVO_COOPCENTRAL.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_COOPERATIVO_COOPCENTRAL = root.findViewById(R.id.cajeroBancoCooperativoCoopcentral);
                        cajeroBANCO_COOPERATIVO_COOPCENTRAL.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_COLPATRIA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroAth = root.findViewById(R.id.cajeroBancoColpatria);
                        cajeroAth.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_CAJA_SOCIAL.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_CAJA_SOCIAL = root.findViewById(R.id.cajeroBancoCajaSocial);
                        cajeroBANCO_CAJA_SOCIAL.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_BBVA.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_BBVA = root.findViewById(R.id.cajeroBancoBbva);
                        cajeroBANCO_BBVA.setVisibility(View.VISIBLE);
                    }

                    if (cajero.getId().equals(EBancos.BANCO_AV_VILLAS.getId())) {
                        Log.d("Banco", "Soy citibank");
                        ImageView cajeroBANCO_AV_VILLAS = root.findViewById(R.id.cajeroBancoAvvillas);
                        cajeroBANCO_AV_VILLAS.setVisibility(View.VISIBLE);
                    }
                }
            }
        } else {

            ConstraintLayout cajeros = root.findViewById(R.id.cajeros);
            cajeros.setVisibility(View.GONE);

        }
    }

    private void puntosPago(View root) {

        if (estaciones.getListPuntosPago() != null) {
            // Log.d("Banco", String.valueOf(estaciones.getListCajeros().get(0)));

            if (estaciones.getListPuntosPago() != null) {
                for (PuntoPago puntoPago : estaciones.getListPuntosPago()) {
                    //Log.d("Banco", cajero.getId().toString());
                    if (puntoPago.getId().equals(EPuntoPago.BALOTO.getId())) {
                        Log.d("puntopago", "soybaloto");

                        ImageView puntoBaloto = root.findViewById(R.id.puntoPagoBaloto);
                        puntoBaloto.setVisibility(View.VISIBLE);

                    }

                    if (puntoPago.getId().equals(EPuntoPago.EFECTY.getId())) {
                        Log.d("puntopago", "soyefecty");

                        ImageView puntoEfecty = root.findViewById(R.id.puntoPagoEfecty);
                        puntoEfecty.setVisibility(View.VISIBLE);

                    }

                    if (puntoPago.getId().equals(EPuntoPago.PAGA_TODO.getId())) {
                        Log.d("puntopago", "soypagatodo");

                        ImageView puntoPagatodo = root.findViewById(R.id.puntoPagoPagaTodo);
                        puntoPagatodo.setVisibility(View.VISIBLE);

                    }

                    if (puntoPago.getId().equals(EPuntoPago.SU_RED.getId())) {
                        Log.d("puntopago", "soysured");

                        ImageView puntoSuRed = root.findViewById(R.id.puntoPagoSuRed);
                        puntoSuRed.setVisibility(View.VISIBLE);

                    }
                }
            }
        } else {

            ConstraintLayout puntosPagos = root.findViewById(R.id.puntospago);
            puntosPagos.setVisibility(View.GONE);

        }

    }

    private void tiendasConvivencia(View root) {
        if (estaciones.getListTiendas() != null) {
            // Log.d("Banco", String.valueOf(estaciones.getListCajeros().get(0)));

            if (estaciones.getListTiendas() != null) {
                for (Tiendas tienda : estaciones.getListTiendas()) {
                    //Log.d("Banco", cajero.getId().toString());
                    if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                        Log.d("tienda", "soyTigerMarker");

                        ImageView tiendaTigerMarker = root.findViewById(R.id.puntoPagoBaloto);
                        tiendaTigerMarker.setVisibility(View.VISIBLE);

                    }

                    if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                        Log.d("tienda", "soyTigerMarker");

                        ImageView tiendaTigerMarker = root.findViewById(R.id.tiendaTigerMarket);
                        tiendaTigerMarker.setVisibility(View.VISIBLE);

                    }

                    if (tienda.getId().equals(ETiendas.OLIMPICA.getId())) {
                        Log.d("tienda", "soyOlimpica");

                        ImageView tiendaOlimpica = root.findViewById(R.id.tiendaOlimpica);
                        tiendaOlimpica.setVisibility(View.VISIBLE);

                    }

                    if (tienda.getId().equals(ETiendas.JUSTO_Y_BUENO.getId())) {
                        Log.d("tienda", "soyJustoBueno");

                        ImageView tiendaJustoBueno = root.findViewById(R.id.tiendaJustoBueno);
                        tiendaJustoBueno.setVisibility(View.VISIBLE);

                    }

                    if (tienda.getId().equals(ETiendas.EXITO_EXPRESS.getId())) {
                        Log.d("tienda", "soyExitoExpress");

                        ImageView tiendaExitoExpress = root.findViewById(R.id.tiendaExitoExpress);
                        tiendaExitoExpress.setVisibility(View.VISIBLE);

                    }

                    if (tienda.getId().equals(ETiendas.D_UNO.getId())) {
                        Log.d("tienda", "soyD1");

                        ImageView tiendaD1 = root.findViewById(R.id.tiendaD1);
                        tiendaD1.setVisibility(View.VISIBLE);
                    }

                    if (tienda.getId().equals(ETiendas.ALTOQUE.getId())) {
                        Log.d("tienda", "soyAltoque");

                        ImageView tiendaAltoque = root.findViewById(R.id.tiendaAltoque);
                        tiendaAltoque.setVisibility(View.VISIBLE);

                    }
                }
            }
        } else {
            ConstraintLayout tiendasConvivencia = root.findViewById(R.id.tiendasconvivencia);
            tiendasConvivencia.setVisibility(View.GONE);
        }
    }

    private void soat(View root) {

        if (estaciones.getSoat() != null) {

            if (estaciones.getSoat().getId().equals(ESoat.ALLIANZ.getId())) {
                Log.d("soat", "soyAllianz");

                ImageView soatAllianz = root.findViewById(R.id.soatAllianz);
                soatAllianz.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.AXA_COLPATRIA.getId())) {
                Log.d("soat", "soyAxa");

                ImageView soatAxa = root.findViewById(R.id.soatAxaColpatria);
                soatAxa.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.LIBERTY_SEGUROS.getId())) {
                Log.d("soat", "soyLiberty");

                ImageView soatLiberty = root.findViewById(R.id.soatLibertySeguros);
                soatLiberty.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.MAPRE_SEGUROS.getId())) {
                Log.d("soat", "soyMapre");

                ImageView soatMapfre = root.findViewById(R.id.soatMapreSeguros);
                soatMapfre.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.MUNDIAL_DE_SEGUROS.getId())) {
                Log.d("soat", "soyMundial");

                ImageView soatMundial = root.findViewById(R.id.soatMundialDeSeguros);
                soatMundial.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_BOLIVAR.getId())) {
                Log.d("soat", "soySegurosBolivar");

                ImageView soatBolivar = root.findViewById(R.id.soatSegurosBolivar);
                soatBolivar.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_DEL_ESTADO.getId())) {
                Log.d("soat", "soyEstado");

                ImageView soatEstado = root.findViewById(R.id.soatSegurosEstado);
                soatEstado.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_EXITO.getId())) {
                Log.d("soat", "soySegurosExito");

                ImageView soatExito = root.findViewById(R.id.soatSegurosExito);
                soatExito.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_FALABELLA.getId())) {
                Log.d("soat", "soyFalabella");

                ImageView soatFalabella = root.findViewById(R.id.soatSegurosFalaBella);
                soatFalabella.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SEGUROS_LA_EQUIDAD.getId())) {
                Log.d("soat", "soyAllianz");

                ImageView soatEquidad = root.findViewById(R.id.soatSegurosEquidad);
                soatEquidad.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.SURA_SEGUROS.getId())) {
                Log.d("soat", "soySura");

                ImageView soatSura = root.findViewById(R.id.soatSuraSeguros);
                soatSura.setVisibility(View.VISIBLE);
            }

        } else {
            ConstraintLayout soat = root.findViewById(R.id.soat);
            soat.setVisibility(View.GONE);
        }

    }

    private void restaurantes(View root) {

        if (estaciones.getListRestaurantes() != null) {
            ImageView restaurante = root.findViewById(R.id.estacionRestaurante);
            restaurante.setVisibility(View.VISIBLE);

        } else {

            ConstraintLayout restaurante = root.findViewById(R.id.restautantes);
            restaurante.setVisibility(View.GONE);

        }

    }

    private void hotel(View root) {

        if (estaciones.getListHoteles() != null) {
            ImageView hotel = root.findViewById(R.id.estacionHotel);
            hotel.setVisibility(View.VISIBLE);
        } else {

            ConstraintLayout restaurante = root.findViewById(R.id.hoteles);
            restaurante.setVisibility(View.GONE);

        }

    }


    private void bano(View root) {

        if (estaciones.getTieneBanios() != null) {
            if (estaciones.getTieneBanios()) {
                ImageView hotel = root.findViewById(R.id.estacionbanios);
                hotel.setVisibility(View.VISIBLE);
            } else {

                ConstraintLayout bano = root.findViewById(R.id.banios);
                bano.setVisibility(View.GONE);

            }
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