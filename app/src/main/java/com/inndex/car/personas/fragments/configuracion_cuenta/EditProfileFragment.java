package com.inndex.car.personas.fragments.configuracion_cuenta;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.hbb20.CountryCodePicker;
import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.configuracion_cuenta.presenter.IpresenterEditAccount;
import com.inndex.car.personas.fragments.configuracion_cuenta.presenter.PesenterEditAccount;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class EditProfileFragment extends Fragment implements IeditAccout {

    private Button btnMasculino, btnFemenino, btnGuardar;
    private EditText etText, etPassworDesa;
    private LinearLayout navPassword;
    private CountryCodePicker ccp;
    private View view;
    private ImageButton btnBack;
    private EditText tvName;
    private EditText tvApellidos;
    private EditText tvNumeroIdentidad;
    private EditText tvCorreo;
    private TextView tvFecNacimiento;
    private IpresenterEditAccount ipresenterEditAccount;
    private SharedPreferences myPreferences;
    private RelativeLayout imagenCarga;

    private  static  final  int REQUEST_PERMISSION_CAMERA = 100;
    private  static  final  int REQUEST_IMAGE_CAMERA = 101;
    private  static  final  int REQUEST_PERMISSION_GALERY = 102;
    private  static  final  int REQUEST_IMAGE_GALEY = 103;
    private ImageView img_eds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ccp = view.findViewById(R.id.ccp);// Las banderas y sus indicativos
        etText = view.findViewById(R.id.etCel);

        tvFecNacimiento = view.findViewById(R.id.tvFecNacimientoCapt);
        navPassword = view.findViewById(R.id.navPassword);

        etPassworDesa = view.findViewById(R.id.etPasswordDesa);
        etPassworDesa.setEnabled(false);

        btnMasculino = view.findViewById(R.id.btnMasculino);
        btnFemenino = view.findViewById(R.id.btnFemenino);
        tvName = view.findViewById(R.id.user_name_edit);
        tvApellidos = view.findViewById(R.id.etApellidos);
        tvNumeroIdentidad = view.findViewById(R.id.etNumDeIdentidad);
        tvCorreo = view.findViewById(R.id.etEmail);
        btnGuardar = view.findViewById(R.id.guardar_usuario);
        imagenCarga = view.findViewById(R.id.status_image);

        img_eds = view.findViewById(R.id.imagen_perfil_editar);


        myPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        int userID =  myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        ipresenterEditAccount = new PesenterEditAccount(this, userID);


        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->{
            Navigation.findNavController(v).navigateUp();
        });

        btnGuardar.setOnClickListener(v->{
            ipresenterEditAccount.updateUserInfoAccount();
        });

        img_eds.setOnClickListener( v ->{
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.menufotoperfil);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.camara:

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                        irACamara();
                                    } else {
                                        ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                                    }
                                }else{
                                    irACamara();
                                }

                                return true;
                            case R.id.galeria:

                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                        abrirGaleria();
                                    }else {
                                        ActivityCompat.requestPermissions((Activity) v.getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_GALERY);
                                    }
                                } else {
                                    abrirGaleria();
                                }

                                return true;
                            default:
                                return  false;
                        }
                    }
                });
        });





        //Cambiamos a masculino en negro
        btnMasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnMasculino.setBackgroundColor(Color.BLACK);
                btnMasculino.setTextColor(Color.WHITE);
                btnFemenino.setBackgroundColor(Color.WHITE);
                btnFemenino.setTextColor(Color.BLACK);

            }
        });

        //Cambiamos a femenino en negro
        btnFemenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnFemenino.setBackgroundColor(Color.BLACK);
                btnFemenino.setTextColor(Color.WHITE);
                btnMasculino.setBackgroundColor(Color.WHITE);
                btnMasculino.setTextColor(Color.BLACK);

            }
        });


/*

        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNumber();

            }
        });
*/

        //Captura el click de fecha de nacimiento
        tvFecNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendario();
            }
        });

        //navPassword.setOnClickListener(v ->
        //        Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_editPasswordFragment));
        return view;
    }


    //Abrimos el calendario y obtenemos la fecha dada por el usuario
    public  void abrirCalendario(){
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month+1) + "/" + year;
                tvFecNacimiento.setText(fecha);
            }
        }, anio, mes,dia);
        datePicker.show();
    }

    //para recuperar el telefono mas el indicativo
    private void getNumber(){
        String fullNumber = ccp.getFullNumber() + etText.getText().toString();
    }


    @Override
    public EditText createTextViewName() {
        return tvName;
    }


    @Override
    public EditText createTextViewLastName() {
        return tvApellidos;
    }

    @Override
    public EditText createTextViewId() {
        return tvNumeroIdentidad;
    }

    @Override
    public EditText createTextViewEmail() {
        return tvCorreo;
    }

    @Override
    public EditText createTextViewCellphone() {
        return etText;
    }

    @Override
    public  TextView createTextViewBornAt() {
        return tvFecNacimiento;
    }

    @Override
    public Usuario updateUser() {

        Usuario usuario = new Usuario(tvCorreo.getText().toString(), tvNumeroIdentidad.getText().toString(), tvName.getText().toString(),
                tvApellidos.getText().toString(), etText.getText().toString(), tvFecNacimiento.getText().toString());
        return usuario;
    }

    @Override
    public RelativeLayout imagenCarga() {
        return imagenCarga;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //camara
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                irACamara();

            } else {
                Toast.makeText(view.getContext(), "Se necesitan los permisos", Toast.LENGTH_SHORT).show();

            }
        }
        //galeria
        if (requestCode == REQUEST_PERMISSION_GALERY){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            } else{
                Toast.makeText(view.getContext(), "Se necesitan los permisos", Toast.LENGTH_SHORT).show();
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
                img_eds.setImageBitmap(bitmapCa);

                Log.i("TAG", "Result=>" + bitmapCa);

            } else {
                Toast.makeText(view.getContext(), "Se requieren los permisos", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_IMAGE_GALEY){
            if (resultCode == Activity.RESULT_OK){
                Uri uriGa =  data.getData();
                img_eds.setImageURI(uriGa);

            } else {
                Toast.makeText(view.getContext(), "No elegiste ninguna imagen", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //va a la camara
    private void irACamara(){
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, REQUEST_IMAGE_CAMERA);
    }

    private void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_IMAGE_GALEY);
    }

    String convertirImagenBase64(Bitmap userimage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userimage.compress(Bitmap.CompressFormat.JPEG , 100, baos);
        byte[] data = baos.toByteArray();
        String encoded = Base64.encodeToString(data, Base64.DEFAULT);
        return encoded;
    }
}