package wladimir.andrades.formulario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeDisplayForm(view: View){
        intent = Intent(this, FormActivity::class.java)
        startActivity(intent)
    }

    fun changeDisplayGet(view: View){
        intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }
}