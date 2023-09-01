package com.alexlyxy.alextroikaone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alexlyxy.alextroikaone.databinding.ActivityMainBinding
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

const val API_KEY = "8ea82f6e78674e4996e181556230908"

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bGet.setOnClickListener {
            this.getResult(name = ("London"))
            this.getMama()
            this.getMessage()

        }
    }

    private fun getMessage() {
        val url = "https://dog.ceo/api/breeds/image/random"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            { message ->
                val obj = JSONObject(message)
                //val info = obj.getJSONObject("message")
                Log.d("MyDog","Message: $message")
            },
            {
                Log.d("MyDog","Volley error: $it")

            }
        )
        queue.add(stringRequest)
    }

    private fun getMama() {
        val url = "https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            { mama ->
               val obj = JSONObject(mama)
               //val info = obj.getJSONObject("USD")
               Log.d("MyCoin","Mama: $mama")
            },
            {
               Log.d("MyCoin","Volley error: $it")

            }
        )
        queue.add(stringRequest)
    }

    private fun getResult(name :String) {
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                Log.d("MyWeather","Response: ${temp.getString("temp_c")}")
            },
            {
                Log.d("MyWeather","Volley error: $it")

            }
        )
        queue.add(stringRequest)
    }
}
