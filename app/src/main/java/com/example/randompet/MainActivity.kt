package com.example.randompet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var petList: MutableList<String>
    private lateinit var rvPets: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rvPets = findViewById<RecyclerView>(R.id.pet_list)
        rvPets.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        petList = mutableListOf()
        fetchDogImages()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }



//    private fun fetchCatImage(imageView: ImageView){
//        val client = AsyncHttpClient()
//
//        client["https://api.thecatapi.com/v1/images/search", object : JsonHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
//                val petImageURL = json.jsonArray.getJSONObject(0).getString("url")
//                Log.d("petImageURL", "Pet Image URL: $petImageURL")
//
//                Glide.with(this@MainActivity).load(petImageURL).fitCenter().into(imageView)
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                errorResponse: String,
//                throwable: Throwable?
//            ) {
//                Log.d("CatCat Error", errorResponse)
//            }
//        }]
//    }
//
    private fun fetchDogImages(){
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {

                val petImageArray = json.jsonObject.getJSONArray("message")
                //fetch each image in the array and add them to petList(mutableList)
                for (i in 0 until petImageArray.length()){
                    petList.add(petImageArray.getString(i))
                }
                // Attach petList to PetAdapter
                val adapter = PetAdapter(petList)
                // Attach adapter the rvPet's adapter
                rvPets.adapter = adapter
                // Setup the layout...
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }
}