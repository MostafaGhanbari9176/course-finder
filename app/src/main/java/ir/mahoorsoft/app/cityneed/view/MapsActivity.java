package ir.mahoorsoft.app.cityneed.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import ir.mahoorsoft.app.cityneed.R;
import ir.mahoorsoft.app.cityneed.model.preferences.Pref;
import ir.mahoorsoft.app.cityneed.model.struct.PrefKey;

import static ir.mahoorsoft.app.cityneed.model.struct.PrefKey.lat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {

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
        try {
            if (marker.getPosition().longitude < 60.90546827763319 && marker.getPosition().longitude > 60.79812191426753
                    && marker.getPosition().latitude < 29.548549235896427 && marker.getPosition().latitude > 29.433302501885095) {
                showDialog("?????? ???????????? ??????????", "?????? ???? ???????????? ???????????? ?????? ?????????? ????????????", "??????", "??????");
            } else {
                showDialog("??????!", "?????????? ???????? ?????????? ???? ?????? ???? ?????? ???????????? ?????????? ???? ??????.", "", "????????");
            }
        } catch (Exception e) {
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
        showDialog("????????????", "???????????? ???????? ???????? ???????????? ???? ???????? ????????,???????? ?????????? ???????? ???????? ?? ???????? ???????? ???????? ???? ?????????? ???????? ???????? ???????? ????????,???????????? ?????????? ???????? ???????? ?????????? ???????????? ?????? ???? ?????? ???????? ???????? ?????? ???????? ????????,???????????? ???? ???????????? ???? ?????? ?????????? ???????? ?????? ?? ?????????????? ???? ???????? ???????? ?????? ?????????? ???????????? ???????????? ????????.?????? ???? ?????? ???????? ?????????? ???????? ???????? ????????.", "", "????????");
        mMap = googleMap;

        try {
            if (Pref.getStringValue(PrefKey.lat, "").length() == 0)
                location = new LatLng(29.4892550, 60.8612360);
            else
                location = new LatLng(Double.parseDouble(Pref.getStringValue(PrefKey.lat, "")), Double.parseDouble(Pref.getStringValue(PrefKey.lon, "")));

        } catch (Exception e) {
            //  showDialog("??????!", "???????? ?????????? ???????? ????????.", "", "????????");
        }
        mMap.setOnMyLocationButtonClickListener(this);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.draggable(true);
        marker = mMap.addMarker(markerOptions);
        marker.setDraggable(true);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                location).zoom(12).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnMapClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            onMyLocationButtonClick();
        } else {
            getPermision();
        }

    }

    private void getPermision() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mMap.setMyLocationEnabled(true);
                            onMyLocationButtonClick();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // next();
                            // show alert dialog navigating to Settings
                            // showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        // Toast.makeText(getApplicationContext(), "?????? ???? ???????????? ???? ??????????!???????? ???????????? ???? ?????????? ???? ???? ???????? ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onMapClick(LatLng latLng) {

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(mMap.getCameraPosition().zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.setPosition(latLng);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        try {
            location = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
            marker.setPosition(location);
        } catch (Exception ignored) {
        }
        return false;
    }
}
