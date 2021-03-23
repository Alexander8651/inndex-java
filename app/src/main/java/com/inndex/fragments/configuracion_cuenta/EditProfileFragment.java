package com.inndex.fragments.configuracion_cuenta;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;
import com.inndex.R;
import com.inndex.enums.EGenero;
import com.inndex.fragments.configuracion_cuenta.presenter.IpresenterEditAccount;
import com.inndex.fragments.configuracion_cuenta.presenter.PesenterEditAccount;
import com.inndex.model.Usuario;
import com.inndex.utils.Constantes;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.query.In;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class EditProfileFragment extends Fragment implements IeditAccout {

    private Button btnMasculino, btnFemenino, btnGuardar;
    private EditText edtCelular, etPassworDesa;
    private LinearLayout navPassword;
    private CountryCodePicker ccp;
    private View view;
    private ImageButton btnBack;
    private EditText edtName;
    private EditText edtApellidos;
    private EditText edtNumeroIdentidad;
    private EditText edtCorreo;
    private TextView tvFecNacimiento;
    private IpresenterEditAccount ipresenterEditAccount;
    private SharedPreferences myPreferences;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    private static final int REQUEST_PERMISSION_GALERY = 102;
    private static final int REQUEST_IMAGE_GALEY = 103;
    private ImageView imgPerfil;

    private Integer genero = 0;
    private Usuario currentUser;

    private FirebaseAuth mAuth;
    private RelativeLayout statusApi;
    long userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ccp = view.findViewById(R.id.ccp);// Las banderas y sus indicativos
        edtCelular = view.findViewById(R.id.etCel);

        tvFecNacimiento = view.findViewById(R.id.tvFecNacimientoCapt);
        navPassword = view.findViewById(R.id.navPassword);

        etPassworDesa = view.findViewById(R.id.etPasswordDesa);
        etPassworDesa.setEnabled(false);
        statusApi = view.findViewById(R.id.statusApi);

        btnMasculino = view.findViewById(R.id.btnMasculino);
        btnFemenino = view.findViewById(R.id.btnFemenino);
        edtName = view.findViewById(R.id.user_name_edit);
        edtApellidos = view.findViewById(R.id.etApellidos);
        edtNumeroIdentidad = view.findViewById(R.id.etNumDeIdentidad);
        edtCorreo = view.findViewById(R.id.etEmail);
        btnGuardar = view.findViewById(R.id.guardar_usuario);

        imgPerfil = view.findViewById(R.id.imagen_perfil_editar);

        myPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        userID = myPreferences.getLong(Constantes.DEFAULT_USER_ID, 0);

        ipresenterEditAccount = new PesenterEditAccount(this, userID, requireContext(), view);

        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);
        titulo.setText(getString(R.string.editar_perfil));

        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        btnGuardar.setOnClickListener(v -> {
            ipresenterEditAccount.updateUserInfoAccount();
        });

        mAuth = FirebaseAuth.getInstance();
        String password = "Inndex2021%";
        statusApi.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(getString(R.string.inndex_email), password)
                .addOnCompleteListener(requireActivity(), task -> {
                    statusApi.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        checkPermissions();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        btnBack.callOnClick();
                    }
                });

        //Cambiamos a masculino en negro
        btnMasculino.setOnClickListener(v -> {
            genero = EGenero.MASCULINO.getId();
            btnMasculino.setBackgroundColor(Color.BLACK);
            btnMasculino.setTextColor(Color.WHITE);
            btnFemenino.setBackgroundColor(Color.WHITE);
            btnFemenino.setTextColor(Color.BLACK);
        });

        //Cambiamos a femenino en negro
        btnFemenino.setOnClickListener(v -> {
            genero = EGenero.FEMENINO.getId();
            btnFemenino.setBackgroundColor(Color.BLACK);
            btnFemenino.setTextColor(Color.WHITE);
            btnMasculino.setBackgroundColor(Color.WHITE);
            btnMasculino.setTextColor(Color.BLACK);
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
        tvFecNacimiento.setOnClickListener(v -> abrirCalendario());

        //navPassword.setOnClickListener(v ->
        //        Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_editPasswordFragment));
        return view;
    }

    //Abrimos el calendario y obtenemos la fecha dada por el usuario
    public void abrirCalendario() {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this.getContext(), (view, year, month, dayOfMonth) -> {
            //String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            String fecha = year + "-" + dayOfMonth + "-" + (month + 1);
            tvFecNacimiento.setText(fecha);
        }, anio, mes, dia);
        datePicker.show();
    }

    //para recuperar el telefono mas el indicativo
    private void getNumber() {
        String fullNumber = ccp.getFullNumber() + edtCelular.getText().toString();
    }

    @Override
    public EditText createTextViewName() {
        return edtName;
    }

    @Override
    public EditText createTextViewLastName() {
        return edtApellidos;
    }

    @Override
    public EditText createTextViewId() {
        return edtNumeroIdentidad;
    }

    @Override
    public EditText createTextViewEmail() {
        return edtCorreo;
    }

    @Override
    public EditText createTextViewCellphone() {
        return edtCelular;
    }

    @Override
    public TextView createTextViewBornAt() {
        return tvFecNacimiento;
    }

    @Override
    public Usuario updateUser() {

        Usuario usuario;
        usuario = new Usuario(edtCorreo.getText().toString(), edtNumeroIdentidad.getText().toString(), edtName.getText().toString(),
                edtApellidos.getText().toString(), edtCelular.getText().toString(), tvFecNacimiento.getText().toString());
        usuario.setId(userID);
        if (genero > 0)
            usuario.setGenero(genero);
        return usuario;
    }

    @Override
    public RelativeLayout imagenCarga() {
        return statusApi;
    }

    @Override
    public Button getBtnMasculino() {
        return btnMasculino;
    }

    @Override
    public Button getBtnFemenino() {
        return btnFemenino;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //camara
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (!(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Navigation.findNavController(view).navigateUp();
                return;
            }
        }
        //galeria
        if (requestCode == REQUEST_PERMISSION_GALERY) {
            if (!(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Navigation.findNavController(view).navigateUp();
                return;
            }
        }
        initCameraAndGalleryButton();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //camara
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmapCa = (Bitmap) data.getExtras().get("data");
                imgPerfil.setImageBitmap(bitmapCa);
            } else {
                Toast.makeText(view.getContext(), "Se requieren los permisos", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_IMAGE_GALEY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uriGa = data.getData();
                imgPerfil.setImageURI(uriGa);
            } else {
                Toast.makeText(view.getContext(), "No elegiste ninguna imagen", Toast.LENGTH_SHORT).show();
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

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
        } else {
            initCameraAndGalleryButton();
        }
    }

    private void initCameraAndGalleryButton() {
        imgPerfil.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), imgPerfil);
            popupMenu.inflate(R.menu.menufotoperfil);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.camara) {
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        irACamara();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                    return true;
                } else if (item.getItemId() == R.id.galeria) {

                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        abrirGaleria();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_GALERY);
                    }
                    return true;
                }
                return false;
            });
        });
    }

    //convierte a base64
    String convertirImagenBase64(Bitmap userimage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userimage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String encoded = Base64.encodeToString(data, Base64.DEFAULT);
        return encoded;
    }
}