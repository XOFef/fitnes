package com.example.myapplication.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.R

object FragmentManager {
    var currentFragment: Fragment? = null

    @SuppressLint("CommitTransaction")
    fun setFragment(newFragment: Fragment, activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }


}