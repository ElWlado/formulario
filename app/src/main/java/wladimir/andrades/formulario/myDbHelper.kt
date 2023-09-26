package wladimir.andrades.formulario

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date

class myDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Game"
        private const val TABLE_NAME = "Armament"

        private const val COL_NAME = "Nombre"
        private const val COL_ATTRIBUTE = "Atributo"
        private const val COL_RARITY = "Rareza"
        private const val COL_DAMAGE = "Daño"
        private const val COL_USES = "Usos"
        private const val COL_DATE_OBTAINED = "FechaObtenida"
        private const val COL_MAGIC = "Magia"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, $COL_ATTRIBUTE TEXT, $COL_RARITY TEXT, " +
                "$COL_DAMAGE INTEGER, $COL_USES INTEGER, " +
                "$COL_DATE_OBTAINED TEXT, $COL_MAGIC INTEGER)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addArmament(name: String, attribute: String, rarity: String, damage: Int, uses: Int, date: String, magic: Boolean) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COL_NAME, name)
        values.put(COL_ATTRIBUTE, name)
        values.put(COL_RARITY, attribute)
        values.put(COL_DAMAGE, damage)
        values.put(COL_USES, uses)
        values.put(COL_DATE_OBTAINED, date)
        values.put(COL_MAGIC, if (magic) 1 else 0)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


}
