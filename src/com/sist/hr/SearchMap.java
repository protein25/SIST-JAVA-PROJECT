package com.sist.hr;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

public class SearchMap extends MapView {
	static MapViewOptions mapOptions = new MapViewOptions();

	private Marker mainMarker;
	static private JTextField addressEdit;

	public SearchMap(boolean standalone) {
		super(mapOptions);

		setOnMapReadyHandler(new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status) {
				// Check if the map is loaded correctly
				if (status == MapStatus.MAP_STATUS_OK) {
					// Getting the associated map object
					final Map map = getMap();
					// Creating a map options object
					MapOptions options = new MapOptions();
					// Creating a map type control options object
					MapTypeControlOptions controlOptions = new MapTypeControlOptions();
					// Changing position of the map type control
					controlOptions.setPosition(ControlPosition.TOP_RIGHT);
					// Setting map type control options
					options.setMapTypeControlOptions(controlOptions);
					// Setting map options
					map.setOptions(options);
					// Setting the map center
					map.setCenter(new LatLng(37.541, 126.986));
					// Setting initial zoom value
					map.setZoom(15.0);
				}
			}
		});

		addressEdit = new JTextField("검색할 장소를 입력하세요.");
		addressEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				geocodePlace(addressEdit.getText());
			}
		});
		
		JFrame frame = new JFrame("Places Search");

		frame.add(addressEdit, BorderLayout.NORTH);
		frame.add(this, BorderLayout.CENTER);
		frame.setSize(500, 590);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	private void geocodePlace(String address) {
		final Map map = getMap();

		// Creating geocoder request
		GeocoderRequest request = new GeocoderRequest();
		// Set address for request
		request.setAddress(address);
		// Geocoding a position by address
		getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
			@Override
			public void onComplete(GeocoderResult[] results, GeocoderStatus status) {
				// Checking operation status
				if ((status == GeocoderStatus.OK) && (results.length > 0)) {
					// Getting first result
					GeocoderResult result = results[0];
					// Getting location (coords)
					LatLng location = result.getGeometry().getLocation();
					// Centering map to result location
					map.setCenter(location);
					// Initializing main marker
					mainMarker = new Marker(map);
					// Moving marker to result location
					mainMarker.setPosition(location);
					// Showing marker on map
					mainMarker.setVisible(true);
				}
			}
		});
	}

//	public static void main(String[] args) {
//		final SearchMap map = new SearchMap(true);
//
//
//	}
}
