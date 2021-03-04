package com.inndex.car.personas.fragments.promociones;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterSpinnerLinea;
import com.inndex.car.personas.fragments.promociones.presentador.IPresenterPromocionForm;
import com.inndex.car.personas.fragments.promociones.presentador.IPromocionFormFragment;
import com.inndex.car.personas.fragments.promociones.presentador.PresenterPromocionForm;
import com.inndex.car.personas.model.Estaciones;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PromocionFormFragment extends Fragment implements IPromocionFormFragment {

    private PromocionFormViewModel mViewModel;

    ImageButton btnBack;
    Button botonPublicarOferta;
    TextView titulo;
    LinearLayout tv_agregarfoto;
    Spinner tipoOferta, categoriaOferta;
    EditText tituloOferta, precioOferta, DescripcionOferta;

    RelativeLayout agregar_foto_promocion;
    IPresenterPromocionForm iPresenterPromocionForm;

    private Estaciones estacion;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    private static final int REQUEST_PERMISSION_GALERY = 102;
    private static final int REQUEST_IMAGE_GALEY = 103;

    public static PromocionFormFragment newInstance() {
        return new PromocionFormFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_promocion_form, container, false);

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);
        tv_agregarfoto = root.findViewById(R.id.tv_agregarfoto);
        agregar_foto_promocion = root.findViewById(R.id.agregar_foto_promocion);
        tipoOferta = root.findViewById(R.id.spn_tipo_oferta);
        categoriaOferta = root.findViewById(R.id.spn_categoria_oferta);

        tituloOferta = root.findViewById(R.id.titulo_oferta);
        precioOferta = root.findViewById(R.id.precio_oferta);
        DescripcionOferta = root.findViewById(R.id.decripcion_oferta);

        botonPublicarOferta = root.findViewById(R.id.botonPublicarOferta);

        iPresenterPromocionForm = new PresenterPromocionForm(this, requireContext(), estacion);

        titulo.setText("Agregar Promoción");

        btnBack.setOnClickListener(v ->
            Navigation.findNavController(v).navigateUp()
        );

        agregar_foto_promocion.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menufotoperfil);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.camara:

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                irACamara();
                            } else {
                                ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                            }
                        } else {
                            irACamara();
                        }
                        return true;
                    case R.id.galeria:

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                abrirGaleria();
                            } else {
                                ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_GALERY);
                            }
                        } else {
                            abrirGaleria();
                        }
                        return true;
                    default:
                        return false;
                }
            });
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PromocionFormViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //camara
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                irACamara();
            } else {
                //Toast.makeText(view.getContext(), "Se necesitan los permisos", Toast.LENGTH_SHORT).show();
            }
        }
        //galeria
        if (requestCode == REQUEST_PERMISSION_GALERY) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                //  Toast.makeText(view.getContext(), "Se necesitan los permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //camara
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmapCa = (Bitmap) data.getExtras().get("data");
                tv_agregarfoto.setVisibility(View.GONE);
                Drawable d = new BitmapDrawable(getResources(), bitmapCa);
                agregar_foto_promocion.setBackground(d);
            } else {
                //Toast.makeText(view.getContext(), "Se requieren los permisos", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_IMAGE_GALEY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uriGa = data.getData();

                tv_agregarfoto.setVisibility(View.GONE);

                Bitmap bm = null;
                InputStream is = null;
                BufferedInputStream bis = null;
                try {
                    URLConnection conn = new URL(uriGa.getPath()).openConnection();
                    is = conn.getInputStream();
                    bis = new BufferedInputStream(is, 8192);
                    bm = BitmapFactory.decodeStream(bis);

                    Drawable d = new BitmapDrawable(getResources(), bm);
                    agregar_foto_promocion.setBackground(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //img_eds.setImageURI(uriGa);

            } else {
                //Toast.makeText(view.getContext(), "No elegiste ninguna imagen", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //va a la camara
    private void irACamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, REQUEST_IMAGE_CAMERA);
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GALEY);
    }

    @Override
    public Spinner crearSpinerTipoOferta() {
        return tipoOferta;
    }

    @Override
    public Spinner crearSpinerCategoriaOferta() {
        return categoriaOferta;
    }

    @Override
    public EditText crearEditTextTituloOferta() {
        return tituloOferta;
    }

    @Override
    public EditText crearEditTextPresioOferta() {
        return precioOferta;
    }

    @Override
    public EditText crearEditTextDescripcionOferta() {
        return DescripcionOferta;
    }

    @Override
    public Button crearBotonPublicarOferta() {
        return botonPublicarOferta;
    }

}