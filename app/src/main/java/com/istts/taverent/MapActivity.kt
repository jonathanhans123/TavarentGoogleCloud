package com.istts.taverent


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.core.Location
import com.here.sdk.core.Point2D
import com.here.sdk.core.engine.SDKNativeEngine
import com.here.sdk.core.engine.SDKOptions
import com.here.sdk.core.errors.InstantiationErrorException
import com.here.sdk.gestures.GestureType
import com.here.sdk.gestures.TapListener
import com.here.sdk.mapviewlite.*
import com.here.sdk.search.*


class MapActivity : AppCompatActivity(){
    private val TAG = MapActivity::class.java.simpleName
    private lateinit var permissionsRequestor: PermissionsRequestor
    private lateinit var platformPositioningProvider: PlatformPositioningProvider
    private lateinit var mapView: MapViewLite
    private lateinit var editLocation: EditText
    private lateinit var autocompleteLocation: AutoCompleteTextView
    private lateinit var confirm: ImageButton
    private lateinit var upbutton: ImageButton
    private lateinit var downbutton: ImageButton
    private lateinit var mapMarker: MapMarker
    private lateinit var searchEngine: SearchEngine
    private lateinit var linearmap: LinearLayout
    private lateinit var coordinates: GeoCoordinates
    private lateinit var stringAdapter: ArrayAdapter<String>
    private lateinit var textZoom: TextView
    var addresses: ArrayList<String> = ArrayList()
    var zoomLevel = 14.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usually, you need to initialize the HERE SDK only once during the lifetime of an application.
        initializeHERESDK()

        setContentView(R.layout.activity_map)

        // Get a MapView instance from the layout.
        mapView = findViewById(R.id.map_view)
        editLocation = findViewById(R.id.editLocation)
        confirm = findViewById(R.id.imageButton9)
        linearmap = findViewById(R.id.linearmap)
        autocompleteLocation = findViewById(R.id.autoCompleteTextView)
        mapView.onCreate(savedInstanceState)
        stringAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, addresses)
        autocompleteLocation.setAdapter(stringAdapter)
        textZoom = findViewById(R.id.textView24)
        textZoom.text = zoomLevel.toString()
        mapView.gestures.disableDefaultAction(GestureType.DOUBLE_TAP)


        searchEngine = try {
            SearchEngine()
        } catch (e: InstantiationErrorException) {
            throw java.lang.RuntimeException("Initialization of SearchEngine failed: " + e.error.name)
        }

        handleAndroidPermissions()
        platformPositioningProvider = PlatformPositioningProvider(this)

