package com.ladyprogram.film.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ladyprogram.film.R
import com.ladyprogram.film.data.Film
import com.ladyprogram.film.data.FilmService
import com.ladyprogram.film.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var film: Film
    lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("SUPERHERO_ID")!! //Se o¡puede añadir defaultValue: -1
        getFilmById(id)

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_info -> {
                    binding.plotContent.root.visibility = View.GONE
                    binding.infoContent.root.visibility = View.VISIBLE

                }

                R.id.action_plot -> {
                    binding.plotContent.root.visibility = View.VISIBLE
                    binding.infoContent.root.visibility = View.GONE
                }
            }

            true
        }

        binding.navigationBar.selectedItemId = R.id.action_info
    }


    fun loadData() {
        //Toast.makeText(this, film.name, Toast.LENGTH_LONG).show()
        Picasso.get().load(film.Poster).into(binding.posterImageView)

        supportActionBar?.title = film.Title
        //supportActionBar?.subtitle = film.Year

        //INFO
        binding.infoContent.titleTextView.text = film.Title
        binding.infoContent.yearTextView.text = film.Year
        binding.infoContent.directorTextView.text = film.Director
        binding.infoContent.runtimeTextView.text = film.Runtime
        binding.infoContent.genreTextView.text = film.Genre
        binding.infoContent.countryTextView.text = film.Country

        //PLOT
        binding.plotContent.plotTextView.text = film.Plot

    }

    fun getRetrofit(): FilmService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(FilmService::class.java)
    }

    fun getFilmById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                film = service.findFilmById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
