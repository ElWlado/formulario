package wladimir.andrades.formulario

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
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

        intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
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
                deleteImageView.setOnClickListener { delete(id) }
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