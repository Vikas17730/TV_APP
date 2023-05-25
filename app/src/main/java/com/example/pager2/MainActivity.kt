package com.example.pager2
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.viewpager.widget.ViewPager
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var viewPager: ViewPager
//    private lateinit var pagerAdapter: WebViewPagerAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        viewPager = findViewById(R.id.viewPager)
//        pagerAdapter = WebViewPagerAdapter(supportFragmentManager, this) // Pass the activity instance
//        viewPager.adapter = pagerAdapter
//
//        getUrlsFromApi()
//    }
//
//    private fun getUrlsFromApi() {
//        val apiService = ApiClient.create()
//        val call = apiService.getUrls()
//
//        call.enqueue(object : Callback<List<String?>> {
//            override fun onResponse(call: Call<List<String?>>, response: Response<List<String?>>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val fetchedUrls = response.body() ?: emptyList()
//                    pagerAdapter.addUrls(fetchedUrls)
//                } else {
//                    // Handle API error
//                }
//            }
//
//            override fun onFailure(call: Call<List<String?>>, t: Throwable) {
//                // Handle network or API call failure
//            }
//        })
//    }
//}
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var webViewPagerAdapter: WebViewPagerAdapter
    private lateinit var apiService: ApiService
    private var currentPage = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        webViewPagerAdapter = WebViewPagerAdapter(this)
        viewPager.adapter = webViewPagerAdapter

        apiService = ApiClient.create()
        scheduleApiCall(9, 30)
        fetchUrls()

        startAutoSwitching(10000)
    }
    private fun scheduleApiCall(hours: Long, minutes: Long) {
        val intervalMillis = (hours * 60 + minutes) * 60 * 1000

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    // Make the API call
                    fetchUrls()
                }
            }
        }, intervalMillis, intervalMillis)
    }

    private fun fetchUrls() {
        apiService.getUrls().enqueue(object : Callback<List<String?>> {
            override fun onResponse(call: Call<List<String?>>, response: Response<List<String?>>) {
                if (response.isSuccessful) {
                    val urls = response.body()
                    urls?.let {
                        webViewPagerAdapter.setUrls(urls)
                    }
                } else {
                    // Handle API call error
                    Log.d("mess","Api error")
                }
            }

            override fun onFailure(call: Call<List<String?>>, t: Throwable) {
                // Handle API call failure
                Log.d("mess", "Failed ${t.message}")
            }
        })
    }
    private fun startAutoSwitching(delay: Long) {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val itemCount = webViewPagerAdapter.itemCount
                    if (itemCount > 1) {
                        currentPage = (currentPage + 1) % itemCount
                        viewPager.setCurrentItem(currentPage, true)
                    }
                }
            }
        }, delay, delay)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer when the activity is destroyed to avoid leaks
        timer?.cancel()
        timer = null
    }
}
