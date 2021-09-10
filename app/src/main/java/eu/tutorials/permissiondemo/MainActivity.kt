package eu.tutorials.permissiondemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    //Todo 1: This time we creat the Activity result launcher of type Array<String>
    private val cameraAndLocationResultLauncher:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){permissions->
            /**
            Here it returns a Map of permission name as key with boolean as value
            Todo 2: We loop through the map to get the value we need which is the boolean
            value
             */
               Log.d("MainActivity","Permissions $permissions")
                permissions.entries.forEach {
                    val permissionName = it.key
                    //Todo 3: if it is granted then we show its granted
                    val isGranted = it.value
                    if (isGranted) {
                        //check the permission name and perform the specific operation
                        if ( permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission granted for location",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }else{
                            //check the permission name and perform the specific operation
                            Toast.makeText(
                                this,
                                "Permission granted for Camera",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    } else {
                        if ( permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission denied for location",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }else{
                            Toast.makeText(
                                this,
                                "Permission denied for Camera",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
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
               showRationaleDialog(" Permission Demo requires camera access",
                   "Camera cannot be used because Camera access is denied")
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
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