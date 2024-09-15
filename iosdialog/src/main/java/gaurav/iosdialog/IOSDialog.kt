package gaurav.iosdialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class IOSDialog(private val context: Context) {

    private lateinit var dialog: Dialog

    fun create(
        title: String,
        message: String,
        isConfirmationDialog: Boolean = false,
        onCancelClick: (() -> Unit)? = null,
        onConfirmClick: (() -> Unit)? = null
    ): IOSDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_ios_style, null)

        dialog = AlertDialog.Builder(context, R.style.iOSDialogTheme)
            .setView(view)
            .create()

        view.findViewById<TextView>(R.id.iosDialogTitle).text = title
        view.findViewById<TextView>(R.id.iosDialogMessage).text = message

        val cancelButton = view.findViewById<Button>(R.id.iosDialogCancelButton)
        val confirmButton = view.findViewById<Button>(R.id.iosDialogConfirmButton)
        val divider = view.findViewById<View>(R.id.iosDevider)

        if (isConfirmationDialog) {
            cancelButton.text = "Cancel"
            confirmButton.visibility = View.VISIBLE
            divider.visibility = View.VISIBLE

            confirmButton.setOnClickListener {
                dialog.dismiss()
                onConfirmClick?.invoke()
            }
        } else {
            cancelButton.text = "Close"
            confirmButton.visibility = View.GONE
            divider.visibility = View.GONE
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
            onCancelClick?.invoke()
        }

        return this
    }

    fun show() {
        dialog.show()
    }
}
