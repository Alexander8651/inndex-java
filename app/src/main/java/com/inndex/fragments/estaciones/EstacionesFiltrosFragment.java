package com.inndex.fragments.estaciones;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.inndex.R;
import com.inndex.database.DataBaseHelper;
import com.inndex.databinding.FragmentEstacionesFiltrosBinding;
import com.inndex.dto.filtros.EstacionFiltrosListDto;
import com.inndex.enums.EEstacionesFiltros;
import com.inndex.model.Certificados;
import com.inndex.model.MarcaVehiculos;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.to.EstacionesFiltros;
import com.inndex.utils.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EstacionesFiltrosFragment extends Fragment {

    List<EstacionesFiltros> lEstacionesFiltros;

    public RelativeLayout relFilterMarcas;

    public RelativeLayout relFilterCalificacion;
    //
    //public RelativeLayout relFilterCertifiacdos;

    public RelativeLayout relFilterDistancia;

    public RelativeLayout relFilterTipoCombustible;

    public TextView tvFiltroCalificacion;
    //
    //public TextView tvFiltroCertificados;

    public TextView tvFiltroMarcas;

    public TextView tvFiltroDistancia;

    public TextView tvFiltroTipoCombustible;

    public TextView tvFiltroCajeros;

    public TextView tvFiltroCorresponsales;
    //
    //public TextView tvFiltroPrecioHasta;

    public TextView tvFiltroPuntosPago;

    public TextView tvFiltroTiendas;

    public TextView tvFiltroAbiertoAhora;

    public TextView tvFiltroRestaurantes;

    public TextView tvFiltroBanios;

    public TextView tvFiltroHoteles;

    public TextView tvFiltroLavaderos;

    public TextView tvFiltroVentaLubricantes;

    public TextView tvFiltroVentaSoat;

    public TextView tvFiltroLlanterias;

    public Button btnFiltrarEstaciones;

    //private MainActivity mainActivity;
    private DataBaseHelper helper;
    private List<MarcaVehiculos> lMarcasVehiculos;
    private NavController navController;

    private boolean[] checkedBrands;
    private boolean[] checkedTipoCombustibles;

    private int opcionCalificacionSelected = -1;
    private int opcionDistanciaSelected = -1;

    private double latitud;
    private double longitud;
    private Gson gson;
    private EstacionFiltrosListDto estacionFiltrosListDto;
    SharedPreferences sharedPreferences;
    FragmentEstacionesFiltrosBinding binding;

    public EstacionesFiltrosFragment(DataBaseHelper helper) {

        this.helper = helper;
        checkedBrands = null;
        checkedTipoCombustibles = null;
    }

    public EstacionesFiltrosFragment() {
        // Required empty public constructor
    }

    public static EstacionesFiltrosFragment newInstance(String param1, String param2) {
        EstacionesFiltrosFragment fragment = new EstacionesFiltrosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_estaciones_filtros, container, false);
        binding = FragmentEstacionesFiltrosBinding.inflate(inflater, container, false);

        TextView tvTitulo = binding.getRoot().findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText("Filtros");
        ImageView imgBack = binding.getRoot().findViewById(R.id.btnBack);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        imgBack.setOnClickListener(v -> {
            navController.navigate(R.id.estacionesMapFragment);
        });

        sharedPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);

        latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
        longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));

        String filtrosDto = sharedPreferences.getString(Constantes.FILTROS_KEY, null);
        gson = new Gson();
        if (filtrosDto == null) {
            estacionFiltrosListDto = new EstacionFiltrosListDto();
            this.lEstacionesFiltros = new ArrayList<>();
        } else {
            estacionFiltrosListDto = gson.fromJson(filtrosDto, EstacionFiltrosListDto.class);
            if (estacionFiltrosListDto != null)
                this.lEstacionesFiltros = estacionFiltrosListDto.getListEstacionesFiltros();
        }

        initFilterData();
        initFilterViewEvents();
        initDistanceFilter();
        getFilterCountResult();
        binding.btnFiltrarEstaciones.setOnClickListener(v ->
                filtrarEstaciones()
        );

        ImageView imgMenu = binding.getRoot().findViewById(R.id.btnMenuToolbar);
        imgMenu.setVisibility(View.VISIBLE);
        PopupMenu popupMenu = new PopupMenu(requireContext(), imgMenu);
        popupMenu.inflate(R.menu.menu_filtros);
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.optEliminarFiltros) {
                deleteFilters();
            }
            return false;
        });
        imgMenu.setOnClickListener(v -> popupMenu.show());

        return binding.getRoot();
    }

    private void initFilterViewEvents() {
        binding.swFilterBanios.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.BANIOS.getId());
        });
        binding.swFilterHoteles.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.HOTELES.getId());
        });
        binding.swFilterLavaderos.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.LAVADEROS.getId());
        });
        binding.swFilterLlanterias.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.LLANTERIA.getId());
        });
        binding.swFilterVentaLubricantes.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.LUBRICANTES.getId());
        });
        binding.swFilterSoat.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.SOAT.getId());
        });
        binding.swFilterRestaurantes.setOnCheckedChangeListener((compoundButton, b) -> {
            setBooleanFilter(b, EEstacionesFiltros.RESTAURANTES.getId());
        });
        binding.swFilterPromocion.setOnCheckedChangeListener((compoundButton, b) ->
                setBooleanFilter(b, EEstacionesFiltros.PROMOCION.getId())
        );
    }

    private void initFilterData() {

        if (lEstacionesFiltros != null && lEstacionesFiltros.size() > 0) {

            for (int i = 0; i < lEstacionesFiltros.size(); i++) {
                Log.e("TAG", String.valueOf(lEstacionesFiltros.get(i).getId()));
                if (lEstacionesFiltros.get(i) != null && lEstacionesFiltros.get(i).getId() != null)
                    switch (EEstacionesFiltros.getEEstacionesFiltrosById(lEstacionesFiltros.get(i).getId())) {
                        case ABIERTO_AHORA:
                            break;
                        case BANIOS:
                            binding.swFilterBanios.setChecked(true);
                            break;
                        case CAJEROS:
                            break;
                        case CALIFICACION:
                            break;
                        case CORRESPONSALES:
                            break;
                        case DISTANCIA:
                            break;
                        case HOTELES:
                            binding.swFilterHoteles.setChecked(true);
                            break;
                        case LAVADEROS:
                            binding.swFilterLavaderos.setChecked(true);
                            break;
                        case LLANTERIA:
                            binding.swFilterLlanterias.setChecked(true);
                            break;
                        case LUBRICANTES:
                            binding.swFilterVentaLubricantes.setChecked(true);
                            break;
                        case MARCAS:
                            break;
                        case PUNTOS_PAGO:
                            break;
                        case RESTAURANTES:
                            binding.swFilterRestaurantes.setChecked(true);
                            break;
                        case SOAT:
                            binding.swFilterSoat.setChecked(true);
                            break;
                        case TIENDAS:
                            break;
                        case TIPO_COMBUSTIBLE:
                            break;
                        case PROMOCION:
                            binding.swFilterPromocion.setChecked(true);
                            break;
                        default:
                            break;
                    }
            }

            initDistanceFilter();
        }
    }

    private void setBooleanFilter(boolean b, Long filterId) {
        removeExistingFilter(filterId);
        if (b) {
            EstacionesFiltros filtro = new EstacionesFiltros();
            filtro.setId(filterId);
            lEstacionesFiltros.add(filtro);
        }
        getFilterCountResult();
    }

    public void showBrandFilters() {
        //String[] opciones = getResources().getStringArray(R.array.marcas_estaciones);
        String[] opciones = new String[]{"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BlackDialogTheme);
        builder.setTitle("Marcas");
        builder.setMultiChoiceItems(opciones, checkedBrands, (dialog, which, isChecked) -> {
            if (checkedBrands == null)
                checkedBrands = new boolean[opciones.length];
            checkedBrands[which] = isChecked;
        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (checkedBrands.length > 0) {
                StringBuilder stBuilder = new StringBuilder("");
                for (int i = 0; i < checkedBrands.length; i++) {
                    if (checkedBrands[i]) {
                        stBuilder.append(opciones[i]);
                        if (i < checkedBrands.length)
                            stBuilder.append(" , ");
                    }
                }
                tvFiltroMarcas.setText(stBuilder.toString().substring(0, stBuilder.length() - 2));
            } else {
                tvFiltroMarcas.setText(R.string.que_marcas_prefieres);
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            checkedBrands = new boolean[]{};
            tvFiltroMarcas.setText(R.string.que_marcas_prefieres);
            dialog.dismiss();
        });
        builder.create();
        builder.show();
    }

    public void showDistanceFilters() {

        String[] opciones = getResources().getStringArray(R.array.opciones_filtro_distancia);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BlackDialogTheme);
        builder.setTitle("Distancia");
        builder.setSingleChoiceItems(opciones, opcionDistanciaSelected, (dialog, which) -> {
            opcionDistanciaSelected = which;
        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            tvFiltroDistancia.setText(opciones[opcionDistanciaSelected]);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            opcionDistanciaSelected = -1;
            tvFiltroDistancia.setText(R.string.que_tan_cerca_de_ti);
            dialog.dismiss();
        });
        builder.create();
        builder.show();
    }

    public void showCalificacionFilters() {

        String[] opciones = getResources().getStringArray(R.array.opciones_filtro_calificacion);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BlackDialogTheme);
        builder.setTitle("Calificación");
        builder.setSingleChoiceItems(opciones, opcionCalificacionSelected, (dialog, which) ->
                opcionCalificacionSelected = which
        );
        builder.setPositiveButton("OK", (dialog, which) -> {
            tvFiltroCalificacion.setText(opciones[opcionCalificacionSelected]);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            opcionCalificacionSelected = -1;
            tvFiltroCalificacion.setText(R.string.con_que_calificacion);
            dialog.dismiss();
        });
        builder.create();
        builder.show();
    }

    public void showTipoCombustiblesFilters() {
        String[] opciones = getResources().getStringArray(R.array.tipos_combustibles);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BlackDialogTheme);
        builder.setTitle("Combustibles");
        builder.setMultiChoiceItems(opciones, checkedTipoCombustibles, (dialog, which, isChecked) -> {
            if (checkedTipoCombustibles == null)
                checkedTipoCombustibles = new boolean[opciones.length];

            checkedTipoCombustibles[which] = isChecked;
        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (checkedTipoCombustibles.length > 0) {
                StringBuilder stBuilder = new StringBuilder("");
                for (int i = 0; i < checkedTipoCombustibles.length; i++) {
                    if (checkedTipoCombustibles[i]) {
                        stBuilder.append(opciones[i]);
                        if (i < checkedTipoCombustibles.length)
                            stBuilder.append(" , ");
                    }
                }
                tvFiltroTipoCombustible.setText(stBuilder.toString().substring(0, stBuilder.length() - 2));
            } else {
                tvFiltroTipoCombustible.setText(R.string.con_que_tipo_de_combustible);
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            checkedTipoCombustibles = new boolean[]{};
            tvFiltroTipoCombustible.setText(R.string.con_que_tipo_de_combustible);
            dialog.dismiss();
        });
        builder.create();
        builder.show();
    }

    private void getFilterCountResult() {
        if (this.lEstacionesFiltros != null && this.lEstacionesFiltros.size() > 0) {
            binding.statusApi.setVisibility(View.VISIBLE);
            binding.btnFiltrarEstaciones.setClickable(false);

            Call<Long> callPostQueryCountByFilter = MedidorApiAdapter.getApiService().postQueryCountByFilters(lEstacionesFiltros, latitud, longitud);
            callPostQueryCountByFilter.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    binding.statusApi.setVisibility(View.GONE);
                    binding.btnFiltrarEstaciones.setClickable(true);

                    if (response.isSuccessful()) {

                        Long responseCount = response.body();
                        binding.btnFiltrarEstaciones.setText("Ver " + responseCount + " EDS");
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    binding.statusApi.setVisibility(View.GONE);
                    binding.btnFiltrarEstaciones.setClickable(true);
                    Toast.makeText(getActivity(), "ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireContext(), "Filtros vacios", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeExistingFilter(Long filterId) {
        if (lEstacionesFiltros.size() > 0) {
            for (EstacionesFiltros filtro :
                    lEstacionesFiltros) {
                if (filtro.getId().equals(filterId)) {
                    lEstacionesFiltros.remove(filtro);
                    break;
                }
            }
        }
    }

    public void filtrarEstaciones() {

        if (lEstacionesFiltros != null && lEstacionesFiltros.size() > 0) {
            estacionFiltrosListDto = new EstacionFiltrosListDto(lEstacionesFiltros);
            String filtros = gson.toJson(estacionFiltrosListDto);
            sharedPreferences.edit().putString(Constantes.FILTROS_KEY, filtros).apply();
        }
        navController.navigate(R.id.estacionesMapFragment);
    }

    private void deleteFilters() {
        if (lEstacionesFiltros != null) {
            lEstacionesFiltros.clear();
            sharedPreferences.edit().putString(Constantes.FILTROS_KEY, null).apply();
        }
        binding.swFilterBanios.setChecked(false);
        binding.swFilterHoteles.setChecked(false);
        binding.swFilterLavaderos.setChecked(false);
        binding.swFilterLlanterias.setChecked(false);
        binding.swFilterVentaLubricantes.setChecked(false);
        binding.swFilterRestaurantes.setChecked(false);
        binding.swFilterSoat.setChecked(false);
        binding.swFilterPromocion.setChecked(false);
        initDistanceFilter();
    }

    private void initDistanceFilter() {

        boolean filterAlreadyExists = false;
        if (lEstacionesFiltros != null && lEstacionesFiltros.size() > 0) {

            for (EstacionesFiltros filtro :
                    lEstacionesFiltros) {

                if (filtro != null && filtro.getId() != null && filtro.getId().equals(EEstacionesFiltros.DISTANCIA.getId())) {
                    filterAlreadyExists = true;
                    break;
                }
            }
        }

        if (!filterAlreadyExists) {
            EstacionesFiltros filtroDistancia = new EstacionesFiltros();
            filtroDistancia.setId(EEstacionesFiltros.DISTANCIA.getId());
            lEstacionesFiltros.add(filtroDistancia);
        }
    }
}