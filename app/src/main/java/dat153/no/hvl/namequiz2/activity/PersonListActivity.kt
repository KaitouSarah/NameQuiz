package dat153.no.hvl.namequiz2.activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dat153.no.hvl.namequiz2.R
import dat153.no.hvl.namequiz2.data.PersonDatabaseHandler
import dat153.no.hvl.namequiz2.data.PersonListAdapter
import dat153.no.hvl.namequiz2.model.Person
import kotlinx.android.synthetic.main.activity_person_list.*

class PersonListActivity : AppCompatActivity() {

    private var adapter: PersonListAdapter? = null
    private var personList: ArrayList<Person>? = null
    private var personListItems: ArrayList<Person>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: PersonDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list)

        dbHandler = PersonDatabaseHandler(this)

        personList = ArrayList()
        personListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = PersonListAdapter(personListItems!!, this)

        //Setup recyclerView
        recyclerViewID.layoutManager = layoutManager
        recyclerViewID.adapter = adapter

        personList = dbHandler!!.readPersons()

        personList!!.reverse()

        for (p in personList!!.iterator()){
            val person = Person()

            person.name = "Name: ${p.name}"
            person.img = p.img
            person.id = p.id

            personListItems!!.add(person)
        }

        adapter!!.notifyDataSetChanged()
    }

    //TODO Add OnCreateOptionsMenu + onOptionsItemSelected + popup

}