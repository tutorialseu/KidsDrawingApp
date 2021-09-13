package eu.tutorials.backgroundexecution

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute:Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
                execute("Task executed successfully.")
        }

    }


    private fun execute(result:String){
            for (i in 1..1000000) {
                Log.e("delay : ", "" + i)
            }
        Toast.makeText(
            this@MainActivity, result,
            Toast.LENGTH_SHORT
        ).show()
    }




}