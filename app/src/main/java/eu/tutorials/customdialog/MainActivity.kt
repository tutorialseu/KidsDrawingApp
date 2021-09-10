package eu.tutorials.customdialog

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

        /**
         * This method is autocreated by Android when the Activity Class is created.
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            //This call the parent constructor
            super.onCreate(savedInstanceState)

            // This is used to align the xml view to this class
            setContentView(R.layout.activity_main)

            /**
             * Here we have handled onclick of ImageButton. And we have used SnackBar to notify.
             */
            val imageButton:ImageButton = findViewById(R.id.image_button)
                imageButton.setOnClickListener { view ->
                Snackbar.make(view, "You have clicked image button.", Snackbar.LENGTH_LONG).show()
            }

            /**
             * Here we have handled onClick of Alert Dialog Button.
             */
            val btnAlertDialog: Button = findViewById(R.id.btn_alert_dialog)
            btnAlertDialog.setOnClickListener {

                //Launch Alert Dialog
                alertDialogFunction()
            }

            /**
             * Here we have handled onClick of Custom Dialog Button.
             */
            val btnCustomDialog:Button = findViewById(R.id.btn_custom_dialog)
            btnCustomDialog.setOnClickListener {

                //Launch Custom Dialog
                customDialogFunction()
            }

            /**
             * Here we have handled onClick of Custom Progress Dialog Button.
             */
            val btnCustomProgress:Button = findViewById(R.id. btn_custom_progress_dialog)
            btnCustomProgress.setOnClickListener {

                //Launch Custom Progress Dialog
                customProgressDialogFunction()
            }
    }

    /**
     * Method is used to show the Alert Dialog.
     */
    private fun alertDialogFunction() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Alert")
        //set message for alert dialog
        builder.setMessage("This is Alert Dialog. Which is used to show alerts in our app.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(
                applicationContext,
                "clicked cancel\n operation cancel",
                Toast.LENGTH_LONG
            ).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }


    /**
     * Method is used to show the Custom Dialog.
     */
    private fun customDialogFunction() {
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
    The resource will be inflated, adding all top-level views to the screen.*/
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss() // Dialog will be dismissed
        }
        customDialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        customDialog.show()
    }

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun customProgressDialogFunction() {
        val customProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog.show()
    }
}