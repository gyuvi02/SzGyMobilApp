package com.gyula.kepek

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), JsonLetoltes.OnDownloadComplete,
        KepParser.OnDataAvailable,
        KlikkListener.OnRecyclerClickListener {

    private val kepViewAdapter = KepViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val letoltes = JsonLetoltes(this)
        letoltes.execute("https://jsonplaceholder.typicode.com/photos")

        activateToolbar(false)
        recycler_view.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recycler_view.addOnItemTouchListener(KlikkListener(this, recycler_view, this))
        recycler_view.adapter = kepViewAdapter
    }

    override fun onItemClick(view: View, position: Int) {
        val nagyKep = kepViewAdapter.getKep(position)
        if (nagyKep != null) {
            val intent = Intent(this, KepReszletek::class.java)
            intent.putExtra(KEPATVITEL, nagyKep)
            startActivity(intent)
        }

    }

    override fun onItemLongClick(view: View, position: Int) {
        Toast.makeText(this, "Ennek most nincs funkciója", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: LetoltesAllapot) {
        if (status == LetoltesAllapot.OK) {

            val kepAdatok = KepParser(this)
            kepAdatok.execute(data)
        } else {
        }
    }

    override fun onDataAvailable(data: List<Kep>) {
        kepViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(LEKERDEZES, "")

        if (queryResult.isNotEmpty()) {
            //Keresés
        }
    }
}
