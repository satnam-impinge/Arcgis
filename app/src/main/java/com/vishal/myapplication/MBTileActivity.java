package com.vishal.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;

import java.io.File;

public class MBTileActivity extends AppCompatActivity {

    MapView mMapView = null;
    ArcGISTiledMapServiceLayer tileLayer;
    boolean activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbtile);

        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView) findViewById(R.id.map);

        // create an ArcGISTiledMapServiceLayer as a background if network available
        activeNetwork = isNetworkAvailable();
//        if (activeNetwork) {
//            tileLayer = new ArcGISTiledMapServiceLayer(
//                    "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
//            // Add tiled layer to MapView
//            mMapView.addLayer(tileLayer);
//        } else {
//            Toast toast = Toast.makeText(this, "Offline", Toast.LENGTH_SHORT);
//            toast.show();
//        }
        File file = new File(getFilesDir(), "satnam_lake.mbtiles");
//        File file = new File(getFilesDir(), "world_countries.mbtiles");
//        File file = new File(getFilesDir(), "trails.mbtiles");
        MBTilesLayer mbLayer = new MBTilesLayer(file.getAbsolutePath());
        mbLayer.setOpacity(0.5f);
        mMapView.addLayer(mbLayer);
        mMapView.enableWrapAround(true);
//        mMapView.setMapBackground(Color.GRAY, Color.TRANSPARENT, 1, 1);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.unpause();
    }

}
