package com.gaurav.testapp.util

import android.content.Context
import gaurav.iosdialog.IOSDialog

class  Dlghelper{

    companion object {

        fun showmessg(context: Context, title: String, message: String) {
            IOSDialog(context)
                .create(
                    title = title,
                    message = message)
                .show()
        }
    }
}