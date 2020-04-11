package com.elabda3.tripstask.base

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.elabda3.tripstask.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun Activity.myToast(msg: String) {
    var dialog: AlertDialog? = null
    var toastText: TextView
    var toastBtn: TextView


    var view = LayoutInflater.from(this).inflate(R.layout.toast_dialog, null)
    toastText = view.findViewById(R.id.toastMsg)
    toastBtn = view.findViewById(R.id.toastBtn)


    toastText?.text = msg

    toastBtn?.setOnClickListener {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }


    dialog = AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(false)
        .show()

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


}

fun Activity.myToastWithClick(msg: String, toastClick: (AlertDialog) -> Unit) {
    var dialog: AlertDialog?=null
    var toastText: TextView
    var toastBtn: TextView

        val view = LayoutInflater.from(this).inflate(R.layout.toast_dialog, null)
        toastText = view.findViewById(R.id.toastMsg)
        toastBtn = view.findViewById(R.id.toastBtn)



    toastText?.text = msg

    toastBtn?.setOnClickListener {
        if (dialog?.isShowing == true) {
            toastClick.invoke(dialog!!)
        }
    }


        dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

}

var loadingDialog: AlertDialog? = null

fun Activity.loadingDialog(show: Boolean) {

    if (loadingDialog?.ownerActivity != this) {
        loadingDialog = AlertDialog.Builder(this)
            .setView(R.layout.loading)
            .setCancelable(false)
            .create()

        loadingDialog?.ownerActivity = this
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    if (show && loadingDialog?.isShowing == false) {
        loadingDialog?.show()
    } else if (!show) {
        loadingDialog?.dismiss()
    }

}


fun getMarkerIcon(color: Int): BitmapDescriptor? {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    return BitmapDescriptorFactory.defaultMarker(hsv[0])
}


suspend fun <T : Any> safeCall(call: suspend () -> NetworkResult<T>): NetworkResult<T> = try {
    call.invoke()
} catch (e: Exception) {
    e.printStackTrace()
    NetworkResult.Error(e)
}



