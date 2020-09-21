package com.inndex.car.personas.fragments.estaciones;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Certificados;
import com.inndex.car.personas.model.MarcaCarros;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstacionesFiltrosFragment extends Fragment {

    @BindView(R.id.rel_filter_marcas)
    public RelativeLayout relFilterMarcas;
    @BindView(R.id.rel_filter_calificacion)
    public RelativeLayout relFilterCalificacion;
    @BindView(R.id.rel_filter_cerificados)
    public RelativeLayout relFilterCertifiacdos;
    @BindView(R.id.rel_filter_distancia)
    public RelativeLayout relFilterDistancia;
    @BindView(R.id.rel_filter_tipo_combustible)
    public RelativeLayout relFilterTipoCombustible;

    @BindView(R.id.tv_filtro_con_que_calificacion)
    public TextView tvFiltroCalificacion;
    @BindView(R.id.tv_filtro_con_que_certificados)
    public TextView tvFiltroCertificados;
    @BindView(R.id.tv_filtro_que_marcas_prefieres)
    public TextView tvFiltroMarcas;
    @BindView(R.id.tv_filtro_que_tan_cerca_de_ti)
    public TextView tvFiltroDistancia;
    @BindView(R.id.tv_filtro_tipo_combustible)
    public TextView tvFiltroTipoCombustible;

    private MainActivity mainActivity;
    private DataBaseHelper helper;
    private List<MarcaCarros> lMarcasCarros;

    private boolean[] checkedBrands;
    private boolean[] checkedCertificados;
    private boolean[] checkedTipoCombustibles;

    private int opcionCalificacionSelected = -1;
    private int opcionDistanciaSelected = -1;

    private List<Certificados> lCertificados;

    public EstacionesFiltrosFragment(MainActivity mainActivity, DataBaseHelper helper) {
        this.mainActivity = mainActivity;
        this.helper = helper;
        checkedBrands = null;
        checkedTipoCombustibles = null;
        checkedCertificados = null;
        Call<List<Certificados>> callGetCertificados = MedidorApiAdapter.getApiService().getCertificados();
        callGetCertificados.enqueue(new Callback<List<Certificados>>() {
            @Override
            public void onResponse(Call<List<Certificados>> call, Response<List<Certificados>> response) {
                if (response.isSuccessful()) {
                    lCertificados = response.body();
                }
            }
            @Override
            public void onFailure(Call<List<Certificados>> call, Throwable t) {
                Toast.makeText(mainActivity, "NO FUE POSIBLE CONECTARSE AL SERVIDOR.", Toast.LENGTH_SHORT).show();
            }
        });
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
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.rel_filter_cerificados)
    public void showCertificateFilters() {
        String[] opciones;
        if(lCertificados != null && lCertificados.size() > 0) {
            opciones = new String[lCertificados.size()];
            for (int i = 0; i < lCertificados.size(); i++) {
                opciones[i] = lCertificados.get(i).getName();
            }
        } else {
            Toast.makeText(mainActivity, "NO EXISTEN CERTIFICADOS", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Certificados");
        builder.setMultiChoiceItems(opciones, checkedBrands, (dialog, which, isChecked) -> {
            if(checkedCertificados == null)
                checkedCertificados = new boolean[opciones.length];
            checkedCertificados[which] = isChecked;
        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (checkedCertificados.length > 0) {
                StringBuilder stBuilder = new StringBuilder("");
                for (int i = 0; i < checkedCertificados.length; i++) {
                    if (checkedCertificados[i]) {
                        stBuilder.append(opciones[i]);
                        if (i < checkedCertificados.length)
                            stBuilder.append(" , ");
                    }
                }
                tvFiltroCertificados.setText(stBuilder.toString().substring(0, stBuilder.length() - 2));
            } else {
                tvFiltroCertificados.setText(R.string.con_que_certificados_de_calidad);
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            checkedCertificados = new boolean[]{};
            tvFiltroCertificados.setText(R.string.con_que_certificados_de_calidad);
            dialog.dismiss();
        });
        builder.create();
        builder.show();
    }

    @OnClick(R.id.rel_filter_marcas)
    public void showBrandFilters() {
        String[] opciones = getResources().getStringArray(R.array.marcas_estaciones);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Marcas");
        builder.setMultiChoiceItems(opciones, checkedBrands, (dialog, which, isChecked) -> {
            if(checkedBrands == null)
                checkedBrands = new boolean[opciones.length];
            checkedBrands[which] = isChecked;
            Log.e("Click", String.valueOf(isChecked));
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

    @OnClick(R.id.tv_filtro_que_tan_cerca_de_ti)
    public void showDistanceFilters() {

        String[] opciones = getResources().getStringArray(R.array.opciones_filtro_distancia);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
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

    @OnClick(R.id.rel_filter_calificacion)
    public void showCalificacionFilters() {

        String[] opciones = getResources().getStringArray(R.array.opciones_filtro_calificacion);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
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

    @OnClick(R.id.tv_filtro_tipo_combustible)
    public void showTipoCombustiblesFilters() {
        String[] opciones = getResources().getStringArray(R.array.tipos_combustibles);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Combustibles");
        builder.setMultiChoiceItems(opciones, checkedTipoCombustibles, (dialog, which, isChecked) -> {
            if(checkedTipoCombustibles == null)
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
}