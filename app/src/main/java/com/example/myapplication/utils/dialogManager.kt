package com.example.myapplication.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R

object dialogManager {
    fun showDialog(contex: Context, mId: Int, listener: Listener){
        val builder = AlertDialog.Builder(contex)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.alert)
        builder.setMessage(mId)
        builder.setPositiveButton(R.string.reset){_,_->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton(R.string.back){_,_->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}