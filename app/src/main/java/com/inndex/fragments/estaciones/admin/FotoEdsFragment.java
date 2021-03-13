package com.inndex.fragments.estaciones.admin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.inndex.R;

public class FotoEdsFragment extends Fragment {


    private  static  final  int REQUEST_PERMISSION_CAMERA = 100;
    private  static  final  int REQUEST_IMAGE_CAMERA = 101;
    private  static  final  int REQUEST_PERMISSION_GALERY = 102;
    private  static  final  int REQUEST_IMAGE_GALEY = 103;
    private LinearLayout llCamaraEds, llGalleryEds, llEliminar;
    private ImageView img_eds;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_foto_eds, container, false);

        llCamaraEds = view.findViewById(R.id.ll_camara_eds);
        llGalleryEds = view.findViewById(R.id.ll_galeria_eds);
        llEliminar = view.findViewById(R.id.ll_eliminar);

        img_eds = view.findViewById(R.id.img_eds);
        img_eds.setScaleType(ImageView.ScaleType.CENTER_CROP);

        llEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_eds.setImageResource(R.drawable.fondo_negro);
            }
        });

        llCamaraEds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        irACamara();
                    } else {
                        ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }

                }else{
                    irACamara();
                }
            }
        });

        llGalleryEds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        abrirGaleria();
                    }else {
                        ActivityCompat.requestPermissions((Activity) v.getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_GALERY);
                    }

                } else {
                    abrirGaleria();
                }

            }
        });

        return  view;

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

}