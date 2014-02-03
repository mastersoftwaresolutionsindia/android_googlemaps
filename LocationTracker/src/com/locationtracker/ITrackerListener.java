package com.locationtracker;

import android.content.DialogInterface;

/**
 * This interface defines the list of actions that can be performed on Google
 * map Classes implementing this interface will typically help in google map
 * settings
 * 
 * @author Master Software Solutions
 * 
 */
public interface ITrackerListener {
	/**
	 * checks the provide from the location manager
	 * 
	 * @return network or gps
	 */
	String checkProvider();

	/**
	 * to enable the settings of google map
	 */
	void enableMapUiSettings();

	/**
	 * for displaying alert
	 * 
	 * @param title
	 *            title of alert
	 * @param message
	 *            message to be displayed
	 * @param positiveListener
	 *            listener for positive button
	 * @param negativeListener
	 *            listener for negative button
	 */
	void showAlert(int title, int message, DialogInterface.OnClickListener positiveListener,
			DialogInterface.OnClickListener negativeListener);

	/**
	 * draw marker on map
	 */
	void drawMarker();

	/**
	 * draw path on map as the location changes
	 */
	void drawPath();

	/**
	 * displays the information depending upon current location
	 */
	void showLocationInfo();

	/**
	 * zoom and animate map to a specific location
	 */
	void zoomAndAnimate();
}
