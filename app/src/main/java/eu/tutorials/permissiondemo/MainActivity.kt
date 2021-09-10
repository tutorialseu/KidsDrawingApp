package eu.tutorials.permissiondemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    /**
    Todo 1: This time we creat the Activity result launcher of type Array<String>
     */
    private val cameraAndLocationResultLauncher:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){permissions->
            /**
            Here it returns a Map of permission name as key with boolean as value
            Todo 2: We loop through the map to get the value we need which is the boolean
            value
             */
            permissions.entries.forEach{
                val permissionName =it.key
                val isGranted =it.value
//Todo 3: if it is granted then we show its granted
                if (isGranted) {
                    Toast.makeText(this, "Permission granted for location and camera.", Toast.LENGTH_LONG)
                        .show()
                } else {
//Todo 4: if not then we show a denied message
                    Toast.makeText(this, "Permission denied for location and camera.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)

        btnCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA
                )
            ) {
                /**
                If the permission has been denied do not ask for the permission again, only
                let the user know that the feature that requires the camera will not be available because the permission is denied
                 */
               showRationaleDialog(" Permission Demo requires camera access",
                   "Camera cannot be used because Camera access is denied")
            } else {
                //Todo: We launch array of permissions that we need
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION)
                )

            }
        }

    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

}