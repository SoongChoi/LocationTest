package com.kth.marty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.net.Uri;
import android.os.Bundle;
import android.location.*;

public class LocationApiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        _locationMain = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        Criteria _criteriaConfig = new Criteria();
        _criteriaConfig.setAccuracy(Criteria.NO_REQUIREMENT);
        _criteriaConfig.setPowerRequirement(Criteria.NO_REQUIREMENT);
        
        String _bestProvider = _locationMain.getBestProvider(_criteriaConfig, true);
        
        _locationMain.requestLocationUpdates(_bestProvider, 1000, 0, _locationListener);

        _geoInfoBasic = (TextView) findViewById(R.id.tvGeoInfoBasic);
        _geoInfoDetail = (TextView) findViewById(R.id.tvGeoInfoDetail);
        _btnStart = (Button) findViewById(R.id.btnOpenLocation);
    }
    
    private TextView _geoInfoBasic = null;
    private TextView _geoInfoDetail = null;
    private Button	_btnStart = null;
    
    private Location _recentLocationInfo;
    
    private LocationManager _locationMain = null;
    
    private void updateGeoDetailInfo(Location location)
    {
    	_geoInfoDetail.setText(String.format("Lat: %f , Lon: %f", 
    			location.getLatitude(), location.getLongitude()));
    }
    
    private void updateProviderInfo(boolean state)
    {
    	if(state)
    			_geoInfoBasic.setText("==> Provider Disabled !!!");
    	else	_geoInfoBasic.setText("Provider Enabled !!!");
    }

    private View.OnClickListener on_OpenMap = new View.OnClickListener() {
    	public void onClick(View v) {
    		doOpenGoogleMap(_recentLocationInfo);
    	}
    };
    
    private void doOpenGoogleMap(Location location)
    {
    	Uri _GeoURI = Uri.parse(String.format("geo:%f,%f",
    			location.getLatitude(), location.getLongitude()) );
    	Intent _GeoMap = new Intent(Intent.ACTION_VIEW, _GeoURI);
    	startActivity(_GeoMap);
    }
    
    private final LocationListener _locationListener = new LocationListener() 
    {
    	public void onLocationChanged(Location location)
    	{
    		_recentLocationInfo = location;
    		
    		updateGeoDetailInfo(location);
    	}
    	public void onProviderDisabled(String provider) {
    		updateProviderInfo(false);
    	}
    	public void onProviderEnabled(String provider) {
    		updateProviderInfo(true);
    	}
    	public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    
}