package com.example.kliv

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import com.example.kliv.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    // 허용받을 권한 목록
    val permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, // GPS와 네트워크를 이용하여 단말기 위치 식별
        Manifest.permission.ACCESS_COARSE_LOCATION // 네트워크를 이용하여 단말기 위치 식별
    )
    lateinit var binding : ActivityMapBinding
    lateinit var manager : LocationManager // GPS 정보 관리
    lateinit var locationListener: LocationListener // 위치 측정 성공 시 호출될 메서드가 구현된 메서드, 측정 한 번하고 연결 끊을 예정
    lateinit var  googleMap : GoogleMap // 구글맵 제어
    var myMarker : Marker? = null

    var nearby_lat = ArrayList<Double>()
    var nearby_lng = ArrayList<Double>()
    var nearby_name = ArrayList<String>()
    var nearby_vicinity = ArrayList<String>()
    var nearby_marker_list = ArrayList<Marker>()

    val dialogData = "park"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("공병 반환 위치")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 권한 요청
        requestPermissions(permission_list, 0)

        // 맵의 상태가 변경되면 호출될 메서드가 구현되어 있는 곳을 등록
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // 지도 준비가 완료되면 호출되는 메서드
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        val latLng = LatLng(35.88,128.61)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        //구글 지도의 옵션 설정을 위해 권한을 확인
        val a1 = Manifest.permission.ACCESS_FINE_LOCATION
        val a2 = Manifest.permission.ACCESS_COARSE_LOCATION
        if(ActivityCompat.checkSelfPermission(this, a1) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, a2) == PackageManager.PERMISSION_GRANTED) {
            // 확대 축소 버튼
            googleMap.uiSettings.isZoomControlsEnabled = true

            // 현재 위치 표시
            googleMap.isMyLocationEnabled = true // 이걸 쓰거나 마커를 쓰거나 둘 중 하나만 사용

            // 현재 위치 버튼 제거
            googleMap.uiSettings.isMyLocationButtonEnabled = false

            // 맵 타입
            // googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        getMyLocation()
    }

    // 현재 위치를 측정하는 메서드
    fun getMyLocation() {
        // 위치 정보를 관리하는 매니저를 추출
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 권한 확인
        val a1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        val a2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        if (a1 && a2) {
            return
        }

        // 저장되어 있는 위치값이 있으면 가져온다.
        val location1 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER) // GPS를 통해 저장된
        val location2 = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) // NETWORK를 통해 저장된

        // 새로운 위치 측정을 요청
        locationListener = LocationListener {
            setUserLocation(it)
        }

        if (location1 != null) {
            setUserLocation(location1)
        }
        else if (location2 != null) {
            setUserLocation(location2)
        }

        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) { // GPS 이용 가능 환경일 경우
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0f, locationListener) // locationListner -> setUserLocation()
        }
        else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        }

    }

    // 위치 값을 받아 지도를 이동시키는 메서드
    fun setUserLocation(location : Location) {
        //위치 측정을 중단한다.
        manager.removeUpdates(locationListener)

        // 위도와 경도값을 관리하는 객체
        val loc1 = LatLng(location.latitude, location.longitude)

        // 지도를 이동시키기 위한 객체 (지도 위에 카메라가 있다고 생각)
        val loc2 = CameraUpdateFactory.newLatLngZoom(loc1, 15f)

        // 이동한다
        googleMap.animateCamera(loc2)

        /*--------------------------------------------------
        // 마커가 표시가 되어있다면
        if (myMarker != null) {
            myMarker?.remove()
        }

        // 현재 위치에 마커를 표시
        val myMarkerOptions = MarkerOptions()
        myMarkerOptions.position(loc1)
        myMarker = googleMap.addMarker(myMarkerOptions)
        --------------------------------------------------*/
    }

    // 메뉴 띄우기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // 메뉴 클릭 시 호출되는 메서드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.main_menu_location -> {
                getMyLocation()
            }
            R.id.main_menu_place -> {
                // 리스트를 초기화한다.
                nearby_lat.clear()
                nearby_lng.clear()
                nearby_name.clear()
                nearby_vicinity.clear()

                for (m in nearby_marker_list) {
                    m.remove() // 마커 지도상에서 제거
                }
                nearby_marker_list.clear() // 초기화

                getNearbyPlaceData(dialogData)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getNearbyPlaceData(type : String) {
        // 위치 정보를 관리하는 매니저를 추출
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 권한 확인
        val a1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        val a2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        if (a1 && a2) {
            return
        }

        // 저장되어 있는 위치값이 있으면 가져온다.
        val location1 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER) // GPS를 통해 저장된
        val location2 = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) // NETWORK를 통해 저장된

        val GOOGLE_MAPS_PLACES_API_KEY = BuildConfig.GOOGLE_MAPS_PLACES_API_KEY // API KEY 값 안전하게 사용하기

        thread {
            var site = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
            if (location1 != null) {
                site += "?location=${location1?.latitude},${location1?.longitude}"
            }
            else {
                site += "?location=${location2?.latitude},${location2?.longitude}"
            }
            site += "&radius=1000&type=${type}&language=ko"
            site += "&key=${GOOGLE_MAPS_PLACES_API_KEY}"

            Log.d("test", site)

            val url = URL(site)
            val conn = url.openConnection() as HttpURLConnection

            // 데이터를 읽어온다.
            val  isr = InputStreamReader(conn.inputStream, "UTF-8")
            val br = BufferedReader(isr)

            var str : String ? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()
                if (str != null) {
                    buf.append(str)
                }
            }
            while (str != null)

            val data = buf.toString()
//            Log.d("test", data)

            // JSON 객체 생성
            val root = JSONObject(data)

            if (root.getString("status") == "OK") {
                val results = root.getJSONArray("results")

                for (i in 0 until results.length()) {
                    val results_item = results.getJSONObject(i)

                    val geometry = results_item.getJSONObject("geometry")
                    val location = geometry.getJSONObject("location")
                    val lat = location.getDouble("lat")
                    val lng = location.getDouble("lng")
                    val name = results_item.getString("name")
                    val vicinity = results_item.getString("vicinity")

//                    Log.d("test", "$lat")
//                    Log.d("test", "$lng")
//                    Log.d("test", "$name")
//                    Log.d("test", "$vicinity")
//                    Log.d("test", "----------------------")

                    nearby_lat.add(lat)
                    nearby_lng.add(lng)
                    nearby_name.add(name)
                    nearby_vicinity.add(vicinity)

                    runOnUiThread {
                        for (i in 0 until nearby_lat.size) {
                            val loc = LatLng(nearby_lat[i], nearby_lng[i])
                            var placeMarkerOptions = MarkerOptions()
                            placeMarkerOptions.position(loc)
                            placeMarkerOptions.title(nearby_name[i])
                            placeMarkerOptions.snippet(nearby_vicinity[i])

                            val placeMarker = googleMap.addMarker(placeMarkerOptions)
                            nearby_marker_list.add(placeMarker!!)
                        }
                    }
                }
            }
        }

    }
}