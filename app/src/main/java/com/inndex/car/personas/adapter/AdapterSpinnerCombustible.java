

package com.inndex.car.personas.adapter;


        import android.content.Context;
        import android.graphics.Color;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import androidx.core.app.ActivityCompat;

        import com.inndex.car.personas.R;
        import com.inndex.car.personas.model.Combustibles;
        import com.inndex.car.personas.model.LineasVehiculos;
        import com.inndex.car.personas.model.MarcaVehiculos;

        import java.util.Calendar;
        import java.util.List;

public class AdapterSpinnerCombustible extends ArrayAdapter<Combustibles> {
    private Context context;

    List<Combustibles> datos = null;

    public AdapterSpinnerCombustible(Context context, List<Combustibles> datos) {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.itemspinnermarca, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.itemspinnermarca, null);
        }

        TextView item = ((TextView) convertView.findViewById(R.id.itemSpinnerMarca));


        item.setTextColor(Color.BLACK);
        item.setText("Selecciona una marca");


        // cuando se seleciona un item en el spinner se cambia el valor selecionado
        if (position >= 0) {
            Combustibles conclusion = datos.get(position);
            item.setText(conclusion.getNombre());
            item.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.itemspinnermarca, parent, false);
            Combustibles marcaVehiculos = datos.get(position);

            ((TextView) row.findViewById(R.id.itemSpinnerMarca)).setText(marcaVehiculos.getNombre());


            if (row.getTag() == null) {
                //AdapterSpinner UnidadesHolder = new AdapterSpinner();
                // UnidadesHolder.setTextView((TextView) row.findViewById(R.id.itemSpinners));
                // row.setTag(UnidadesHolder);
            }

            //rellenamos el layout con los datos de la fila que se está procesando
            Combustibles categorias = datos.get(position);

        }
        return row;

    }
}

