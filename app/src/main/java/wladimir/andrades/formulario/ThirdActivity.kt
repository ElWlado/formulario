package wladimir.andrades.formulario

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ThirdActivity : AppCompatActivity() {
    private lateinit var tvGet: TextView
    private var db = myDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        tvGet = findViewById(R.id.tvGet)
        getDb()
    }

    private fun getDb(){
        val dbRead: SQLiteDatabase = this.db.readableDatabase
        val cursor = dbRead.rawQuery("SELECT * FROM Armament", null)

        if (cursor.moveToFirst()){
            do{
                tvGet.append(cursor.getInt(0).toString() + ": ")
                tvGet.append(cursor.getString(2).toString() + ", ")
                tvGet.append(cursor.getString(3).toString() + ", ")
                tvGet.append(cursor.getInt(4).toString() + ", ")
                tvGet.append(cursor.getInt(5).toString() + ", ")
                tvGet.append(cursor.getString(6).toString() + ", ")
                tvGet.append(cursor.getInt(7).toString() + "\n")
            } while (cursor.moveToNext())
        }
    }
}