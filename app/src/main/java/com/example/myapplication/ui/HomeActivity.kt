package com.example.myapplication.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.FavouriteActivity
import com.example.myapplication.R
import com.example.myapplication.SearchActivity
import com.example.myapplication.auth.ViewmodelFactory
import com.example.myapplication.auth.WeatherViewModel
import com.example.myapplication.databinding.ActivityMain2Binding

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions
import kotlinx.android.synthetic.main.activity_main2.*
import com.example.myapplication.repositories.WeatherRepository
import com.example.myapplication.response.MyWeatherApi
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private var weatherViewModel :WeatherViewModel?=null
     lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main2)


        val api = MyWeatherApi()
        val repository=WeatherRepository(api)
        val factory=ViewmodelFactory(repository)
        weatherViewModel= ViewModelProviders.of(this,factory).get(WeatherViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main2
        )
        binding!!.viewmodel=weatherViewModel





        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(toolbar)
        supportActionBar?.setLogo(R.drawable.logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mitem1 ->startActivity(Intent(this,
                    HomeActivity::class.java))
                R.id.mitem2 -> startActivity(Intent(this,
                    FavouriteActivity::class.java))
                R.id.mitem3 -> startActivity(Intent(this,
                    SearchActivity::class.java))
            }
            true
        }
        //val rview = findViewById<View>(R.id.rview) as RecyclerView
        //val picture = intArrayOf(
          //  R.drawable.icon_temperature,
            //R.drawable.icon_precipitation,
            //R.drawable.icon_humidity,
            //R.drawable.icon_visibility,
            //R.drawable.icon_wind
        //)
       // val name = arrayOf("Min-Max", "Precipitation", "Humidity", "Visibility", "Wind")
            //var info = arrayOf("22-33", "0%", "47%", "6 km/h", "15km")

        //val lManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        //rview.layoutManager = lManager

       // rview.adapter = MyAdapter(picture, name, info,this)
        val textView : TextView = findViewById(R.id.tv_datetime)
        val simpleDateFormat = SimpleDateFormat("E, dd MMM yyy hh:mm a")
        val currentDateAndTime:String= simpleDateFormat.format(Date())
        textView.text =currentDateAndTime

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation(tv_location)
        }
        else {
            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)

        }

        tv_location.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var place = tv_location.text.toString()
                weatherViewModel!!.weatherdeatails(place)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
           weatherViewModel!!.response.observe(this, androidx.lifecycle.Observer { tv_temp.text = it.temperature
            tv_minmax.text = it.min_max
            tv_humidity.text = it.humidity
            tvPrecipitationData.text = it.precipitation
            tv_visibility.text = it.visibility
            tv_wind.text = it.wind
            val id = it.id
            val desc = it.description
            if (id < 300) {
                discription.text = "Thunderstorm"
            }
            else if (id > 300 && id < 600) {
               discription.text = "Rain"
            } else if (id > 600 && id <= 800) {
                discription.text = "Mostly Sunny"
            } else if (id > 800) {
                if (desc == "few clouds") {
                    discription.text = "Partly Cloudy"
                } else {
                   discription.text = "Mostly Cloudy"
                }
            }

            val text = discription.text
            if (text == "Thunderstorm") {
                iv_weathericon.setBackgroundResource(R.drawable.icon_thunderstorm_big)
            } else if (text == "Rain") {
               iv_weathericon.setBackgroundResource(R.drawable.icon_rain_big)
            } else if (text == "Mostly Sunny") {
                iv_weathericon.setBackgroundResource(R.drawable.icon_mostly_sunny_small)
            } else if (text == "Partly Cloudy") {
                iv_weathericon.setBackgroundResource(R.drawable.icon_partially_cloudy_big)
            } else if (text == "Mostly Cloudy") {
                iv_weathericon.setBackgroundResource(R.drawable.icon_mostly_cloudy_big)
            }
        })}
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.search_bar, menu)
    return super.onCreateOptionsMenu(menu)
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
        R.id.homesearchbar -> {
            val intent = PlaceAutocomplete.IntentBuilder()
                .accessToken("pk.eyJ1Ijoic3VzaG1pdGhhc2hldDEyMyIsImEiOiJja2ZjYjNndHkxZTZjMnpxc2k4bndnaTJ2In0.YtNvTh7-VtuL_0lJ1X-5Kg")
                .placeOptions(PlaceOptions.builder().build(PlaceOptions.MODE_CARDS))
                .build(this)

            startActivityForResult(intent,1)
        }
    }
    return true
    // return super.onOptionsItemSelected(item)
}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(resultCode == Activity.RESULT_OK && requestCode == 1) {
        var feature = PlaceAutocomplete.getPlace(data)
        //PlaceAutocomplete.getPlace(data)
        var searchValue =  feature.text()!!
        Log.d("LogReport", "" + searchValue)
    }
}

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getCurrentLocation(tv_location :TextView) {
    val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(){
            val location:Location=it.getResult()
            val geocoder: Geocoder = Geocoder(this)
            val address: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            tv_location .text = "${address.get(0).subAdminArea}, ${address.get(0).adminArea}"
        }
    }



}
