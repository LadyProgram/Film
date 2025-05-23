package com.ladyprogram.film.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ladyprogram.film.R
import com.ladyprogram.film.adapter.FilmAdapter
import com.ladyprogram.film.data.Film
import com.ladyprogram.film.data.FilmService.Companion.getRetrofit
import com.ladyprogram.film.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var filmList: List<Film> = listOf()

    lateinit var adapter: FilmAdapter
    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = FilmAdapter(filmList) { position ->
            val film = filmList[position]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SUPERHERO_ID", film.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)


        searchFilmByName("Red")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.i("MENU", "He pulsado Enter")
                searchFilmByName(query)
                return false
            }


            override fun onQueryTextChange(s: String): Boolean {
                //Log.i("MENU", s)
                return true
            }
        })

        return true

    }

    fun searchFilmByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.findFilmByName(query)

                filmList = result.results

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = filmList
                    adapter.notifyDataSetChanged()

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


            //for (film in result.results) {
            //Log.i("API", filmList.toString())
        }
    }
}