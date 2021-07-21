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
import java.lang.Math.sqrt


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
    val Radius=50.0
    private lateinit var mapCircle : Circle
    private lateinit var mapDot:Circle
    lateinit var CurrentCoordinate : LatLng
    val KNU = LatLng(35.888166756477105, 128.61056411139742)
    val BorderArray = arrayListOf(
        LatLng(35.889540, 128.603830)
        ,LatLng(35.888454, 128.603830)
        ,LatLng(35.886672, 128.604828)
        ,LatLng(35.886401, 128.607551)
        ,LatLng(35.886058, 128.607506)
        ,LatLng(35.885795, 128.607842)
        ,LatLng(35.885744, 128.608309)
        ,LatLng(35.885417, 128.608756)
        ,LatLng(35.885313, 128.609412)
        ,LatLng(35.885654, 128.609570)
        ,LatLng(35.885682, 128.609947)
        ,LatLng(35.886272, 128.609987)
        ,LatLng(35.886174, 128.612861)
        ,LatLng(35.885498, 128.613825)
        ,LatLng(35.884895, 128.614136)
        ,LatLng(35.885571, 128.615380)
        ,LatLng(35.888731, 128.616920)
        ,LatLng(35.890126, 128.616177)
        ,LatLng(35.894846, 128.613726)
        ,LatLng(35.895387, 128.614280)
        ,LatLng(35.895725, 128.613741)
        ,LatLng(35.895854, 128.613119)
        ,LatLng(35.892524, 128.609283)
        ,LatLng(35.889688, 128.603978)
    )






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
        //GoogleMap.uiSettings().setScrollGesturesEnabled(false)

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
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18F))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(KNU))
        //mMap.addMarker(MarkerOptions().position(KNU).title("Marker in KNU_IT"))

        googleMap.addPolygon(PolygonOptions()
            .add(
                BorderArray[0],
                BorderArray[1],
                BorderArray[2],
                BorderArray[3],
                BorderArray[4],
                BorderArray[5],
                BorderArray[6],
                BorderArray[7],
                BorderArray[8],
                BorderArray[9],
                BorderArray[10],
                BorderArray[11],
                BorderArray[12],
                BorderArray[13],
                BorderArray[14],
                BorderArray[15],
                BorderArray[16],
                BorderArray[17],
                BorderArray[18],
                BorderArray[19],
                BorderArray[20],
                BorderArray[21],
                BorderArray[22],
                BorderArray[23]
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F))

                showcircle()
                ShowLetter()
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

    fun ShowLetter() : Unit{
        var LetterCoordinate=CurrentCoordinate
        var marker : Marker
        if(CheckInRadius(LetterCoordinate)){
            marker=mMap.addMarker(MarkerOptions()
                .position(LetterCoordinate)
                .title("5/7")
                .snippet("3:15"))
            marker.showInfoWindow()
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.letter))
        }
    }

    val latlngtometers = 111139
    private fun CheckInRadius(LetterCoordinate: LatLng) : Boolean{
        var Lat=(LetterCoordinate.latitude-CurrentCoordinate.latitude)*latlngtometers
        var Lng=(LetterCoordinate.longitude-CurrentCoordinate.longitude)*latlngtometers
        return kotlin.math.sqrt(Lat * Lat + Lng * Lng) <=Radius
    }
    fun showcircle() : Unit{
        try {
            mapCircle.remove()
        }
        catch(e: Exception){
            println("hi1")
        }
        try {
            mapDot.remove()
        }
        catch(e: Exception){
            println("hi2")
        }
        mapCircle= mMap.addCircle(
            CircleOptions()
                .center(CurrentCoordinate)
                .radius(Radius)
                .strokeWidth(2f)
                .strokeColor(Color.argb(200,0,255, 255))
                .fillColor(Color.argb(20,0,255, 255))
        )
        mapDot= mMap.addCircle(
            CircleOptions()
                .center(CurrentCoordinate)
                .radius(3.0)
                .strokeWidth(2f)
                .strokeColor(Color.argb(20,30,255, 255))
                .fillColor(Color.argb(70,30,255, 255))
        )
    }

}