package wladimir.andrades.formulario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class FormActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAttribute: EditText
    private lateinit var etRarity: EditText
    private lateinit var etDamage: EditText
    private lateinit var etUses: EditText
    private lateinit var tbMagic: ToggleButton
    private lateinit var dpDateObtained: DatePicker
    private lateinit var txtMessage: TextView

    private var edit: Boolean = false
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        etName = findViewById(R.id.etName)
        etAttribute = findViewById(R.id.etAttribute)
        etRarity = findViewById(R.id.etRarity)
        etDamage = findViewById(R.id.etDamage)
        etUses = findViewById(R.id.etUses)
        tbMagic = findViewById(R.id.tbMagic)
        dpDateObtained = findViewById(R.id.dpDateObtained)
        txtMessage = findViewById(R.id.txtMessage)

        loadArmament()
    }

    fun clear(view: View){
        etName.text.clear()
        etAttribute.text.clear()
        etRarity.text.clear()
        etDamage.text.clear()
        etUses.text.clear()
        tbMagic.isChecked = false
    }

    fun changeDisplay(view: View){
        if (!validateTexts()){
            intent = Intent(this, SecondActivity::class.java)

            putInfo(intent)
            startActivity(intent)
        }
        else txtMessage.text = "Debe ingresar todos los datos."
    }

    private fun createArmament(): Armament{
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
        intent.putExtra("Armament", createArmament())
        intent.putExtra("Edit", edit)
        intent.putExtra("Id", id)
    }

    private fun validateTexts(): Boolean{
        return this.etName.text.isNullOrBlank() || this.etAttribute.text.isNullOrBlank() || this.etRarity.text.isNullOrBlank() ||
                this.etDamage.text.isNullOrBlank() || this.etUses.text.isNullOrBlank()
    }

    private fun getDP(): Date {
        return Date(dpDateObtained.year-1900,dpDateObtained.month,dpDateObtained.dayOfMonth)
    }

    private fun loadArmament(){
        if (intent.getSerializableExtra("Armament") != null){
            val armament: Armament = intent.getSerializableExtra("Armament") as Armament

            etName.text = Editable.Factory.getInstance().newEditable(armament.getName())
            etAttribute.text = Editable.Factory.getInstance().newEditable(armament.getAttribute())
            etRarity.text = Editable.Factory.getInstance().newEditable(armament.getRarity())
            etDamage.text = Editable.Factory.getInstance().newEditable(armament.getDamage().toString())
            etUses.text = Editable.Factory.getInstance().newEditable(armament.getUses().toString())
            tbMagic.isChecked = armament.getMagic()

            val date = formatDate(armament.getDateObtained())
            dpDateObtained.updateDate(date[0], date[1], date[2])

            id = intent.getIntExtra("Id", 0)
            edit = true
        }
    }

    private fun formatDate(date: Date): Array<Int>{
        var dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = SimpleDateFormat("dd/MM/yyyy").parse(dateFormat.format(date))
        val calendar = Calendar.getInstance()

        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return arrayOf(year, month, day)
    }
}