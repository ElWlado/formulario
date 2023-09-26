package wladimir.andrades.formulario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAttribute: EditText
    private lateinit var etRarity: EditText
    private lateinit var etDamage: EditText
    private lateinit var etUses: EditText
    private lateinit var tbMagic: ToggleButton
    private lateinit var dtDateObtained: DatePicker
    private lateinit var txtMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etAttribute = findViewById(R.id.etAttribute)
        etRarity = findViewById(R.id.etRarity)
        etDamage = findViewById(R.id.etDamage)
        etUses = findViewById(R.id.etUses)
        tbMagic = findViewById(R.id.tbMagic)
        dtDateObtained = findViewById(R.id.cvDateObtained)
        txtMessage = findViewById(R.id.txtMessage)
    }

    fun changeDisplay(view: View){
        if (!validateTexts()){
            intent = Intent(this, SecondActivity::class.java)

            putInfo(intent)
            startActivity(intent)
        }
        else txtMessage.text = "Debe ingresar todos los datos."
    }

    private fun createPerson(): Armament{
        val armament = Armament()

        armament.setName(this.etName.text.toString())
        armament.setAttribute(this.etAttribute.text.toString())
        armament.setRarity(this.etRarity.text.toString())
        armament.setDamage(this.etDamage.text.toString().toInt())
        armament.setUses(this.etUses.text.toString().toInt())
        armament.setMagic(this.tbMagic.isChecked)
        armament.setDateObtained(getDP())

        return armament
    }

    private fun putInfo(intent: Intent){
        intent.putExtra("Person", createPerson())
    }

    private fun validateTexts(): Boolean{
        return this.etName.text.isNullOrBlank() || this.etAttribute.text.isNullOrBlank() || this.etRarity.text.isNullOrBlank() ||
                this.etDamage.text.isNullOrBlank() || this.etUses.text.isNullOrBlank()
    }

    private fun getDP(): Date{
        return Date(dtDateObtained.year-1900,dtDateObtained.month,dtDateObtained.dayOfMonth)
    }
}