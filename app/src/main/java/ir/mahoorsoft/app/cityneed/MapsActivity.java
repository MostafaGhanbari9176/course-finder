package ir.mahoorsoft.app.cityneed;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;

import static ir.mahoorsoft.app.cityneed.G.context;
import static ir.mahoorsoft.app.cityneed.model.struct.PrefKey.lat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Marker marker;
    RadioButton satelite;
    RadioButton normal;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        satelite = (RadioButton) findViewById(R.id.rbSateliteActivityMap);
        normal = (RadioButton) findViewById(R.id.rbNormalActivityMap);

        satelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapType(satelite.isChecked());
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapType(!normal.isChecked());
            }
        });

        (findViewById(R.id.btnSaveLocationActivityMap)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocation();
            }
        });


    }

    private void setMapType(boolean sateliteShow) {
        if (sateliteShow)
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void checkLocation() {
        if (marker.getPosition().longitude < 60.90546827763319 && marker.getPosition().longitude > 60.79812191426753
                && marker.getPosition().latitude < 29.548549235896427 && marker.getPosition().latitude > 29.433302501885095) {
            showDialog("ثبت موقعیت مکانی", "آیا از موقعیت انتخاب شده مطمعن هستید؟", "بله", "خیر");
        } else {
            showDialog("خطا!", "درحال حاظر سرویس ما فقط در شهر زاهدان ارائه می شود.", "", "قبول");
        }
    }


    private void showDialog(String title, String message, String btntrue, String btnFalse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btntrue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveLocation();
                dialog.cancel();
            }
        });
        builder.setNegativeButton(btnFalse, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void saveLocation() {
        Toast.makeText(this, marker.getPosition().latitude + " ; " + marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
        Pref.saveStringValue(lat, String.valueOf(marker.getPosition().latitude));
        Pref.saveStringValue(PrefKey.lon, String.valueOf(marker.getPosition().longitude));
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        this.finish();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        normal.setChecked(true);
        showDialog("راهنما", "لطفا برای تعیین موقعیت خود بر روی مکان مورد نظر کلیک کنید,همچنین می توانید با نگه داشتن مکان نما و جابجایی آن مکان مورد نظر خودرا دقیقتر انتخاب کنید.سپس بر روی دکمه پایین صفحه کلیک کنید.", "", "قبول");
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        try {
            if (Pref.getStringValue(PrefKey.lat, "").length() == 0)
                location = new LatLng(29.4892550, 60.8612360);
            else
                location = new LatLng(Double.parseDouble(Pref.getStringValue(PrefKey.lat, "")), Double.parseDouble(Pref.getStringValue(PrefKey.lon, "")));

        }catch (Exception e){
            showDialog("خطا!", "لطفا مجددا تلاش کنید.", "", "قبول");
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.draggable(true);
        marker = mMap.addMarker(markerOptions);
        marker.setDraggable(true);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                location).zoom(12).build();
        // mMap.setBuildingsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        marker.setPosition(latLng);
    }
}
