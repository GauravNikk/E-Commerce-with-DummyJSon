package gaurav.iosdialog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        IOSDialog(this)
            .create(
                title = "Alert",
                message = "This is an iOS-style dialog.",
                onCancelClick = { Toast.makeText(this, "Cancel clicked", Toast.LENGTH_SHORT).show() },
                onConfirmClick = { Toast.makeText(this, "Confirm clicked", Toast.LENGTH_SHORT).show() }
            )
            .show()
    }
}