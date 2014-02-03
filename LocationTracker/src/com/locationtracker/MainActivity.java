package com.locationtracker;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity implements LocationListener, ITrackerListener {
	private LocationManager	locationManager	= null;
	private GoogleMap		map				= null;
	private Marker			userMarker		= null;
	private LatLng			userLatLng		= null;
	private boolean			animate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		enableMapUiSettings();
	}

	@Override
	protected void onResume() {
		super.onResume();
		final String provider = checkProvider();
		if (provider == null)
			showAlert(R.string.alert, R.string.enable_gps_wifi, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		else if (provider.equals(LocationManager.GPS_PROVIDER))
			showAlert(R.string.alert, R.string.use_wifi, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					locationManager.requestLocationUpdates(provider, 0, 0, MainActivity.this);
				}
			});
		else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
			ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting())
				showAlert(R.string.alert, R.string.enable_wifi, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						locationManager.requestLocationUpdates(provider, 0, 0, MainActivity.this);
					}
				});
			else if (manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting())
				locationManager.requestLocationUpdates(provider, 0, 0, this);
		}
	}

	@Override
	protected void onPause() {
		locationManager.removeUpdates(this);
		super.onPause();
	}

	@Override
	public void onLocationChanged(Location location) {
		userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
		drawMarker();
		drawPath();
		showLocationInfo();
		if (!animate)
			zoomAndAnimate();
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public String checkProvider() {
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			return LocationManager.NETWORK_PROVIDER;
		else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
			return LocationManager.GPS_PROVIDER;
		return null;
	}

	@Override
	public void enableMapUiSettings() {
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setAllGesturesEnabled(true);
	}

	@Override
	public void showAlert(int title, int message, DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener) {
		new AlertDialog.Builder(MainActivity.this).setCancelable(false).setTitle(title).setMessage(message)
				.setPositiveButton("OK", positiveListener).setNegativeButton("Cancel", negativeListener).create()
				.show();
	}

	@Override
	public void drawMarker() {
		if (userMarker == null)
			userMarker = map.addMarker(new MarkerOptions().position(userLatLng).visible(true));
		else
			userMarker.setPosition(userLatLng);
	}

	@Override
	public void drawPath() {
		map.addPolyline(new PolylineOptions().add(userLatLng).width(5).color(Color.BLUE).geodesic(true));
	}

	@Override
	public void showLocationInfo() {
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(userMarker.getPosition().latitude,
					userMarker.getPosition().longitude, 1);
			userMarker.setTitle(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubLocality() + ", "
					+ addresses.get(0).getAdminArea());
		} catch (Exception e) {
			userMarker.setTitle("Searching...");
		}
	}

	@Override
	public void zoomAndAnimate() {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		animate = true;
	}
}