platformPositioningProvider.startLocating {
    addPin(mapView, GeoCoordinates(it.latitude, it.longitude))
    coordinates = GeoCoordinates(it.latitude, it.longitude)
    mapView.camera.target = GeoCoordinates(it.latitude, it.longitude)
    getAddressForCoordinates(GeoCoordinates(it.latitude, it.longitude))
    platformPositioningProvider.stopLocating()
    linearmap.setOnClickListener {
        autocompleteLocation.setText("Loading . . .")
        platformPositioningProvider.startLocating {
            mapView.mapScene.removeMapMarker(mapMarker)
            val geoCoordinates = GeoCoordinates(it.latitude, it.longitude)
            coordinates = geoCoordinates
            addPin(mapView, geoCoordinates)
            getAddressForCoordinates(geoCoordinates)
            platformPositioningProvider.stopLocating()
        }
    }
}



        mapView.gestures.tapListener = object : TapListener {
            override fun onTap(p0: Point2D) {
                autocompleteLocation.setText("Loading . . .")
                mapView.mapScene.removeMapMarker(mapMarker)
                val geoCoordinates = mapView.camera.viewToGeoCoordinates(p0)
                coordinates = geoCoordinates
                addPin(mapView, geoCoordinates)
                getAddressForCoordinates(geoCoordinates)
            }
        }

        confirm.setOnClickListener {
            if (autocompleteLocation.text.toString() == "Location . . .") {
                Toast.makeText(this@MapActivity, "Masih Loading", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent()
                resultIntent.putExtra("alamat", autocompleteLocation.text.toString())
                resultIntent.putExtra("koordinat",
                    coordinates.latitude.toString() + "," + coordinates.longitude.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        autocompleteLocation.setOnClickListener {
            autoSuggestExample(autocompleteLocation.text.toString())
        }

        autocompleteLocation.setOnItemClickListener { adapterView, view, i, l ->
            geocodeAddressAtLocation(autocompleteLocation.text.toString(),mapView.camera.target)
        }

        upbutton = findViewById(R.id.imageButton10)
        downbutton = findViewById(R.id.imageButton11)
        upbutton.setOnClickListener {
            zoomLevel++
            mapView.camera.zoomLevel = zoomLevel
            textZoom.text = zoomLevel.toString()
        }
        downbutton.setOnClickListener{
            zoomLevel--
            mapView.camera.zoomLevel = zoomLevel
            textZoom.text = zoomLevel.toString()
        }
    }

    private fun geocodeAddressAtLocation(queryString: String, geoCoordinates: GeoCoordinates) {
        val query = AddressQuery(queryString, geoCoordinates)
        val searchOptions = SearchOptions()
        searchOptions.languageCode = LanguageCode.ID_ID
        searchOptions.maxItems = 30
        searchEngine.search(query, searchOptions, object: SearchCallback {
            override fun onSearchCompleted(p0: SearchError?, p1: MutableList<Place>?) {
                if (p0 != null) {
                    Toast.makeText(this@MapActivity, "Error", Toast.LENGTH_SHORT).show()
                    return
                }
                if (p1 != null) {
                    for (geocodingResult in p1) {
                        val geoCoordinates = geocodingResult.geoCoordinates
                        val address = geocodingResult.address
                        val locationDetails = (address.addressText
                                + ". GeoCoordinates: " + geoCoordinates!!.latitude
                                + ", " + geoCoordinates.longitude)
                        addPin(mapView, geoCoordinates)
                        mapView.camera.target = geoCoordinates
                        coordinates = geoCoordinates
                    }
                }
            }
        })
    }

    private fun autoSuggestExample(search: String) {
        val centerGeoCoordinates: GeoCoordinates = mapView.camera.target
        val searchOptions = SearchOptions()
        searchOptions.languageCode = LanguageCode.ID_ID
        searchOptions.maxItems = 5
        val queryArea = TextQuery.Area(centerGeoCoordinates)
        addresses.clear()
        searchEngine.suggest(
            TextQuery(search,
                queryArea),
            searchOptions,
            object : SuggestCallback {
                override fun onSuggestCompleted(p0: SearchError?, p1: MutableList<Suggestion>?) {
                    if (p0 != null) {
                        Toast.makeText(this@MapActivity,
                            "Autosuggest Error: " + p0.name,
                            Toast.LENGTH_SHORT).show()
                        return
                    }

                    if (p1 != null) {
                        addresses.clear()
                        for (autosuggestResult in p1) {
                            var addressText = "Not a place."
                            val place = autosuggestResult.place
                            if (place != null) {
                                addressText = place.address.addressText
                            }
                            addresses.add(addressText)
                            stringAdapter = ArrayAdapter(this@MapActivity,android.R.layout.simple_list_item_1,addresses)
                            autocompleteLocation.setAdapter(stringAdapter)
                        }
                    }
                }
            }
        )
    }


    private fun getAddressForCoordinates(geoCoordinates: GeoCoordinates) {
val reverseGeocodingOptions = SearchOptions()
reverseGeocodingOptions.languageCode = LanguageCode.ID_ID
reverseGeocodingOptions.maxItems = 1
searchEngine.search(geoCoordinates, reverseGeocodingOptions, object:SearchCallback{
    override fun onSearchCompleted(p0: SearchError?, p1: MutableList<Place>?) {
        if (p0 != null) {
            Toast.makeText(this@MapActivity, "Reverse geocoding:Error: $p0", Toast.LENGTH_SHORT).show()
            return
        }

        // If error is null, list is guaranteed to be not empty.
        if (p1 != null) {
            autocompleteLocation.setText(p1.get(0).address.addressText.toString())
        }
    }

})
    }


    fun addPin(view:View,geoCoordinates: GeoCoordinates){
        val mapImage = MapImageFactory.fromResource(this.getResources(), R.drawable.placeholder_resize)

        mapMarker = MapMarker(geoCoordinates)
        mapMarker.addImage(mapImage, MapMarkerImageStyle())

        mapView.mapScene.addMapMarker(mapMarker)
    }

    private fun handleAndroidPermissions() {
        permissionsRequestor = PermissionsRequestor(this)
        permissionsRequestor.request(object : PermissionsRequestor.ResultListener {
            override fun permissionsGranted() {
                loadMapScene()
            }

            override fun permissionsDenied() {
                Log.e(TAG, "Permissions denied by user.")
            }
        })
    }

    private fun loadMapScene() {
        // Load a scene from the SDK to render the map with a map style.
        mapView.mapScene.loadScene(MapStyle.NORMAL_DAY
        ) { errorCode ->
            if (errorCode == null) {
                mapView.camera.target = GeoCoordinates(-7.286588157808639, 112.77709248650937)
                mapView.camera.zoomLevel = 14.0
            } else {
                Log.d(TAG, "onLoadScene failed: $errorCode")
            }
        }
    }


    private fun convertLocation(nativeLocation: android.location.Location): Location? {
        val geoCoordinates = GeoCoordinates(
            nativeLocation.latitude,
            nativeLocation.longitude,
            nativeLocation.altitude)
        val location = Location(geoCoordinates)
        if (nativeLocation.hasBearing()) {
            location.bearingInDegrees = nativeLocation.bearing.toDouble()
        }

        if (nativeLocation.hasSpeed()) {
            location.speedInMetersPerSecond = nativeLocation.speed.toDouble()
        }

        if (nativeLocation.hasAccuracy()) {
            location.horizontalAccuracyInMeters = nativeLocation.accuracy.toDouble()
        }

        return location
    }

    private fun initializeHERESDK() {
        // Set your credentials for the HERE SDK.
        val accessKeyID = "HvBSWvTW-ajXnBs6abqRLw"
        val accessKeySecret = "5pdus4zHhrkbdIggtB8K5svsGc7BMGc2GZpJuX4XSQQXF16CSCRGmeqoJmRnfSdYtyjRACVFHzchzABZEmnGTQ"
        val options = SDKOptions(accessKeyID, accessKeySecret)
        try {
            SDKNativeEngine.makeSharedInstance(this, options)
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of HERE SDK failed: " + e.error.name.toString())
        }
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        disposeHERESDK()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mapView.onSaveInstanceState()
        super.onSaveInstanceState(outState)
    }

    private fun disposeHERESDK() {
        // Free HERE SDK resources before the application shuts down.
        // Usually, this should be called only on application termination.
        // Afterwards, the HERE SDK is no longer usable unless it is initialized again.
        val sdkNativeEngine = SDKNativeEngine.getSharedInstance()
        if (sdkNativeEngine != null) {
            sdkNativeEngine.dispose()
            // For safety reasons, we explicitly set the shared instance to null to avoid situations,
            // where a disposed instance is accidentally reused.
            SDKNativeEngine.setSharedInstance(null)
        }
    }
}