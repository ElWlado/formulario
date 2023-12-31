package wladimir.andrades.formulario

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class SecondActivity : AppCompatActivity() {
    private var db = myDbHelper(this)

    private var name: String = ""
    private var attribute: String = ""
    private var rarity: String = ""
    private var damage: Int = 0
    private var uses: Int = 0
    private var date: String = ""
    private var magic: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val txtName : TextView = findViewById(R.id.txtName)
        val txtAttribute : TextView = findViewById(R.id.txtAttribute)
        val txtRarity : TextView = findViewById(R.id.txtRarity)
        val txtDamage : TextView = findViewById(R.id.txtDamage)
        val txtUses : TextView = findViewById(R.id.txtUses)
        val txtDateObtained : TextView = findViewById(R.id.txtDateObtained)
        val txtMagic : TextView = findViewById(R.id.txtMagic)
        val btnOption : TextView = findViewById(R.id.btnOption)

        val edit = intent.getBooleanExtra("Edit", false)
        val armament: Armament = intent.getSerializableExtra("Armament") as Armament

        name = armament.getName()
        attribute = armament.getAttribute()
        rarity = armament.getRarity()
        damage = armament.getDamage()
        uses = armament.getUses()
        date = formatDate(armament.getDateObtained())
        magic = armament.getMagic()

        txtName.text = name
        txtAttribute.text = attribute
        txtRarity.text = rarity
        txtDamage.text = damage.toString()
        txtUses.text = uses.toString()
        txtDateObtained.text = date
        if (magic) txtMagic.text = "Sí"
        else txtMagic.text = "No"

        if (edit){
            btnOption.text = "Actualizar"
            btnOption.setOnClickListener { editValues() }
        } else btnOption.setOnClickListener { saveValues() }
    }

    fun back(view: View){
        onBackPressed()
    }

    private fun saveValues() {
        db.addArmament(name, attribute, rarity, damage, uses, date, magic)
        changeDisplay()
    }

    private fun editValues() {
        val id: Int = intent.getIntExtra("Id", 0)

        db.updateArmament(id, name, attribute, rarity, damage, uses, date, magic)
        changeDisplay()
    }

    private fun formatDate(date: Date): String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(date)
    }

    private fun changeDisplay(){
        intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

}