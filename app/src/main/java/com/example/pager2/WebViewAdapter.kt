package com.example.pager2
//
//import android.app.Activity
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import androidx.viewpager2.adapter.FragmentStateAdapter
//
//class WebViewPagerAdapter(fragmentActivity: FragmentActivity,private val activity: Activity) : FragmentStateAdapter(fragmentActivity) {
//    private val urls: MutableList<String?> = mutableListOf()
//
////    override fun getCount(): Int {
////        return urls.size
////    }
////
////    override fun getItem(position: Int): Fragment {
////        val url = urls[position] ?: ""
////        return WebViewFragment.newInstance(url)
////    }
//
//    fun addUrls(newUrls: List<String?>) {
//        urls.addAll(newUrls)
//        Log.d("mess","$urls")
//            notifyDataSetChanged()
//        }
//
//    override fun getItemCount(): Int {
//        return urls.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        val url = urls[position] ?: ""
//        return WebViewFragment.newInstance(url)
//    }
//}
//
//
//
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WebViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val urls: MutableList<String> = mutableListOf()

    fun setUrls(urls: List<String?>) {
        this.urls.clear()
        AddAll(urls)
        Log.d("mess", "$urls")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun createFragment(position: Int): Fragment {
        val url = urls[position]
        return WebViewFragment.newInstance(url)
    }
    fun AddAll(urls: List<String?>){
        for (x in urls) {
            if (x != null) {
                this.urls.add(x)
            }
        }
    }
}

