package dat153.no.hvl.namequiz2.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import dat153.no.hvl.namequiz2.model.*

class PersonDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Create Person DB
     */
    override fun onCreate(db: SQLiteDatabase?) {

        //Values from Constants.kt
        var CREATE_PERSON_TABLE: String = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + "INTEGER PRIMARY KEY," +
                KEY_PERSON_NAME + " TEXT," +
                KEY_PERSON_IMG + " TEXT" + ")"

        db?.execSQL(CREATE_PERSON_TABLE)
    }

    /**
     * Deletes old version of DB and creates a new empty
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        //Delete old table if it exists
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        //Create DB againg
        onCreate(db)

    }

    /**
     * CRUD --->
     */

    //Create
    fun createPerson(person: Person) {

        var db: SQLiteDatabase = writableDatabase

        //HashMap of SQL/Person values
        var values = ContentValues()

        values.put(KEY_PERSON_NAME, person.name)
        values.put(KEY_PERSON_IMG, person.img)

        //Add to DB
        db.insert(TABLE_NAME, null, values)

        //Log entry for debug
        Log.d("DATA INSERTED", "SUCCESS")

        db.close()
    }

    fun readAPerson(id: Int): Person {

        var db: SQLiteDatabase = readableDatabase

        var cursor: Cursor = db.query(
            TABLE_NAME, arrayOf(
                KEY_ID,
                KEY_PERSON_NAME,
                KEY_PERSON_IMG
            ), KEY_ID + "=?", arrayOf(id.toString()),
            null, null, null, null
        )

        var person = Person()

        if (cursor != null) {
            cursor.moveToFirst()

            person.name = cursor.getString(cursor.getColumnIndex(KEY_PERSON_NAME))
            person.img = cursor.getString(cursor.getColumnIndex(KEY_PERSON_IMG))

        }
        cursor.close()

        return person
    }

    //Read all people in DB
    fun readPersons(): ArrayList<Person> {
        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Person> = ArrayList()

        //Select all people from DB
        var selectAll: String = "SELECT * FROM " + TABLE_NAME

        var cursor: Cursor = db.rawQuery(selectAll, null)

        //Loop through all people
        if (cursor.moveToFirst()) {
            do {
                var person = Person()

                person.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                person.name = cursor.getString(cursor.getColumnIndex(KEY_PERSON_NAME))
                person.img = cursor.getString(cursor.getColumnIndex(KEY_PERSON_IMG))

                list.add(person)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return list
    }

    //Update
    fun updatePerson(person: Person): Int {
        var db: SQLiteDatabase = writableDatabase

        var values = ContentValues()

        values.put(KEY_PERSON_NAME, person.name)
        values.put(KEY_PERSON_IMG, person.img)

        //Update a row + return ID of the updated row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(person.id.toString()))
    }

    //Delete
    fun deletePerson(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    //Returns number of People in DB
    fun getPeopleCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery: String = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)
        return cursor.count
    }
}