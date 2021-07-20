package com.example.letter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.letter.databinding.ActivityMapsBinding
import com.example.letter.databinding.MainBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var name:String//로그인 화면에서 가져옴
    private lateinit var number:String//로그인 화면에서 가져옴
    private lateinit var major:String//로그인 화면에서 가져옴
    private lateinit var tact:String//로그인 화면에서 가져옴

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

        // Add a marker in Sydney and move the camera
        val KNU = LatLng(35.888166756477105, 128.61056411139742)
        mMap.addMarker(MarkerOptions().position(KNU).title("Marker in KNU_IT"))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(KNU))

        binding.Write.setOnClickListener{
            val ToSendMessage= Intent(this,SendMessageActivity::class.java)
            startActivity(ToSendMessage)
        }

    }

}