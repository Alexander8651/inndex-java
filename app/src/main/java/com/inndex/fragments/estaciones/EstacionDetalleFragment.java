package com.inndex.fragments.estaciones;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.inndex.R;
import com.inndex.adapter.ExpLAdapter;
import com.inndex.adapter.PromocionesDetalleAdapter;
import com.inndex.enums.EAccesoriosRepuestos;
import com.inndex.enums.EBancos;
import com.inndex.enums.ECombustibles;
import com.inndex.enums.ECompraYventa;
import com.inndex.enums.EEvents;
import com.inndex.enums.EMensajeria;
import com.inndex.enums.EMetodosPago;
import com.inndex.enums.EPuntoPago;
import com.inndex.enums.ESoat;
import com.inndex.enums.ETiendas;
import com.inndex.fragments.estaciones.presenterDetalles.IEstacionDetalleFragment;
import com.inndex.fragments.estaciones.presenterDetalles.IPresenterDetalles;
import com.inndex.fragments.estaciones.presenterDetalles.PresenterDetalles;
import com.inndex.model.AccesoriosYrepuestos;
import com.inndex.model.Bancos;
import com.inndex.model.CompraYventa;
import com.inndex.model.EstacionCombustibles;
import com.inndex.model.Estaciones;
import com.inndex.model.Horario;
import com.inndex.model.Mensajeria;
import com.inndex.model.MetodoPago;
import com.inndex.model.PuntoPago;
import com.inndex.model.Tiendas;
import com.inndex.shared.SharedViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class EstacionDetalleFragment extends Fragment implements IEstacionDetalleFragment {

    private Estaciones estaciones;
    private float distancia = 0;
    private SharedViewModel model;

    private LatLng myPosition;

    private ImageView menuDetalle;
    TextView tv_estacion_servicios_calificar;

    IPresenterDetalles iPresenterDetalles;
    RelativeLayout status_api;
    private SharedViewModel sharedViewModel;

    public EstacionDetalleFragment(Estaciones estacion, Float distance,
                                   LatLng position, SharedViewModel model) {
        this.estaciones = estacion;
        this.myPosition = position;
        this.distancia = distance;
        this.sharedViewModel = model;
    }

    public EstacionDetalleFragment() {
        // Required empty public constructor
    }

    public static EstacionDetalleFragment newInstance(String param1, String param2) {
        return new EstacionDetalleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            estaciones = getArguments().getParcelable(Constantes.ESTACION_SELECCOINADA_KEY);
            this.distancia = getArguments().getFloat("distancia");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estacion_detalle, container, false);

        final TextView direccion = root.findViewById(R.id.direccionBomba);
        final TextView marca = root.findViewById(R.id.tvEstacionDetalleMarca);
        final TextView nombre = root.findViewById(R.id.tvEstaciondetalleNombre);
        final TextView tvDistancia = root.findViewById(R.id.tv_estacion_servicios_distancia);
        final ImageView botonBack = root.findViewById(R.id.botonbackdetallebomba);
        final ImageView imgDrawRoute = root.findViewById(R.id.estaciones_servicios_route);
        tv_estacion_servicios_calificar = root.findViewById(R.id.tv_estacion_servicios_calificar);

        iPresenterDetalles = new PresenterDetalles(this, requireContext(), this.sharedViewModel, estaciones);

        menuDetalle = root.findViewById(R.id.menuDetalleEstacion);

        status_api = root.findViewById(R.id.status_api);

        menuDetalle.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menudetalleestacion);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.compartir) {

                } else if (item.getItemId() == R.id.reportar_problema) {
                    iPresenterDetalles.mostrarDialogReportes(estaciones.getId());
                } else if (item.getItemId() == R.id.editar) {
                    iPresenterDetalles.editarEDS(estaciones);
                }
                return true;
            });
        });

        imgDrawRoute.setOnClickListener(v -> {
            model.setHomeEvents(EEvents.DRAW_ROUTE.getId());
        });
        RatingBar ratingBar = root.findViewById(R.id.rat_bar_estacion_servicios_calificacion);
/*        botonBack.setOnClickListener(v -> {
            this.onDestroy();
            //mainActivity.clickHome();
        });*/

        final TextView tvCalificacion = root.findViewById(R.id.tv_estacion_servicios_calificacion);
