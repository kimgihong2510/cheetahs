package com.example.letter
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.letter.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var binding: ActivityMapsBinding
    private lateinit var name:String//로그인 화면에서 가져옴
    private lateinit var number:String//로그인 화면에서 가져옴
    private lateinit var major:String//로그인 화면에서 가져옴
    private lateinit var tact:String//로그인 화면에서 가져옴

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack
    private val REQUEST_ACCESS_FINE_LOCATION=1000
    private lateinit var mapCircle : Circle
    lateinit var CurrentCoordinate : LatLng
    val KNU = LatLng(35.888166756477105, 128.61056411139742)
    val Border1 = LatLng(35.891144, 128.603872)
    val Border2 = LatLng(35.885720, 128.602730)
    val Border3 = LatLng(35.883830, 128.615279)
    val Border4 = LatLng(35.892905, 128.620085)
    val Border5 = LatLng(35.898328, 128.611631)
    lateinit var borderline : Polygon





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.name = intent.getStringExtra("name").toString()
        this.major = intent.getStringExtra("major").toString()
        this.number = intent.getStringExtra("number").toString()
        this.tact = intent.getStringExtra("tact").toString()


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationInit()
    }

    private fun locationInit(){
        fusedLocationProviderClient=FusedLocationProviderClient(this)

        locationCallback=MyLocationCallBack()

        locationRequest = LocationRequest()

        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY

        locationRequest.interval=1000

        locationRequest.fastestInterval = 5000
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Coordinate and move the camera
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17F))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(KNU))
        mMap.addMarker(MarkerOptions().position(KNU).title("Marker in KNU_IT"))

        borderline=googleMap.addPolygon(PolygonOptions()
            .add(
                Border1, Border2, Border3, Border4, Border5
            )
        )
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates((locationRequest), locationCallback, null)
    }

    inner class MyLocationCallBack:LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?){
            super.onLocationResult(locationResult)

            val location=locationResult?.lastLocation

            location?.run{
                val latLng=LatLng(latitude, longitude)
                CurrentCoordinate=latLng
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                try {
                    mapCircle.remove()
                }
                catch(e: Exception){
                    println("hi")
                }
                mapCircle= mMap.addCircle(
                    CircleOptions()
                        .center(latLng)
                        .radius(70.0)
                        .strokeWidth(2f)
                        .strokeColor(Color.argb(200,0,255, 255))
                        .fillColor(Color.argb(20,0,255, 255))
                )
            }
        }
    }


    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                cancel()
            }
            else{
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
        }
        else{
            ok()
        }
    }

    private fun showPermissionInfoDialog(){
        Toast.makeText(this, "현재 위치 정보를 얻기 위해서 권한이 필요합니다.", Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(this@MapsActivity,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_ACCESS_FINE_LOCATION->{
                if((grantResults.isNotEmpty()
                            && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    addLocationListener()
                }
                else{
                    Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener(){
        fusedLocationProviderClient.removeLocationUpdates((locationCallback))
    }

    override fun onResume(){
        super.onResume()

        permissionCheck(cancel={
            showPermissionInfoDialog()
        }, ok={
            addLocationListener()
        })
    }



    private fun isPointInPolygon(tap: LatLng, vertices: ArrayList<LatLng>): Boolean {
        var intersectCount = 0
        for (j in 0 until vertices.size - 1) {
            if (rayCastIntersect(tap, vertices[j], vertices[j + 1])) {
                intersectCount++
            }
        }
        return intersectCount % 2 == 1 // odd = inside, even = outside;
    }

    private fun rayCastIntersect(tap: LatLng, vertA: LatLng, vertB: LatLng): Boolean {
        val aY = vertA.latitude
        val bY = vertB.latitude
        val aX = vertA.longitude
        val bX = vertB.longitude
        val pY = tap.latitude
        val pX = tap.longitude
        if (aY > pY && bY > pY || aY < pY && bY < pY
            || aX < pX && bX < pX
        ) {
            return false // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }
        val m = (aY - bY) / (aX - bX) // Rise over run
        val bee = -aX * m + aY // y = mx + b
        val x = (pY - bee) / m // algebra is neat!
        return x > pX
    }
}