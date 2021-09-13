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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute:Button = findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            lifecycleScope.launch {
                execute("Task executed successfully.")
            }
        }

    }


    private suspend fun execute(result:String){
        withContext(Dispatchers.IO) {
            for (i in 1..1000000) {
                Log.e("delay : ", "" + i)
            }
            runOnUiThread {
                Toast.makeText(
                    this@MainActivity, result,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }




}