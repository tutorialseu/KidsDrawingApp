package eu.tutorials.backgroundexecution

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    //Create a dialog variable
    var customProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute:Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            lifecycleScope.launch {
                execute("Task executed successfully.")
            }
        }

    }


    private suspend fun execute(result:String){
        //TODO(You can code here what you wants to execute in background execution without freezing the UI
        withContext(Dispatchers.IO) {
            // This is just a for loop which is executed for 1000000 times.
            for (i in 1..1000000) {
                Log.e("delay : ", "" + i)
            }
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(
                    this@MainActivity, result,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }


    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }



}