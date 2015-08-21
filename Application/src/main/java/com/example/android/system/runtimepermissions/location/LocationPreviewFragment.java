package com.example.android.system.runtimepermissions.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.system.runtimepermissions.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by frank on 8/21/15.
 */
public class LocationPreviewFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final String TAG = "Location";

    public static LocationPreviewFragment newInstance() {
        return new LocationPreviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_map, null);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map_preview);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.map_preview, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.867, 151.206);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
        .title("Sydney")
        .snippet("The most popular city in Australia")
        .position(sydney));

    }
}
