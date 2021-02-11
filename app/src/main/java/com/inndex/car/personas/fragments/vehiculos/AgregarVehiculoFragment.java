package com.inndex.car.personas.fragments.vehiculos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterSpinner;
import com.inndex.car.personas.adapter.AdapterSpinnerCombustible;
import com.inndex.car.personas.adapter.AdapterSpinnerLinea;
import com.inndex.car.personas.fragments.vehiculos.presenters.IAgregaVehiculosFragment;
import com.inndex.car.personas.fragments.vehiculos.presenters.IPresenterAgregarVehiculos;
import com.inndex.car.personas.fragments.vehiculos.presenters.PresenterAgregarVehiculos;
import com.inndex.car.personas.model.Combustibles;
import com.inndex.car.personas.model.LineasVehiculos;
import com.inndex.car.personas.model.MarcaVehiculos;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.util.ArrayList;

public class AgregarVehiculoFragment extends Fragment implements IAgregaVehiculosFragment {


    Spinner spnMarca, spnLinea, spnModelo, spnCombustible;
    LinearLayout llColor;
    Button agregarVehiculo;
    private boolean isFirsTime = true;
    View view;

    IPresenterAgregarVehiculos iPresenterAgregarVehiculos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_agregar_vehiculo, container, false);

        spnMarca = view.findViewById(R.id.spnMarca);
        spnLinea = view.findViewById(R.id.spnLinea);
        spnModelo = view.findViewById(R.id.spnModelo);
        spnCombustible = view.findViewById(R.id.spnCombustible);
        llColor = view.findViewById(R.id.llColor);
        iPresenterAgregarVehiculos = new PresenterAgregarVehiculos(this, requireContext());

        agregarVehiculo = view.findViewById(R.id.agregarVehiculo);

        agregarVehiculo.setOnClickListener(v -> {
            Boolean crearVehiculo = iPresenterAgregarVehiculos.agregarVehiculo();

            if (!crearVehiculo) {
                Toast.makeText(requireContext(), "Debes Ingresar todos los datos del vehiculo", Toast.LENGTH_SHORT).show();
            }
        });

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
                    //Log.d("coloressss", String.valueOf(Long.valueOf("ffF44336",16).intValue()));

                    String hex = Integer.toHexString(color);

                    iPresenterAgregarVehiculos.obtenerColorVehiculo(color, hex);
                }
            });
            //customize the dialog however you want
            dialog.show();
        });

        spnMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getSelectedItem() == parent.getItemAtPosition(0)) {
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

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)) {
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

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)) {
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

                if (parent.getSelectedItem() == parent.getItemAtPosition(0)) {
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

    @Override
    public Spinner crearSpinerMarca() {
        return spnMarca;
    }

    @Override
    public AdapterSpinner crearAdapterMarca(ArrayList<MarcaVehiculos> marcaVehiculos) {
        AdapterSpinner spinnerAdapter = new AdapterSpinner(requireContext(), marcaVehiculos);
        spinnerAdapter.setDropDownViewResource(R.layout.itemspinnermarca);
        return spinnerAdapter;
    }

    @Override
    public Spinner crearSpinerLinea() {
        return spnLinea;
    }

    @Override
    public AdapterSpinnerLinea crearAdapterLinea(ArrayList<LineasVehiculos> lineasVehiculos) {
        AdapterSpinnerLinea spinnerAdapter = new AdapterSpinnerLinea(requireContext(), lineasVehiculos);
        spinnerAdapter.setDropDownViewResource(R.layout.itemspinnermarca);
        return spinnerAdapter;
    }

    @Override
    public Spinner crearSpinerModelo() {
        return spnModelo;
    }

    @Override
    public Spinner crearSpinerCombuatible() {
        return spnCombustible;
    }

    @Override
    public AdapterSpinnerCombustible crearAdapterCombustibles(ArrayList<Combustibles> combustibles) {
        AdapterSpinnerCombustible spinnerAdapter = new AdapterSpinnerCombustible(requireContext(), combustibles);
        spinnerAdapter.setDropDownViewResource(R.layout.itemspinnermarca);
        return spinnerAdapter;
    }
}