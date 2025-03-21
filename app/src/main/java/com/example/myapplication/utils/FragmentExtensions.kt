package com.example.myapplication.utils

import android.os.Build
import androidx.fragment.app.Fragment
import com.example.myapplication.db.DayModel

fun Fragment.getDayFromArguments(): DayModel? {
    return arguments.let { bundle ->
        if(Build.VERSION.SDK_INT >= 33){
            bundle?.getSerializable("day", DayModel::class.java)
        }
        else{
            bundle?.getSerializable("day") as DayModel
        }
    }
}