package wladimir.andrades.formulario

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class SecondActivity : AppCompatActivity() {
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

        val person: Person = intent.getSerializableExtra("Person") as Person

        txtName.text = person.getName()
        txtAttribute.text = person.getAttribute()
        txtRarity.text = person.getRarity()
        txtDamage.text = person.getDamage().toString()
        txtUses.text = person.getUses().toString()
        txtDateObtained.text = formatDate(person.getDateObtained())
        if (person.getMagic()) txtMagic.text = "Sí"
        else txtMagic.text = "No"
    }

    private fun formatDate(date: Date): String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(date)
    }

}