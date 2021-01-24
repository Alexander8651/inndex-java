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
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.idlestar.ratingstar.RatingStarView;
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
            Toast.makeText(mainActivity, "Distancia " + distancia, Toast.LENGTH_SHORT).show();
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
        RatingStarView rsv_rating = root.findViewById(R.id.rat_bar_estacion_servicios_calificacion);
        //rsv_rating.setRating(1.5f);
        menuBomba.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), menuBomba);
            popupMenu.getMenuInflater().inflate(R.menu.menu_estacion_servicios, popupMenu.getMenu());
            popupMenu.show();
        });

        tvVerOpiniones.setTypeface(light);
        tvCalificar.setTypeface(light);
        tvCalificacion.setTypeface(light);

        tvPreciosActualizados.setTypeface(light);
        titulocajeros.setTypeface(robotoRegular);
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
                    precioCombustible.setText(getString(R.string.precio_combustible_placeholder, sPrecio.replace(",",".")));
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
            for (Bancos cajero : estaciones.getListCajeros()) {
                if (cajero.getId().equals(EBancos.ATH.getId())) {
                    ImageView cajeroAth = root.findViewById(R.id.cajeroBancoAth);
                    cajeroAth.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.CITIBANK.getId())) {
                    ImageView cajeroCITIBANK = root.findViewById(R.id.cajeroCitiBank);
                    cajeroCITIBANK.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCOOMEVA.getId())) {
                    ImageView cajeroBANCOOMEVA = root.findViewById(R.id.cajeroBancoomeva);
                    cajeroBANCOOMEVA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_AGRARIO_DE_COLOMBIA.getId())) {
                    ImageView cajeroBANCO_AGRARIO_DE_COLOMBIA = root.findViewById(R.id.cajeroBancoAgrarioColombia);
                    cajeroBANCO_AGRARIO_DE_COLOMBIA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCOLOMBIA.getId())) {
                    ImageView cajeroBANCOLOMBIA = root.findViewById(R.id.cajeroBancolombia);
                    cajeroBANCOLOMBIA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_PROCREDIT.getId())) {
                    ImageView cajeroBANCO_PROCREDIT = root.findViewById(R.id.cajeroBancoProcredit);
                    cajeroBANCO_PROCREDIT.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_POPULAR.getId())) {
                    ImageView cajeroAth = root.findViewById(R.id.cajeroBancoPopular);
                    cajeroAth.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_PICHINCHA.getId())) {
                    ImageView cajeroBANCO_PICHINCHA = root.findViewById(R.id.cajeroBancoPichincha);
                    cajeroBANCO_PICHINCHA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_OLD_MUTUAL.getId())) {
                    ImageView cajeroBANCO_OLD_MUTUAL = root.findViewById(R.id.cajeroBancoOldMutual);
                    cajeroBANCO_OLD_MUTUAL.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_GNB_SUDAMERIS.getId())) {
                    ImageView cajeroAth = root.findViewById(R.id.cajeroBancoBngSudameris);
                    cajeroAth.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_FALABELLA.getId())) {
                    ImageView cajeroBANCO_GNB_SUDAMERIS = root.findViewById(R.id.cajeroFalabella);
                    cajeroBANCO_GNB_SUDAMERIS.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_DE_OCCIDENTE.getId())) {
                    ImageView cajeroAth = root.findViewById(R.id.cajeroBancoOccidente);
                    cajeroAth.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_DE_BOGOTA.getId())) {
                    ImageView cajeroBANCO_DE_BOGOTA = root.findViewById(R.id.cajeroBancoBogota);
                    cajeroBANCO_DE_BOGOTA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_DAVIVIENDA.getId())) {
                    ;
                    ImageView cajeroBANCO_DAVIVIENDA = root.findViewById(R.id.cajeroBancoDavivienda);
                    cajeroBANCO_DAVIVIENDA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_ITAU.getId())) {
                    ;
                    ImageView cajeroBANCO_ITAU = root.findViewById(R.id.cajeroItau);
                    cajeroBANCO_ITAU.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_CORPBANCA.getId())) {
                    ;
                    ImageView cajeroBANCO_CORPBANCA = root.findViewById(R.id.cajeroBancoCorpBanca);
                    cajeroBANCO_CORPBANCA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_COOPERATIVO_COOPCENTRAL.getId())) {
                    ;
                    ImageView cajeroBANCO_COOPERATIVO_COOPCENTRAL = root.findViewById(R.id.cajeroBancoCooperativoCoopcentral);
                    cajeroBANCO_COOPERATIVO_COOPCENTRAL.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_COLPATRIA.getId())) {
                    ;
                    ImageView cajeroAth = root.findViewById(R.id.cajeroBancoColpatria);
                    cajeroAth.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_CAJA_SOCIAL.getId())) {
                    ;
                    ImageView cajeroBANCO_CAJA_SOCIAL = root.findViewById(R.id.cajeroBancoCajaSocial);
                    cajeroBANCO_CAJA_SOCIAL.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_BBVA.getId())) {
                    ;
                    ImageView cajeroBANCO_BBVA = root.findViewById(R.id.cajeroBancoBbva);
                    cajeroBANCO_BBVA.setVisibility(View.VISIBLE);
                }

                if (cajero.getId().equals(EBancos.BANCO_AV_VILLAS.getId())) {
                    ;
                    ImageView cajeroBANCO_AV_VILLAS = root.findViewById(R.id.cajeroBancoAvvillas);
                    cajeroBANCO_AV_VILLAS.setVisibility(View.VISIBLE);
                }
            }

        } else {
            ConstraintLayout cajeros = root.findViewById(R.id.cajeros);
            cajeros.setVisibility(View.GONE);
        }
    }

    private void puntosPago(View root) {
        if (estaciones.getListPuntosPago() != null && estaciones.getListPuntosPago().size() > 0) {
            for (PuntoPago puntoPago : estaciones.getListPuntosPago()) {
                //
                if (puntoPago.getId().equals(EPuntoPago.BALOTO.getId())) {
                    ImageView puntoBaloto = root.findViewById(R.id.puntoPagoBaloto);
                    puntoBaloto.setVisibility(View.VISIBLE);
                }

                if (puntoPago.getId().equals(EPuntoPago.EFECTY.getId())) {
                    ImageView puntoEfecty = root.findViewById(R.id.puntoPagoEfecty);
                    puntoEfecty.setVisibility(View.VISIBLE);
                }

                if (puntoPago.getId().equals(EPuntoPago.SU_RED.getId())) {
                    ImageView puntoSuRed = root.findViewById(R.id.puntoPagoSuRed);
                    puntoSuRed.setVisibility(View.VISIBLE);
                }
            }
        } else {
            ConstraintLayout puntosPagos = root.findViewById(R.id.puntospago);
            puntosPagos.setVisibility(View.GONE);
        }
    }

    private void tiendasConvivencia(View root) {
        if (estaciones.getListTiendas() != null && estaciones.getListTiendas().size() > 0) {
            for (Tiendas tienda : estaciones.getListTiendas()) {
                //
                if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                    ImageView tiendaTigerMarker = root.findViewById(R.id.puntoPagoBaloto);
                    tiendaTigerMarker.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                    ImageView tiendaTigerMarker = root.findViewById(R.id.tiendaTigerMarket);
                    tiendaTigerMarker.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.OLIMPICA.getId())) {
                    ImageView tiendaOlimpica = root.findViewById(R.id.tiendaOlimpica);
                    tiendaOlimpica.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.JUSTO_Y_BUENO.getId())) {
                    ImageView tiendaJustoBueno = root.findViewById(R.id.tiendaJustoBueno);
                    tiendaJustoBueno.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.EXITO_EXPRESS.getId())) {
                    ImageView tiendaExitoExpress = root.findViewById(R.id.tiendaExitoExpress);
                    tiendaExitoExpress.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.D_UNO.getId())) {
                    ImageView tiendaD1 = root.findViewById(R.id.tiendaD1);
                    tiendaD1.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.ALTOQUE.getId())) {
                    ImageView tiendaAltoque = root.findViewById(R.id.tiendaAltoque);
                    tiendaAltoque.setVisibility(View.VISIBLE);
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