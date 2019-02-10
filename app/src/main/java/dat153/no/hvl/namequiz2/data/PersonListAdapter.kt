package dat153.no.hvl.namequiz2.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import dat153.no.hvl.namequiz2.R
import dat153.no.hvl.namequiz2.model.Person
import kotlinx.android.synthetic.main.popup.view.*
import kotlin.collections.ArrayList

class PersonListAdapter(private val list: ArrayList<Person>?,
                        private val context: Context): RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {

//TODO

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }
//TODO
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {



    }

    override fun getItemCount(): Int {
        return list!!.size
    }




    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Person>): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //local instances of parent parameters
        var mContext: Context = context
        var mList: ArrayList<Person> = list

        var personName = itemView.findViewById(R.id.txt_name_card) as TextView
        var personImg: ImageView = itemView.findViewById(R.id.img_card) as ImageView
        var deleteBtn: Button = itemView.findViewById(R.id.btn_delete_card) as Button
        var editBtn: Button = itemView.findViewById(R.id.btn_edit_card) as Button


        fun bindViews(person: Person){
            personName.text = person.name.toString()
            personImg.setImageURI(Uri.parse(person.img))

            //Register buttons for onClick
            deleteBtn.setOnClickListener (this)
            editBtn.setOnClickListener(this)
        }


        override fun onClick(v: View?) {

            var mPosition: Int = adapterPosition
            var person: Person = mList[mPosition]

            when(v!!.id){
                deleteBtn.id -> {
                    deletePerson(person.id!!)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editBtn.id -> {
                    editPerson(person)
                }
            }

        }

        fun deletePerson(id : Int){
            var db : PersonDatabaseHandler = PersonDatabaseHandler(mContext)
            db.deletePerson(id)
        }

        fun editPerson(person: Person){
            var dialogBuilder: AlertDialog.Builder? = null
            var dialog: AlertDialog? = null
            var dbHandler: PersonDatabaseHandler = PersonDatabaseHandler(context)

            var view: View = LayoutInflater.from(context).inflate(R.layout.popup, null)

            //Invoke widgets
            var personName = view.pop_name
            var personImg = view.pop_img
            var imgBtn = view.pop_img_btn
            var saveBtn = view.pop_save_btn

            //Build dialog
            dialogBuilder = AlertDialog.Builder(context).setView(view)

            //Show the dialog
            dialog = dialogBuilder!!.create()
            dialog?.show()

            //TODO Add the code segment from v1 - > image URI

            saveBtn.setOnClickListener{
                if (!TextUtils.isEmpty(personName.text.toString().trim())){
                    person.name = personName.text.toString()
                    person.img = personImg.toString()

                    dbHandler!!.updatePerson(person)

                    notifyItemChanged(adapterPosition, person)

                    dialog!!.dismiss()
                } else {
                    //Do nothing
                }
            }
        }

    }
}