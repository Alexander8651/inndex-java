package com.inndex.car.personas.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.inndex.car.personas.R;
import com.inndex.car.personas.enums.EDias;
import com.inndex.car.personas.model.Horario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ExpLAdapter extends BaseExpandableListAdapter {

    private Map<String, ArrayList<Horario>> datos;
    private Context context;
    private ArrayList<String> listCategoria;

    public ExpLAdapter(Map<String, ArrayList<Horario>> datos, ArrayList<String> listCategoria, Context context) {
        this.datos = datos;
        this.context = context;
        this.listCategoria = listCategoria;
    }

    @Override
    public int getGroupCount() {
        return listCategoria.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datos.get(listCategoria.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCategoria.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datos.get(listCategoria.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String tituloCategoria = (String) getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.itemhorario, null);
        TextView tvGroupTitle = convertView.findViewById(R.id.horarioitem);
        tvGroupTitle.setText(tituloCategoria);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.itemspinners, null);

        Calendar rightNow = Calendar.getInstance();

        Horario horario = (Horario) getChild(groupPosition, childPosition);

        Long dia = horario.getDia();

        if (dia.equals(EDias.DOMINGO.getId())) {
            if (horario.getAbiertoSiempre() != null) {
                Log.d("meejcuo", "domingo");
                if (horario.getAbiertoSiempre()) {
                    String horarioAbierto = "Abierto 24 horas";
                    String diasemana = "Domingo";
                    String horarioAtencion = "Abierto 24 horas";

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                } else {
                    String diasemana = "Domingo";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                }
            }


        }

        if (dia.equals(EDias.LUNES.getId())) {
            //Log.d("horarios", "lunes");
            if (horario.getAbiertoSiempre() != null) {
                if (horario.getAbiertoSiempre()) {
                    Log.d("meejcuo", "lunes");
                    String diasemana = "Lunes";
                    String horarioAtencion = "Abierto 24 horas";


                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                } else {
                    String diasemana = "Lunes";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);
                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                }
            }
        }

        if (dia.equals(EDias.MARTES.getId())) {
            //Log.d("horarios", "marter");

            if (horario.getAbiertoSiempre() != null) {
                if (horario.getAbiertoSiempre()) {
                    Log.d("meejcuo", "martes");
                    String diasemana = "Martes";
                    String horarioAtencion = "Abierto 24 horas";

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                } else {
                    String diasemana = "Martes";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                }
            }
        }

        if (dia.equals(EDias.MIERCOLES.getId())) {
            Log.d("horarios", "miercoles");

            if (horario.getAbiertoSiempre() != null) {

                if (horario.getAbiertoSiempre()) {
                    String diasemana = "Miercoles";
                    String horarioAtencion = "Abierto 24 horas";

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                } else {

                    String diasemana = "Miercoles";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                }
            }

        }

        if (dia.equals(EDias.JUEVES.getId())) {
            Log.d("horarios", "jueves");

            if (horario.getAbiertoSiempre() != null) {

                if (horario.getAbiertoSiempre()) {
                    String diasemana = "Jueves";
                    String horarioAtencion = "Abierto 24 horas";

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                } else {
                    String diasemana = "Jueves";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                }
            }
        }

        if (dia.equals(EDias.VIERNES.getId())) {
            Log.d("horarios", "viernes");

            if (horario.getAbiertoSiempre() != null) {

                if (horario.getAbiertoSiempre()) {
                    Log.d("horarios", "viernesabierto24");

                    String diasemana = "Viernes";
                    String horarioAtencion = "Abierto 24 horas";

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                } else {
                    Log.d("horarios", "viernesiniciofin");


                    String diasemana = "Viernes";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                }
            }
        }

        if (dia.equals(EDias.SABADO.getId())) {
            if (horario.getAbiertoSiempre() != null) {
                if (horario.getAbiertoSiempre()) {
                    String diasemana = "Sabado";
                    String horarioAtencion = "Abierto 24 horas";
                    Log.d("horarios", "sabadoabierto24");


                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }

                } else {
                    Log.d("horarios", "sabadoabiertoiniciofin");

                    String diasemana = "Sabado";
                    String horarioAtencion = "Inicio: " + horario.getInicio().toString() + "Fin: " + horario.getFin().toString();

                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setText(diasemana);
                    ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setText(horarioAtencion);

                    if (rightNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDia)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                        ((TextView) convertView.findViewById(R.id.itemSpinnersDomingoDiaHorario)).setTextColor(ActivityCompat.getColor(context, R.color.colorPrimary));
                    }
                }
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
