package wladimir.andrades.formulario

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date

class ThirdActivity : AppCompatActivity() {
    private var db = myDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        getDb()
    }

    fun changeDisplayMain(view: View){
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun edit(id: Int){
        intent = Intent(this, FormActivity::class.java)
        val dbRead: SQLiteDatabase = this.db.readableDatabase
        val cursor = dbRead.rawQuery("SELECT * FROM Armament WHERE _id=?", arrayOf(id.toString()))
        val armament = Armament()

        if (cursor.moveToFirst()){
            do{
                armament.setName(cursor.getString(1))
                armament.setAttribute(cursor.getString(2))
                armament.setRarity(cursor.getString(3))
                armament.setDamage(cursor.getString(4).toInt())
                armament.setUses(cursor.getString(5).toInt())

                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date: Date = dateFormat.parse(cursor.getString(6))
                armament.setDateObtained(date)

                armament.setMagic((cursor.getString(7).toInt() == 1))
            } while (cursor.moveToNext())
        }

        intent.putExtra("Armament", armament)
        intent.putExtra("Id", id)
        startActivity(intent)
    }

    private fun delete(id: Int){
        db.deleteArmament(id)
        Toast.makeText(this, "Armamento Eliminado.", Toast.LENGTH_SHORT).show()

        startActivity(intent)
    }

    private fun generateModal(id: Int){
        val density = resources.displayMetrics.density
        val marginInPixels = (20 * density).toInt()

        val mainLinearLayout = LinearLayout(this)
        mainLinearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        mainLinearLayout.gravity = Gravity.CENTER
        mainLinearLayout.orientation = LinearLayout.VERTICAL

        val internalLinearLayout = LinearLayout(this)
        internalLinearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        internalLinearLayout.setPadding(marginInPixels, marginInPixels, marginInPixels, marginInPixels)
        internalLinearLayout.orientation = LinearLayout.VERTICAL
        internalLinearLayout.gravity = Gravity.CENTER
        internalLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))

        val textView = TextView(this)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.text = getString(R.string.delete)
        textView.gravity = Gravity.CENTER
        textView.textSize = 20f
        textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))

        val yesButton = Button(this)
        yesButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        val yBParams = yesButton.layoutParams as LinearLayout.LayoutParams
        yBParams.setMargins(0, marginInPixels , 0, 0)
        yesButton.layoutParams = yBParams

        yesButton.text = getString(R.string.yes)
        yesButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        yesButton.setOnClickListener { delete(id) }

        val noButton = Button(this)
        noButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val nBParams = yesButton.layoutParams as LinearLayout.LayoutParams
        nBParams.setMargins(0, marginInPixels, 0, 0)
        noButton.layoutParams = nBParams

        noButton.text = getString(R.string.no)
        noButton.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGray))
        noButton.setOnClickListener { startActivity(intent) }

        internalLinearLayout.addView(textView)
        internalLinearLayout.addView(yesButton)
        internalLinearLayout.addView(noButton)

        mainLinearLayout.addView(internalLinearLayout)

        setContentView(mainLinearLayout)
    }

    private fun getDb(){
        val dbRead: SQLiteDatabase = this.db.readableDatabase
        val cursor = dbRead.rawQuery("SELECT * FROM Armament", null)
        val tlContainer: TableLayout = findViewById(R.id.tlContainer)

        if (cursor.moveToFirst()){
            do{
                val row = TableRow(this)

                val tvGet = TextView(this)
                var id: Int = cursor.getInt(0)
                tvGet.append(cursor.getString(1).toString() + ", ")
                tvGet.append(cursor.getString(2).toString() + ", ")
                tvGet.append(cursor.getString(3).toString() + ", ")
                tvGet.append(cursor.getInt(4).toString() + ", ")
                tvGet.append(cursor.getInt(5).toString() + ", ")
                tvGet.append(cursor.getString(6).toString() + ", ")
                tvGet.append(cursor.getInt(7).toString() + "\n")
                val params0 = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 10f) // Peso mayor
                tvGet.layoutParams = params0
                row.addView(tvGet)

                val editImageView = ImageView(this)
                editImageView.setImageResource(R.drawable.ic_edit)
                editImageView.setOnClickListener { edit(id) }
                val params1 = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f) // Peso menor
                editImageView.layoutParams = params1
                row.addView(editImageView)

                val deleteImageView = ImageView(this)
                deleteImageView.setImageResource(R.drawable.ic_delete)
                deleteImageView.setOnClickListener { generateModal(id) }
                deleteImageView.layoutParams = params1
                row.addView(deleteImageView)

                tlContainer.addView(row)
            } while (cursor.moveToNext())
        }
    }

    private fun formatDate(date: Date): String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(date)
    }
}