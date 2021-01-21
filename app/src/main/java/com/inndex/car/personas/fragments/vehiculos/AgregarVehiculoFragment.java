package com.inndex.car.personas.fragments.vehiculos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class AgregarVehiculoFragment extends Fragment {


    Spinner spnMarca, spnLinea , spnModelo, spnCombustible;
    LinearLayout llColor;
    private boolean isFirsTime = true;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_agregar_vehiculo, container, false);

        spnMarca = view.findViewById(R.id.spnMarca);
        spnLinea = view.findViewById(R.id.spnLinea);
        spnModelo = view.findViewById(R.id.spnModelo);
        spnCombustible = view.findViewById(R.id.spnCombustible);
        llColor = view.findViewById(R.id.llColor);

/*        ArrayAdapter<CharSequence> adapterMarca = ArrayAdapter.createFromResource(this.getContext(),R.array.Marca, android.R.layout.simple_spinner_item);
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterLinea = ArrayAdapter.createFromResource(this.getContext(),R.array.Linea, android.R.layout.simple_spinner_item);
        adapterLinea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterModelo = ArrayAdapter.createFromResource(this.getContext(),R.array.Modelo, android.R.layout.simple_spinner_item);
        adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterCombustible = ArrayAdapter.createFromResource(this.getContext(),R.array.Combustible, android.R.layout.simple_spinner_item);
        adapterCombustible.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

/*        spnMarca.setAdapter(adapterMarca);
        spnLinea.setAdapter(adapterLinea);
        spnModelo.setAdapter(adapterModelo);
        spnCombustible.setAdapter(adapterCombustible);*/

        llColor.setOnClickListener(v -> {
            ColorChooserDialog dialog = new ColorChooserDialog(view.getContext());
            dialog.setTitle("Colores");
            dialog.setColorListener(new ColorListener() {
                @Override
                public void OnColorClick(View v, int color) {
                    //do whatever you want to with the values

                    /*esto arroja un numero lo que podriamos ahcer es asociar esos numeros en un map
                    * y segun el numero que arroje que diga que color fue*/
                    Toast.makeText(view.getContext(), "Seleccionaste el color " + color, Toast.LENGTH_SHORT).show();
                }
            });
            //customize the dialog however you want
            dialog.show();
        });

        spnMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getSelectedItem() == parent.getItemAtPosition(0)){
                    isFirsTime = false;
                } else {

                    Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnLinea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)){
                    isFirsTime = false;
                } else {

                    Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)){
                    isFirsTime = false;
                } else {

                    Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCombustible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)){
                    isFirsTime = false;
                } else {

                    Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}