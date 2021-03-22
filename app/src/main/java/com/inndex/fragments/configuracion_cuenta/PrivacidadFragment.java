package com.inndex.fragments.configuracion_cuenta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inndex.R;
import com.inndex.activities.LoginActivity;
import com.inndex.enums.EUserAccountState;
import com.inndex.model.Usuario;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.utils.Constantes;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacidadFragment extends Fragment {

    private RelativeLayout statusApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_privacidad, null);
        statusApi = root.findViewById(R.id.status_api);

        final TextView body = root.findViewById(R.id.text_body);

        ImageButton btnBack = root.findViewById(R.id.btnBack);
        TextView titulo = root.findViewById(R.id.tv_toolbar_titulo);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        TextView btnEliminarCuenta = root.findViewById(R.id.btnEliminarCuenta);
        btnEliminarCuenta.setOnClickListener(v -> deleteAccount());

        titulo.setText("Privacidad");

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        SpannableString str2 = new SpannableString("Inndex, utiliza los servicios de ubicación para pfrecer un mejor servicio. ");
        str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
        builder.append(str2);

        SpannableString str1 = new SpannableString("Ver mas...");
        str1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str1.length(), 0);
        builder.append(str1);

        body.setText(builder, TextView.BufferType.SPANNABLE);

        body.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_privacidadFragment_to_ubicacionTextoFragment));

        return root;
    }


    private void deleteAccount() {

        Usuario usuario = new Usuario();
        usuario.setEstadoCuenta(EUserAccountState.INACTIVE.getId());
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        long id = sharedPreferences.getLong(Constantes.DEFAULT_USER_ID, 0);
        if (id == 0) {
            Toast.makeText(requireContext(), "ERROR ELIMINANDO LA CUENTA.", Toast.LENGTH_SHORT).show();
            return;
        }
        usuario.setId(id);

        Call<Usuario> callUpdateAccountState = MedidorApiAdapter.getApiService().updateUserAccountState(usuario);
        statusApi.setVisibility(View.VISIBLE);
        callUpdateAccountState.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                statusApi.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "INFORMACIÓN ELIMINADA DE MANERA EXITOSA", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().clear().apply();
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "ERROR ELIMINANDO LA CUENTA. " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                statusApi.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "ERROR ELIMINANDO LA CUENTA.", Toast.LENGTH_SHORT).show();
            }
        });


    }


}