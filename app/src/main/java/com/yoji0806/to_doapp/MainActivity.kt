package com.yoji0806.to_doapp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.yoji0806.to_doapp.R.color.*
import io.realm.*
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list_tasktitle.view.*
import kotlin.random.Random



class MainActivity : AppCompatActivity() {


    lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)


        edit_taskTitle.visibility = View.GONE



        val realmResult = realm.where<TaskTitleModel>().findAll()

        val adapter = MainAdapter(this, realmResult, false, realm)

        recyclerView_taskTitle.layoutManager = GridLayoutManager(this, 2)
        recyclerView_taskTitle.adapter = adapter



        var lowPosition = true

        fab.setOnClickListener {


            if (lowPosition) {
                edit_taskTitle.visibility = View.VISIBLE
                showSoftKeyboard(edit_taskTitle)

             /*   val layoutParam = CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT)
                layoutParam.setMargins(860,150,0,0)
                fab.layoutParams = layoutParam*/



            }else if (!lowPosition && TextUtils.isEmpty(edit_taskTitle.text)){
                edit_taskTitle.visibility = View.GONE
                hideSoftKeyboard(edit_taskTitle)

            }
            else{

                val taskTitle = edit_taskTitle.text.toString()

                realm.executeTransaction {
                    val maxId = realm.where<TaskTitleModel>().max("id")
                    val nextId = (maxId?.toInt() ?: 0) + 1


                    val taskBox = realm.createObject<TaskTitleModel>(nextId)

                    taskBox.title = taskTitle
                    taskBox.itemsLeft = 0               //need changed

                    adapter.notifyItemInserted(nextId - 1)
                    edit_taskTitle.text = null
                }


                edit_taskTitle.visibility = View.GONE
                hideSoftKeyboard(edit_taskTitle)
            }

            lowPosition = !lowPosition
        }
    }


    private fun hideSoftKeyboard(view:View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    private fun showSoftKeyboard(view:View) {
        if (view.requestFocus())
        {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }



}





class MainAdapter(private val context: Context, private var collection : OrderedRealmCollection<TaskTitleModel>, private val autoUpdate : Boolean, private val realm : Realm)
    : RealmRecyclerViewAdapter<TaskTitleModel, MainAdapter.MainViewHolder>(collection, autoUpdate){

    inner class MainViewHolder(view : View) : RecyclerView.ViewHolder(view){

        init {


            view.setOnClickListener {


                Log.i("チェック：id", "${position}")
            }
        }

    }



    override fun getItemCount(): Int {
        return collection.size
    }



    //create a view & return a ViewHolder
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val cellForRom = layoutInflater.inflate(R.layout.list_tasktitle, p0, false)

        return MainViewHolder(cellForRom)
    }


    private val colorList = listOf(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9)



    //customize a view in a ViewHolder (In this case, 2TextViews  & one imageButton)
    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {

        val titleBox = collection.sort("id", Sort.DESCENDING)[p1]

        Log.i("いえーい", "ポジション: $p1, コレクション: $collection")

        p0.itemView.text_taskTitle.text = titleBox?.title

        val colorIndex = Random.nextInt(10)

        p0.itemView.text_taskTitle.setBackgroundResource(colorList[1])         //need changed


        val textForItemsLeft = when(titleBox?.itemsLeft){
            0 -> "0 item left"
            1 -> "1 item left"
            else -> "${titleBox?.itemsLeft} items left"
        }
        p0.itemView.text_itemsLeft.text  = textForItemsLeft

        p0.itemView.titleDeleteButton.setOnClickListener {

            realm.executeTransaction {
                realm.where<TaskTitleModel>().equalTo("id", p1 + 1)?.findFirst()?.deleteFromRealm()

             /*   val query  = realm.where<TaskTitleModel>().findAll()
                val result = query.sort( "id", Sort.DESCENDING)


                collection = result*/
            }

            notifyDataSetChanged()


                Log.i("チェック", "コレクション$collection")

        }

    }

}
