package com.example.searchbarapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.searchbarapplication.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "imdb-api.tprojects.workers.dev";

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = arrayOf("Berke", "Ali", "Ahmet", "Emre", "Hasan", "Mehmet", "Faruk")

        val userAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            user
        )

        binding.userList.adapter = userAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (user.contains(query)) {
                    userAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }
        })

        getData()
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData();

        retrofitData.enqueue(object : Callback<List<IMDBDataItem>?> {
            override fun onResponse(
                call: Call<List<IMDBDataItem>?>,
                response: Response<List<IMDBDataItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for (imdbData in responseBody) {
                    myStringBuilder.append(imdbData)
                    myStringBuilder.append("\n")
                }
            }

            override fun onFailure(call: Call<List<IMDBDataItem>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        });
    }
}