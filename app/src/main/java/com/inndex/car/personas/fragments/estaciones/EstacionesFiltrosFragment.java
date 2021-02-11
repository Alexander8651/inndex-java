package com.inndex.car.personas.fragments.estaciones;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Certificados;
import com.inndex.car.personas.model.MarcaVehiculos;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.to.EstacionesFiltros;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private boolean[] checkedCertificados;
    private boolean[] checkedTipoCombustibles;

    private int opcionCalificacionSelected = -1;
    private int opcionDistanciaSelected = -1;

    private List<Certificados> lCertificados;

    public EstacionesFiltrosFragment( DataBaseHelper helper) {

        this.helper = helper;
        checkedBrands = null;
        checkedTipoCombustibles = null;
        checkedCertificados = null;
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
        View view = inflater.inflate(R.layout.fragment_estaciones_filtros, container, false);
        TextView tvTitulo = view.findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText("Filtros");
        ImageView imgBack = view.findViewById(R.id.btnBack);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        imgBack.setOnClickListener(v -> {
            navController.navigate(R.id.estacionesMapFragment);
        });
        return view;
    }

    public void showBrandFilters() {
        String[] opciones = getResources().getStringArray(R.array.marcas_estaciones);
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
        builder.setSingleChoiceItems(opciones, opcionCalificacionSelected, (dialog, which) -> {
            opcionCalificacionSelected = which;
        });
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
        if(this.lEstacionesFiltros != null && this.lEstacionesFiltros.size() > 0) {

            Call<Map<String, Long>> callPostQueryCountByFilter = MedidorApiAdapter.getApiService().postQueryCountByFilters(lEstacionesFiltros);
            callPostQueryCountByFilter.enqueue(new Callback<Map<String, Long>>() {
                @Override
                public void onResponse(Call<Map<String, Long>> call, Response<Map<String, Long>> response) {
                    if(response.isSuccessful()) {
                        Map<String, Long> values = response.body();
                        Long responseCount = values != null ? values.get("count") : null;
                        btnFiltrarEstaciones.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Long>> call, Throwable t) {
                    Toast.makeText(getActivity(), "ERROR " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void filtrarEstaciones() {
        //mainActivity.filtrarEstaciones();
    }


}