/*        menuBomba.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), menuBomba);
            popupMenu.getMenuInflater().inflate(R.menu.menu_estacion_servicios, popupMenu.getMenu());
            popupMenu.show();
        });*/

        tvCalificacion.setText(String.format(Locale.ENGLISH, "%.1f", estaciones.getCalificacion()));
        ratingBar.setRating((float) estaciones.getCalificacion());


        direccion.setText(estaciones.getDireccion());
        marca.setText(estaciones.getMarca());
        nombre.setText(estaciones.getNombre());
        combustibles(root);
        horariosycontacto(root);
        cajeros(root);
        initCorresponsales(root);
        puntosPago(root);
        tiendasConveniencia(root);
        soat(root);
        restaurantes(root);
        hotel(root);
        bano(root);
        farmacia(root);
        lubricantes(root);
        llanteria(root);
        lavaderos(root);
        metodosPago(root);
        mensajeria(root);
        initServiteca(root);
        initCafeteria(root);
        initCambioAceite(root);
        initVentaLlantas(root);
        initVentaBaterias(root);
        initFerreteria(root);
        initLicoreria(root);
        initBebidas(root);
        initCda(root);
        initMecanica(root);
        initAccesorios(root);
        initCompraYventa(root);
        initPromociones(root);
        if (distancia < 1000) {
            tvDistancia.setText(String.format(Locale.ENGLISH, "%.2f m", distancia));
        } else {
            distancia = distancia / 1000;
            tvDistancia.setText(String.format(Locale.ENGLISH, "%.2f km", distancia));
        }

        tv_estacion_servicios_calificar.setOnClickListener(v ->iPresenterDetalles.dialogCalificar());

        return root;
    }

    private void initPromociones(View root) {

        if (estaciones.getListPromociones() != null && estaciones.getListPromociones().size() > 0) {

            RecyclerView recyclerView = root.findViewById(R.id.rvPromocionesDetalle);
            PromocionesDetalleAdapter adapter = new PromocionesDetalleAdapter(estaciones.getListPromociones(), getContext());
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            LinearLayout layout = root.findViewById(R.id.lay_promociones);
            layout.setVisibility(View.GONE);
        }

    }

    private void mensajeria(View root) {
        if (estaciones.getListMensajeria() != null && estaciones.getListMensajeria().size() > 0) {

            int counter = 0;
            ImageView imgMensajeria = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));

            for (Mensajeria mensajeria : estaciones.getListMensajeria()
            ) {
                switch (EMensajeria.getEMensajeriaById(mensajeria.getId())) {
                    case AVIANCA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaAvianca);
                        break;
                    case CADENA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCadena);
                        break;
                    case CALI_EXPRESS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCaliExpress);
                        break;
                    case CANTAURUS_MENSAJEROS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCentaurus);
                        break;
                    case CARVAJAL:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCarvajal);
                        break;
                    case COORDINADORA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCoordinadora);
                        break;
                    case COPETRAN:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCopetran);
                        break;
                    case CUATRO_SETENTA_Y_DOS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaCuatroSetentayDos);
                        break;
                    case DEPRISA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaDeprisa);
                        break;
                    case DHL:
                        imgMensajeria = root.findViewById(R.id.mensajeriaDhl);
                        break;
                    case DOMESA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaDomesa);
                        break;
                    case DOMINA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaDomina);
                        break;
                    case EIS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaEis);
                        break;
                    case ENVIA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaEnvia);
                        break;
                    case FEDEX:
                        imgMensajeria = root.findViewById(R.id.mensajeriaFedex);
                        break;
                    case INTERRAPIDISIMO:
                        imgMensajeria = root.findViewById(R.id.mensajeriaInterRapidisimo);
                        break;
                    case MC:
                        imgMensajeria = root.findViewById(R.id.mensajeriaMC);
                        break;
                    case SERVI_ENTREGA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaServientrega);
                        break;
                    case THOMAS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaThomasExpress);
                        break;
                    case TIEMPO_EXPRESS:
                        imgMensajeria = root.findViewById(R.id.mensajeriaTempoExpress);
                        break;
                    case OTRA:
                        imgMensajeria = root.findViewById(R.id.mensajeriaOtra);
                        break;
                }

                if (imgMensajeria != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgMensajeria.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgMensajeria.setLayoutParams(params);
                    }
                    imgMensajeria.setVisibility(View.VISIBLE);
                    counter++;
                }
            }
        } else {
            ConstraintLayout layout = root.findViewById(R.id.clMensajeria);
            layout.setVisibility(View.GONE);
        }
    }

    private void initServiteca(View root) {
        if (estaciones.getTieneServiteca() != null && estaciones.getTieneServiteca()) {
            ImageView serviteca = root.findViewById(R.id.estacionServiteca);
            serviteca.setVisibility(View.VISIBLE);
        } else {
            LinearLayout lyServiteca = root.findViewById(R.id.layServiteca);
            lyServiteca.setVisibility(View.GONE);
        }
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

                if (bomba.getCombustible().getId().equals(ECombustibles.GNV.getId())) {
                    final LinearLayout gnv = root.findViewById(R.id.GNV);
                    gnv.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreGNV);
                    precioCombustible = root.findViewById(R.id.precioGNV);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.MAX_PRO_DIESEL.getId())) {
                    final LinearLayout maxProDiesel = root.findViewById(R.id.maxProDiesel);
                    maxProDiesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreMaxProDiesel);
                    precioCombustible = root.findViewById(R.id.precioMaxProDiesel);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.BIODIESEL.getId())) {
                    final LinearLayout bioDiesel = root.findViewById(R.id.lay_bio_diesel);
                    bioDiesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombre_bio_diesel);
                    precioCombustible = root.findViewById(R.id.precio_bio_diesel);
                }

                if (nombreCombustible != null && precioCombustible != null) {
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    DecimalFormat formatter = new DecimalFormat("###,###");
                    String sPrecio = "-";
                    if (!(bomba.getPrecio() == 0)) {
                        sPrecio = formatter.format(Double.valueOf(bomba.getPrecio()));
                    }
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
            try {
                ExpLAdapter adapter = new ExpLAdapter(mapChild, listCategorias, requireContext());
                spin.setAdapter(adapter);
            } catch (Exception ex) {
                Toast.makeText(getActivity(), "EX " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            spin.setIndicatorBounds(610, 0);
            spin.setOnGroupClickListener((parent, v, groupPosition, id) -> {
                //View layout = root.findViewById(R.id.horariosycontacto);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layHorarios.getLayoutParams();
                if (parent.isGroupExpanded(groupPosition)) {
                    params.height = 100;
                    LinearLayout linearLayout = root.findViewById(R.id.llamarestacion);
                    linearLayout.setVisibility(View.GONE);

                } else {

                    if (estaciones.getTelefono() != null) {
                        params.height = 1100;
                        LinearLayout linearLayout = root.findViewById(R.id.llamarestacion);
                        linearLayout.setVisibility(View.VISIBLE);
                        final TextView numero = root.findViewById(R.id.numero_estacion);
                        numero.setText(estaciones.getTelefono());

                        final CardView botonLlamar = root.findViewById(R.id.botonllamar);

                        botonLlamar.setOnClickListener(view -> {
                            Intent i = new Intent(Intent.ACTION_DIAL);
                            i.setData(Uri.parse("tel:" + estaciones.getTelefono()));
                            requireContext().startActivity(i);
                        });

                    } else {
                        params.height = 900;

                        LinearLayout linearLayout = root.findViewById(R.id.llamarestacion);
                        linearLayout.setVisibility(View.GONE);
                    }
                }
                return false;
            });

        } else {
            layHorarios.setVisibility(View.GONE);
        }
    }

    private void metodosPago(View root) {

        if (estaciones.getListMetodosPago() != null && estaciones.getListMetodosPago().size() > 0) {

            int counter = 0;
            ImageView imgMetodoPago = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));

            for (MetodoPago metodoPago : estaciones.getListMetodosPago()
            ) {

                switch (Objects.requireNonNull(EMetodosPago.getMetodosPagoById(metodoPago.getId()))) {
                    case PSE:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoPse);
                        break;
                    case AMERICAN_EXPRESS:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoAmericanExpress);
                        break;
                    case DINERS_CLUB:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoDinersClub);
                        break;
                    case DISCOVER:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoDiscover);
                        break;
                    case EFECTIVO:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoEfectivo);
                        break;
                    case GOPASS:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoGopass);
                        break;
                    case MASTERCARD:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoMastercard);
                        break;
                    case MERCADO_PAGO:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoMercadoPago);
                        break;
                    case PAGO_CLICK:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoPagoClick);
                        break;
                    case PUNTOS_COLOMBIA:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoPuntosColombia);
                        break;
                    case TPAGA:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoTPaga);
                        break;
                    case VISA:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoVisa);
                        break;
                    case MAESTRO:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoMaestro);
                        break;
                    case CREDITO_FACIL_CODENSA:
                        imgMetodoPago = root.findViewById(R.id.metodoPagoCreditoFacil);
                        break;
                }

                if (imgMetodoPago != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgMetodoPago.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgMetodoPago.setLayoutParams(params);
                    }
                    imgMetodoPago.setVisibility(View.VISIBLE);
                    counter++;
                }
            }
        } else {
            LinearLayout layout = root.findViewById(R.id.lay_metodos_pago);
            layout.setVisibility(View.GONE);
        }

    }

    private void initAccesorios(View root) {

        if (estaciones.getListAccesoriosYrepuestos() != null && estaciones.getListAccesoriosYrepuestos().size() > 0) {

            int counter = 0;
            ImageView imgAccesorios = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));

            for (AccesoriosYrepuestos accesoriosYrepuestos : estaciones.getListAccesoriosYrepuestos()
            ) {

                switch (Objects.requireNonNull(EAccesoriosRepuestos.getEAccesoriosById(accesoriosYrepuestos.getId()))) {
                    case CARROS:
                        imgAccesorios = root.findViewById(R.id.accesoriosCarros);
                        break;
                    case MOTOS:
                        imgAccesorios = root.findViewById(R.id.accesoriosMotos);
                        break;
                }

                if (imgAccesorios != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgAccesorios.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgAccesorios.setLayoutParams(params);
                    }
                    imgAccesorios.setVisibility(View.VISIBLE);
                    counter++;
                }
            }
        } else {
            LinearLayout layout = root.findViewById(R.id.lay_accesorios);
            layout.setVisibility(View.GONE);
        }

    }

    private void initCompraYventa(View root) {

        if (estaciones.getListCompraYventa() != null && estaciones.getListCompraYventa().size() > 0) {

            int counter = 0;
            ImageView imgCompraYventa = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));

            for (CompraYventa compraYventa : estaciones.getListCompraYventa()
            ) {

                switch (Objects.requireNonNull(ECompraYventa.getEECompraYventaById(compraYventa.getId()))) {
                    case CARROYA_PUNTO_COM:
                        imgCompraYventa = root.findViewById(R.id.compraVentaCarroYa);
                        break;
                    case OLX_AUTOS:
                        imgCompraYventa = root.findViewById(R.id.compraVentaOlxAutos);
                        break;
                    case OTRA:
                        imgCompraYventa = root.findViewById(R.id.compraVentaOtra);
                        break;
                    case TUCARRO_PUNTO_COM:
                        imgCompraYventa = root.findViewById(R.id.compraVentaTuCarro);
                        break;
                }

                if (imgCompraYventa != null) {
                    if (counter == 0) {
                        params.setMargins(0, 0, 20, 0);
                        imgCompraYventa.setLayoutParams(params);
                    } else if (counter > 0) {
                        params.setMargins(20, 0, 20, 0);
                        imgCompraYventa.setLayoutParams(params);
                    }
                    imgCompraYventa.setVisibility(View.VISIBLE);
                    counter++;
                }
            }
        } else {
            LinearLayout layout = root.findViewById(R.id.lay_compra_venta);
            layout.setVisibility(View.GONE);
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
            ConstraintLayout cajeros = root.findViewById(R.id.constrait_corresponsales);
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

                if (puntoPago.getId().equals(EPuntoPago.OTRA.getId())) {
                    imgPuntoPago = root.findViewById(R.id.puntoOtro);
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

    private void tiendasConveniencia(View root) {
        if (estaciones.getListTiendas() != null && estaciones.getListTiendas().size() > 0) {
            int counter = 0;
            ImageView imgTienda = null;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_img_secciones),
                    (int) getResources().getDimension(R.dimen.size_img_secciones));
            for (Tiendas tienda : estaciones.getListTiendas()) {
                //
                if (tienda.getId().equals(ETiendas.TIGER_MARKET.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaTigerMarket);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.ARA.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaAra);
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

                if (tienda.getId().equals(ETiendas.BEST_MART.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaBestMart);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.LA_DESPENSA.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaDespensaExpress);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.DOLLARCITY.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaDollarCity);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.MAKRO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaMakro);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.METRO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaMetro);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.ON_THE_RUN.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaOnTheRun);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.SURTIMAX.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaSurtimax);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.STAR_MART.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaStarMart);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.SPACIO_UNO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaSpaciouno);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.OXXO.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaOxxo);
                    imgTienda.setVisibility(View.VISIBLE);
                }

                if (tienda.getId().equals(ETiendas.OTRA.getId())) {
                    imgTienda = root.findViewById(R.id.tiendaOtraTienda);
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

            if (estaciones.getSoat().getId().equals(ESoat.DEO_SEGUROS.getId())) {
                ImageView soatSura = root.findViewById(R.id.soatDeo);
                soatSura.setVisibility(View.VISIBLE);
            }

            if (estaciones.getSoat().getId().equals(ESoat.OTRA.getId())) {
                ImageView soatSura = root.findViewById(R.id.soatOtra);
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

    private void initCafeteria(View root) {

        if (estaciones.getTieneCafeteriaPanaderia() != null && estaciones.getTieneCafeteriaPanaderia()) {
            ImageView imageView = root.findViewById(R.id.estacionCafeteria);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_cafeteria);
            ll.setVisibility(View.GONE);
        }
    }

    private void initCambioAceite(View root) {

        if (estaciones.getTieneCambioAceite() != null && estaciones.getTieneCambioAceite()) {
            ImageView imageView = root.findViewById(R.id.estacionCambioAceite);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_cambio_aceite);
            ll.setVisibility(View.GONE);
        }
    }

    private void initVentaLlantas(View root) {

        if (estaciones.getTieneVentaLLantas() != null && estaciones.getTieneVentaLLantas()) {
            ImageView imageView = root.findViewById(R.id.estacionVentaLlantas);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_venta_llantas);
            ll.setVisibility(View.GONE);
        }
    }

    private void initVentaBaterias(View root) {

        if (estaciones.getTieneVentaBaterias() != null && estaciones.getTieneVentaBaterias()) {
            ImageView imageView = root.findViewById(R.id.estacionVentaBaterias);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_venta_baterias);
            ll.setVisibility(View.GONE);
        }
    }

    private void initFerreteria(View root) {

        if (estaciones.getTieneFerreteria() != null && estaciones.getTieneFerreteria()) {
            ImageView imageView = root.findViewById(R.id.estacionFerreteria);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_ferreteria);
            ll.setVisibility(View.GONE);
        }
    }

    private void initLicoreria(View root) {

        if (estaciones.getTieneLicoreria() != null && estaciones.getTieneLicoreria()) {
            ImageView imageView = root.findViewById(R.id.estacionLicoreria);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_licoreria);
            ll.setVisibility(View.GONE);
        }
    }

    private void initBebidas(View root) {

        if (estaciones.getTieneBebidas() != null && estaciones.getTieneBebidas()) {
            ImageView imageView = root.findViewById(R.id.estacionBebidas);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_bebidas);
            ll.setVisibility(View.GONE);
        }
    }

    private void initCda(View root) {

        if (estaciones.getTieneCda() != null && estaciones.getTieneCda()) {
            ImageView imageView = root.findViewById(R.id.estacionCda);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_cda);
            ll.setVisibility(View.GONE);
        }
    }

    private void initMecanica(View root) {

        if (estaciones.getTieneMecanicaGeneral() != null && estaciones.getTieneMecanicaGeneral()) {
            ImageView imageView = root.findViewById(R.id.estacionMecanicaGeneral);
            imageView.setVisibility(View.VISIBLE);
        } else {
            LinearLayout ll = root.findViewById(R.id.lay_macanica_general);
            ll.setVisibility(View.GONE);
        }
    }

    private void farmacia(View root) {

        if (estaciones.getTieneDroguerias() != null) {
            if (estaciones.getTieneDroguerias()) {
                ImageView lubricantes = root.findViewById(R.id.estacionFarmacia);
                lubricantes.setVisibility(View.VISIBLE);
                return;
            }
        }
        ConstraintLayout lubricantes = root.findViewById(R.id.constFarmacias);
        lubricantes.setVisibility(View.GONE);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }


}