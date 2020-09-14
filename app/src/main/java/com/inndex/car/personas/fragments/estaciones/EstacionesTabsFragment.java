package com.inndex.car.personas.fragments.estaciones;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.adapter.SeccionesCombustibleAdapter;
import com.inndex.car.personas.utils.Constantes;

import java.util.Objects;


public class EstacionesTabsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AppBarLayout appBar;
    private TabLayout tabs;
    private MainActivity mainActivity;

    public EstacionesTabsFragment() {
    }

    public EstacionesTabsFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static EstacionesTabsFragment newInstance(String param1, String param2) {
        EstacionesTabsFragment fragment = new EstacionesTabsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tabsView = inflater.inflate(R.layout.fragment_estaciones_tabs, container, false);
        if (Constantes.ROTATION == 0) {
            View parent = (View) container.getParent();
            if (appBar == null) {
                appBar = parent.findViewById(R.id.app_bar);
                tabs = new TabLayout(Objects.requireNonNull(getActivity()));
                tabs.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
                tabs.setSelectedTabIndicatorColor(Color.parseColor("#4A86E8"));
                tabs.setSelectedTabIndicator(R.drawable.custom_indicator);
                appBar.addView(tabs);

                ViewPager viewPager = tabsView.findViewById(R.id.vp_estaciones_tabs);
                llenarViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }


                });
                tabs.setupWithViewPager(viewPager);
            } else {
                Constantes.ROTATION = 1;
            }
        }
        return tabsView;
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesCombustibleAdapter adapter = new SeccionesCombustibleAdapter(getFragmentManager());
        adapter.addFragment(new EstacionesListFragment(mainActivity), "Lista");
        adapter.addFragment(new EstacionesMapFragment(mainActivity), "Mapa");
        viewPager.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Constantes.ROTATION == 0) {
            appBar.removeView(tabs);
        }
    }
}