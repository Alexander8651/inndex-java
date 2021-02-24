package com.inndex.car.personas.fragments.estaciones.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.inndex.car.personas.R;
import com.inndex.car.personas.enums.ECombustibles;
import com.inndex.car.personas.enums.EDias;
import com.inndex.car.personas.model.Combustibles;
import com.inndex.car.personas.model.EstacionCombustibles;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Horario;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.ResponseServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombustibleYHorarioFragment extends Fragment {

    private Button btnGuardarCambiosCYH;
    private CheckBox cbGasoCorri, cbGasoExtra, cbDiesel, cbBiodiesel, cbGnv, cbMaxProDiesel;
    private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo;
    private EditText edtPrecioCorriente, edtPrecioExtra, edtPrecioDiesel, edtPrecioBioDiesel, edtPrecioGNV, edtPrecioMaxProDiesel;
    private EditText edtLunesIni, edtMartesIni, edtMiercolesIni, edtJuevesIni, edtViernesIni, edtSabadoIni, edtDomingoIni;
    private EditText edtLunesFin, edtMartesFin, edtMiercolesFin, edtJuevesFin, edtViernesFin, edtSabadoFin, edtDomingoFin;
    private LinearLayout llGasoCorri, llGasoExtra, llDiesel, llBiodiesel, llGnv, llMaxProDiesel;
    private LinearLayout llLunes, llMartes, llMiercoles, llJueves, llViernes, llSabado, llDomingo;
    private Estaciones estacion, estacionWithOnlyId;
    ImageButton btnBack;
    private List<Horario> listHorarios;
    private List<EstacionCombustibles> listEstacionCombustibles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
            if (estacion != null) {
                estacionWithOnlyId = new Estaciones();
                estacionWithOnlyId.setId(estacion.getId());
                listHorarios = estacion.getListHorarios();
                listEstacionCombustibles = estacion.getListEstacionCombustibles();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_combustible_y_horario, container, false);
        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);
        titulo.setText("Combustibles y horarios");
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
        initViews(view);

        initDataHorarios();
        initDataCombustibles();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGuardarCambiosCYH.setOnClickListener(v -> guardarCambios());

        //Clicks visi o invi tipo gaso
        cbGasoCorri.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llGasoCorri.setVisibility(View.VISIBLE);
            } else {
                llGasoCorri.setVisibility(View.GONE);
            }
        });

        cbGasoExtra.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llGasoExtra.setVisibility(View.VISIBLE);
            } else {
                llGasoExtra.setVisibility(View.GONE);
            }
        });

        cbDiesel.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llDiesel.setVisibility(View.VISIBLE);
            } else {
                llDiesel.setVisibility(View.GONE);
            }
        });

        cbBiodiesel.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llBiodiesel.setVisibility(View.VISIBLE);
            } else {
                llBiodiesel.setVisibility(View.GONE);
            }
        });

        cbGnv.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llGnv.setVisibility(View.VISIBLE);
            } else {
                llGnv.setVisibility(View.GONE);
            }
        });

        cbMaxProDiesel.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llMaxProDiesel.setVisibility(View.VISIBLE);
            } else {
                llMaxProDiesel.setVisibility(View.GONE);
            }
        });

        //Clicks visi o invi horarios
        cbLunes.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llLunes.setVisibility(View.GONE);
            } else {
                llLunes.setVisibility(View.VISIBLE);
            }
        });

        cbMartes.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llMartes.setVisibility(View.GONE);
            } else {
                llMartes.setVisibility(View.VISIBLE);
            }
        });

        cbMiercoles.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llMiercoles.setVisibility(View.GONE);
            } else {
                llMiercoles.setVisibility(View.VISIBLE);
            }
        });

        cbJueves.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llJueves.setVisibility(View.GONE);
            } else {
                llJueves.setVisibility(View.VISIBLE);
            }
        });

        cbViernes.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llViernes.setVisibility(View.GONE);
            } else {
                llViernes.setVisibility(View.VISIBLE);
            }
        });

        cbSabado.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llSabado.setVisibility(View.GONE);
            } else {
                llSabado.setVisibility(View.VISIBLE);
            }
        });

        cbDomingo.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llDomingo.setVisibility(View.GONE);
            } else {
                llDomingo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void guardarCambios() {
        validateCombustibles();
        validateHorarios();
        estacionWithOnlyId.setListHorarios(listHorarios);
        //Log.e("JSONM", new Gson().toJson(estacion));
        callUpdateEstacionCombustible();
    }

    private void callUpdateEstacionCombustible() {

        Call<List<EstacionCombustibles>> postSaveEstacionCombustibles = MedidorApiAdapter.getApiService().postSaveAllEstacionesCombustibles(estacion.getId(), listEstacionCombustibles);
        postSaveEstacionCombustibles.enqueue(new Callback<List<EstacionCombustibles>>() {
            @Override
            public void onResponse(Call<List<EstacionCombustibles>> call, Response<List<EstacionCombustibles>> response) {

                if (response.isSuccessful())
                    callUpdateHorarios();
                else
                    Toast.makeText(getContext(), "NOT SUCCESSFULL COMBUSTIBLES", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<EstacionCombustibles>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR EN COMBUSTIBLES", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void callUpdateHorarios() {
        Gson gson = new Gson();
        Log.e("GSON", gson.toJson(estacionWithOnlyId));
        Call<ResponseServices> updateStationSchedule = MedidorApiAdapter.getApiService().updateStationSchedule(estacionWithOnlyId);
        updateStationSchedule.enqueue(new Callback<ResponseServices>() {
            @Override
            public void onResponse(Call<ResponseServices> call, Response<ResponseServices> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "INFORMACIÓN GUARDADA EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(btnBack).navigateUp();
                } else
                    Toast.makeText(getContext(), "NOT SUCCESSFULL HORARIOS", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServices> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR EN HORARIOS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateCombustibles() {

        String precio;
        if (cbMaxProDiesel.isChecked()) {
            precio = edtPrecioMaxProDiesel.getEditableText().toString();
            addCombustible(ECombustibles.MAX_PRO_DIESEL.getId(), precio);
        } else {
            removeCombustible(ECombustibles.MAX_PRO_DIESEL.getId());
        }

        if (cbBiodiesel.isChecked()) {
            precio = edtPrecioBioDiesel.getEditableText().toString();
            addCombustible(ECombustibles.BIODIESEL.getId(), precio);
        } else {
            removeCombustible(ECombustibles.BIODIESEL.getId());
        }

        if (cbGasoCorri.isChecked()) {
            precio = edtPrecioCorriente.getEditableText().toString();
            addCombustible(ECombustibles.CORRIENTE.getId(), precio);
        } else {
            removeCombustible(ECombustibles.CORRIENTE.getId());
        }

        if (cbGnv.isChecked()) {
            precio = edtPrecioGNV.getEditableText().toString();
            addCombustible(ECombustibles.GNV.getId(), precio);
        } else {
            removeCombustible(ECombustibles.GNV.getId());
        }

        if (cbGasoExtra.isChecked()) {
            precio = edtPrecioExtra.getEditableText().toString();
            addCombustible(ECombustibles.EXTRA.getId(), precio);
        } else {
            removeCombustible(ECombustibles.EXTRA.getId());
        }

        if (cbDiesel.isChecked()) {
            precio = edtPrecioDiesel.getEditableText().toString();
            addCombustible(ECombustibles.DIESEL.getId(), precio);
        } else {
            removeCombustible(ECombustibles.DIESEL.getId());
        }

    }

    private void validateHorarios() {

        String horaFin;
        String horaInicio;
        horaFin = edtLunesFin.getEditableText().toString();
        horaInicio = edtLunesIni.getEditableText().toString();
        updateHorario(EDias.LUNES.getId(), cbLunes.isChecked(), horaInicio, horaFin);

        horaFin = edtMartesFin.getEditableText().toString();
        horaInicio = edtMiercolesIni.getEditableText().toString();
        updateHorario(EDias.MARTES.getId(), cbMartes.isChecked(), horaInicio, horaFin);

        horaFin = edtMiercolesFin.getEditableText().toString();
        horaInicio = edtMiercolesIni.getEditableText().toString();
        updateHorario(EDias.MIERCOLES.getId(), cbMiercoles.isChecked(), horaInicio, horaFin);

        horaFin = edtJuevesFin.getEditableText().toString();
        horaInicio = edtJuevesIni.getEditableText().toString();
        updateHorario(EDias.JUEVES.getId(), cbJueves.isChecked(), horaInicio, horaFin);

        horaFin = edtViernesFin.getEditableText().toString();
        horaInicio = edtViernesIni.getEditableText().toString();
        updateHorario(EDias.VIERNES.getId(), cbViernes.isChecked(), horaInicio, horaFin);

        horaFin = edtSabadoFin.getEditableText().toString();
        horaInicio = edtSabadoIni.getEditableText().toString();
        updateHorario(EDias.SABADO.getId(), cbSabado.isChecked(), horaInicio, horaFin);

        horaFin = edtDomingoFin.getEditableText().toString();
        horaInicio = edtDomingoIni.getEditableText().toString();
        updateHorario(EDias.DOMINGO.getId(), cbDomingo.isChecked(), horaInicio, horaFin);

    }

    private void removeCombustible(Long idCombustible) {
        for (EstacionCombustibles combustible :
                listEstacionCombustibles) {
            if (combustible.getCombustible() != null && idCombustible.equals(combustible.getCombustible().getId())) {
                listEstacionCombustibles.remove(combustible);
                break;
            }
        }
    }

    private void addCombustible(Long idCombustible, String precio) {
        boolean noExists = true;
        for (EstacionCombustibles combustible :
                listEstacionCombustibles) {
            if (combustible.getCombustible() != null && idCombustible.equals(combustible.getCombustible().getId())) {
                combustible.setPrecio(Integer.parseInt(precio));
                combustible.setEstaciones(estacionWithOnlyId);
                noExists = false;
                break;
            }
        }

        if (noExists) {
            Combustibles combustibles = new Combustibles();
            combustibles.setId(idCombustible);
            EstacionCombustibles estacionCombustibles = new EstacionCombustibles();
            estacionCombustibles.setCombustible(combustibles);
            if (!precio.equals(""))
                estacionCombustibles.setPrecio(Integer.parseInt(precio));
            estacionCombustibles.setEstaciones(estacionWithOnlyId);
            listEstacionCombustibles.add(estacionCombustibles);
        }
    }

    private void updateHorario(Long idDia, boolean abiertoSiempre, String horaInicio, String horaFin) {

        boolean exists = true;
        if (listHorarios.size() == 0) {
            exists = false;
        }
        for (Horario horario :
                listHorarios) {
            if (horario.getDia().equals(idDia)) {
                horario.setAbiertoSiempre(abiertoSiempre);
                horario.setFin(horaFin);
                horario.setInicio(horaInicio);
                exists = true;
                break;
            } else {
                exists = false;
            }
        }
        if (!exists) {
            Horario hor = new Horario();
            hor.setDia(idDia);
            hor.setAbiertoSiempre(abiertoSiempre);
            hor.setFin(horaFin);
            hor.setInicio(horaInicio);
            Estaciones est = new Estaciones();
            est.setId(estacion.getId());
            hor.setEstaciones(est);
            //hor.setEstaciones(estacionWithOnlyId);
            listHorarios.add(hor);
        }

    }

    private void initDataCombustibles() {
        listEstacionCombustibles = estacion.getListEstacionCombustibles();
        if (listEstacionCombustibles != null && listEstacionCombustibles.size() > 0) {
            for (EstacionCombustibles combustible :
                    listEstacionCombustibles) {
                switch (Objects.requireNonNull(ECombustibles.getECombustiblesById(combustible.getCombustible().getId()))) {
                    case CORRIENTE:
                        cbGasoCorri.setChecked(true);
                        llGasoCorri.setVisibility(View.VISIBLE);
                        edtPrecioCorriente.setText(String.valueOf(combustible.getPrecio()));
                        break;
                    case EXTRA:
                        cbGasoExtra.setChecked(true);
                        llGasoExtra.setVisibility(View.VISIBLE);
                        edtPrecioExtra.setText(String.valueOf(combustible.getPrecio()));
                        break;
                    case DIESEL:
                        cbDiesel.setChecked(true);
                        llDiesel.setVisibility(View.VISIBLE);
                        edtPrecioDiesel.setText(String.valueOf(combustible.getPrecio()));
                        break;
                    case BIODIESEL:
                        cbBiodiesel.setChecked(true);
                        llBiodiesel.setVisibility(View.VISIBLE);
                        edtPrecioBioDiesel.setText(String.valueOf(combustible.getPrecio()));
                        break;
                    case GNV:
                        cbGnv.setChecked(true);
                        llGnv.setVisibility(View.VISIBLE);
                        edtPrecioGNV.setText(String.valueOf(combustible.getPrecio()));
                        break;
                    case MAX_PRO_DIESEL:
                        llMaxProDiesel.setVisibility(View.VISIBLE);
                        edtPrecioMaxProDiesel.setText(String.valueOf(combustible.getPrecio()));
                        cbMaxProDiesel.setChecked(true);
                        break;
                }
            }
        } else {
            listEstacionCombustibles = new ArrayList<>();
        }
    }

    private void initDataHorarios() {
        if (listHorarios != null && listHorarios.size() > 0) {
            listHorarios = estacion.getListHorarios();
            for (Horario horario :
                    listHorarios) {
                switch (Objects.requireNonNull(EDias.getEDiasById(horario.getDia()))) {
                    case LUNES:
                        if (horario.getAbiertoSiempre())
                            cbLunes.setChecked(true);
                        else {
                            llLunes.setVisibility(View.VISIBLE);
                            edtLunesIni.setText(horario.getInicio());
                            edtLunesFin.setText(horario.getFin());
                        }
                        break;
                    case DOMINGO:
                        if (horario.getAbiertoSiempre())
                            cbDomingo.setChecked(true);
                        else {
                            llDomingo.setVisibility(View.VISIBLE);
                            edtDomingoIni.setText(horario.getInicio());
                            edtDomingoFin.setText(horario.getFin());
                        }
                        break;
                    case JUEVES:
                        if (horario.getAbiertoSiempre())
                            cbJueves.setChecked(true);
                        else {
                            llJueves.setVisibility(View.VISIBLE);
                            edtJuevesIni.setText(horario.getInicio());
                            edtJuevesFin.setText(horario.getFin());
                        }
                        break;
                    case MARTES:
                        if (horario.getAbiertoSiempre())
                            cbMartes.setChecked(true);
                        else {
                            llMartes.setVisibility(View.VISIBLE);
                            edtMartesIni.setText(horario.getInicio());
                            edtMartesFin.setText(horario.getFin());
                        }
                        break;
                    case MIERCOLES:
                        if (horario.getAbiertoSiempre())
                            cbMiercoles.setChecked(true);
                        else {
                            llMiercoles.setVisibility(View.VISIBLE);
                            edtMiercolesIni.setText(horario.getInicio());
                            edtMiercolesFin.setText(horario.getFin());
                        }
                        break;
                    case SABADO:
                        if (horario.getAbiertoSiempre())
                            cbSabado.setChecked(true);
                        else {
                            llSabado.setVisibility(View.VISIBLE);
                            edtSabadoIni.setText(horario.getInicio());
                            edtSabadoFin.setText(horario.getFin());
                        }
                        break;
                    case VIERNES:
                        if (horario.getAbiertoSiempre())
                            cbViernes.setChecked(true);
                        else {
                            llViernes.setVisibility(View.VISIBLE);
                            edtViernesIni.setText(horario.getInicio());
                            edtViernesFin.setText(horario.getFin());
                        }
                        break;
                }
            }
        } else {
            llLunes.setVisibility(View.VISIBLE);
            llMartes.setVisibility(View.VISIBLE);
            llMiercoles.setVisibility(View.VISIBLE);
            llJueves.setVisibility(View.VISIBLE);
            llViernes.setVisibility(View.VISIBLE);
            llSabado.setVisibility(View.VISIBLE);
            llDomingo.setVisibility(View.VISIBLE);
        }
    }

    private void initViews(View view) {
        btnGuardarCambiosCYH = view.findViewById(R.id.btn_guardar_cambios_cyh);

        //Checkbox tipo gasolina
        cbGasoCorri = view.findViewById(R.id.cb_gaso_corriente);
        cbGasoExtra = view.findViewById(R.id.cb_gaso_extra);
        cbDiesel = view.findViewById(R.id.cb_diesel);
        cbBiodiesel = view.findViewById(R.id.cb_biodiesel);
        cbGnv = view.findViewById(R.id.cb_gnv);
        cbMaxProDiesel = view.findViewById(R.id.cb_max_pro_diesel);

        //Checkbox horarios
        cbLunes = view.findViewById(R.id.cb_lunes);
        cbMartes = view.findViewById(R.id.cb_martes);
        cbMiercoles = view.findViewById(R.id.cb_miercoles);
        cbJueves = view.findViewById(R.id.cb_jueves);
        cbViernes = view.findViewById(R.id.cb_viernes);
        cbSabado = view.findViewById(R.id.cb_sabado);
        cbDomingo = view.findViewById(R.id.cb_domingo);

        //Linear Layout para hacer visible o invisible
        llGasoCorri = view.findViewById(R.id.ll_gaso_corriente);
        llGasoExtra = view.findViewById(R.id.ll_gaso_extra);
        llDiesel = view.findViewById(R.id.ll_diesel);
        llBiodiesel = view.findViewById(R.id.ll_biodiesel);
        llGnv = view.findViewById(R.id.ll_gnv);
        llMaxProDiesel = view.findViewById(R.id.ll_max_pro_diesel);
        //Linear Layout para hacer visible o invisible
        llLunes = view.findViewById(R.id.ll_lunes);
        llMartes = view.findViewById(R.id.ll_martes);
        llMiercoles = view.findViewById(R.id.ll_miercoles);
        llJueves = view.findViewById(R.id.ll_jueves);
        llViernes = view.findViewById(R.id.ll_viernes);
        llSabado = view.findViewById(R.id.ll_sabado);
        llDomingo = view.findViewById(R.id.ll_domingo);

        edtPrecioCorriente = view.findViewById(R.id.edtPrecioCorriente);
        edtPrecioExtra = view.findViewById(R.id.edtPrecioExtra);
        edtPrecioDiesel = view.findViewById(R.id.edtPrecioDiesel);
        edtPrecioBioDiesel = view.findViewById(R.id.edtPrecioBioDiesel);
        edtPrecioGNV = view.findViewById(R.id.edtPrecioGNV);
        edtPrecioMaxProDiesel = view.findViewById(R.id.edtPrecioMaxProDiesel);


        edtLunesIni = view.findViewById(R.id.edtLunesIni);
        edtMartesIni = view.findViewById(R.id.edtMartesIni);
        edtMiercolesIni = view.findViewById(R.id.edtMiercolesIni);
        edtJuevesIni = view.findViewById(R.id.edtJuevesIni);
        edtViernesIni = view.findViewById(R.id.edtViernesIni);
        edtSabadoIni = view.findViewById(R.id.edtSabadoIni);
        edtDomingoIni = view.findViewById(R.id.edtDomigoIni);


        edtLunesFin = view.findViewById(R.id.edtLunesFin);
        edtMartesFin = view.findViewById(R.id.edtMartesFin);
        edtMiercolesFin = view.findViewById(R.id.edtMiercolesFin);
        edtJuevesFin = view.findViewById(R.id.edtJuevesFin);
        edtViernesFin = view.findViewById(R.id.edtViernesFin);
        edtSabadoFin = view.findViewById(R.id.edtSabadoFin);
        edtDomingoFin = view.findViewById(R.id.edtDomingoFin);
    }
}