package com.alexlyxy.alextroikaone

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.alexlyxy.alextroikaone.adapters.DoCoWeAdapter
import com.alexlyxy.alextroikaone.adapters.DoCoWeModel
import com.alexlyxy.alextroikaone.databinding.ActivityMainBinding
import com.alexlyxy.alextroikaone.pojo.CoinInfo
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY = "8ea82f6e78674e4996e181556230908"

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: DoCoWeAdapter

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bGet.setOnClickListener {
            getDogMessage()
            getCoin()
            getWeatherData(city = "London")
            init()
            model.liveDataList.observe(this@MainActivity) {
                adapter.submitList(it.subList(1, it.size))
                // adapter.submitList(parseDogs())
            }
        }
    }

    private fun init() = with(binding) {
        adapter = DoCoWeAdapter()
        //rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }

    //    SITE COIN

    private fun getCoin() {
        //val url = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR"
        val url = "https://min-api.cryptocompare.com/data/top/totalvolfull?limit=10&tsym=USD"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            { coinInfo ->
                parseCoinData(coinInfo)

                val mainObjectCoin = JSONObject(coinInfo)
                val infoCoin = mainObjectCoin.getJSONArray("Data")
                Log.d(
                    "MyLog", "InfoCoinName: ${
                        infoCoin
                            .getJSONObject(0)
                            .getJSONObject("CoinInfo")
                            .getString("Name")
                    }"
                )

                Log.d(
                    "MyLog", "InfoCoinFullName: ${
                        infoCoin
                            .getJSONObject(0)
                            .getJSONObject("CoinInfo")
                            .getString("FullName")
                    }"
                )

                Log.d(
                    "MyLog", "InfoCoinImageOne: ${
                        infoCoin
                            .getJSONObject(0)
                            .getJSONObject("CoinInfo")
                            .getString("ImageUrl")
                    }"
                )
                Log.d(
                    "MyLog", "InfoCoinImageTwo: ${
                        infoCoin
                            .getJSONObject(0)
                            .getJSONObject("CoinInfo")
                            .getString("Url")
                    }"
                )
            },
            {
                Log.d("MyLog", "Volley error: $it")
            }
        )
        queue.add(stringRequest)
    }

    private fun parseCoinData(coinInfo: String) {
        val mainObjectCoin = JSONObject(coinInfo)
        Log.d("MyLog", "MainObjectCoin: $mainObjectCoin")
        parseCoin(mainObjectCoin)
    }

    private fun parseCoin(mainObjectCoin: JSONObject): List<DoCoWeModel> {
        val coinArray = mainObjectCoin.getJSONArray("Data")
        Log.d("MyLog", "CoinArray: $coinArray")
        val listCoin = ArrayList<DoCoWeModel>()

        //for (i in 0 until coinArray.length()) {
        for (i in 0 until 2) {
            val item = DoCoWeModel(
                dogFaceOne = "",
                dogFaceTwo = "",
                dogFaceThree = "",
                coinArray
                    .getJSONObject(0)
                    .getJSONObject("CoinInfo")
                    .getString("Name"),
                coinArray
                    .getJSONObject(0)
                    .getJSONObject("CoinInfo")
                    .getString("FullName"),
                coinArray
                    .getJSONObject(0)
                    .getJSONObject("CoinInfo")
                    .getString("Url"),
                coinArray
                    .getJSONObject(0)
                    .getJSONObject("CoinInfo")
                    .getString("ImageUrl"),
                time = "26.09.2023",
                //day.getString("date"),
                condition = "Clear",
                //day.getJSONObject("day").getJSONObject("condition").getString("text"),
                currentTemp = "25",
                //day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                imageURL = "ImageUrl"
                //day.getJSONObject("day").getJSONObject("condition").getString("icon")
            )
            listCoin.add(item)
            Log.d("MyLog", "CoinListLast: $listCoin")
        }
        model.liveDataList.value = listCoin
        return listCoin
    }

    //   SITE WEATHER

    private fun getWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseWeatherData(result)

                Log.d("MyLog", "Wether: $result")
            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObjectWe = JSONObject(result)
        Log.d("MyLog", "MainObjectWeather: $mainObjectWe")
        parseDays(mainObjectWe)
    }

    private fun parseDays(mainObjectWe: JSONObject): List<DoCoWeModel> {
        val listWe = ArrayList<DoCoWeModel>()
        val daysArray = mainObjectWe.getJSONObject("forecast").getJSONArray("forecastday")

        for (i in 0 until daysArray.length()) {
            //for (i in 0 until 2) {
            val day = daysArray[i] as JSONObject
            val item = DoCoWeModel(
                dogFaceOne = "",
                dogFaceTwo = "",
                dogFaceThree = "",
                coinName = "BitCoin",
                coinFullName = "",
                coinImageUrl = "",
                coinUrl = "",
                time = day.getString("date"),
                condition = day.getJSONObject("day").getJSONObject("condition").getString("text"),
                currentTemp = day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt()
                    .toString(),
                imageURL = day.getJSONObject("day").getJSONObject("condition").getString("icon")
            )
            listWe.add(item)

            val time = day.getString("date")
            val condition = day.getJSONObject("day").getJSONObject("condition").getString("text")
            val currentTemp =
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString()
            val imageURL = day.getJSONObject("day").getJSONObject("condition").getString("icon")

            Log.d("MyLog", "Date: $time ")
            Log.d("MyLog", "Condition: $condition")
            Log.d("MyLog", "CurrentTemp: $currentTemp")
            Log.d("MyLog", "ImageURL: $imageURL")

            Log.d("MyLog", "WeatherListLast: $listWe")
        }
        model.liveDataList.value = listWe
        return listWe
    }

    //    SITE DOGS

    private fun getDogMessage() {
        //val url = "https://dog.ceo/api/breeds/image/random"
        //val url = "https://dog.ceo/api/breed/hound/afghan/images"
        val url = "https://dog.ceo/api/breed/hound/images"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET,
            url,
            { message ->
                parseDogsData(message)
                Log.d("MyLog", "MessDog: $message")
                val mainObjectDog = JSONObject(message)
                val dogArray = mainObjectDog.getJSONArray("message")

                val dogsFaceOne = dogArray.getString(0)
                val dogsFaceTwo = dogArray.getString(1)
                val dogsFaceThree = dogArray.getString(2)

                Log.d("MyLog", "MessDogArray: $dogArray")

                Log.d("MyLog", "MessDogArrayOne: $dogsFaceOne")
                Log.d("MyLog", "MessDogArrayTwo: $dogsFaceTwo")
                Log.d("MyLog", "MessDogArrayThree: $dogsFaceThree")

            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseDogsData(message: String) {
        val mainObjectDog = JSONObject(message)
        Log.d("MyLog", "MainObjectDog: $mainObjectDog")
        parseDogs(mainObjectDog)
    }

    private fun parseDogs(mainObjectDog: JSONObject): List<DoCoWeModel> {
        val dogArray = mainObjectDog.getJSONArray("message")
        Log.d("MyLog", "DogArray: $dogArray")
        val listDog = ArrayList<DoCoWeModel>()
        // val daysArray = mainObjectWe.getJSONObject("forecast").getJSONArray("forecastday")

        //for (i in 0 until dogArray.length()) {
        for (i in 0 until 3) {
            //val day = daysArray[i] as JSONObject
            val item = DoCoWeModel(
                dogArray.getString(0),
                dogArray.getString(1),
                dogArray.getString(2),
                coinName = "London",
                coinFullName = "FullName",
                coinImageUrl = "CoinImageUrl",
                coinUrl = "CoinUrl",
                time = "26.09.2023",
                //day.getString("date"),
                condition = "Clear",
                //day.getJSONObject("day").getJSONObject("condition").getString("text"),
                currentTemp = "25",
                //day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                imageURL = "ImageUrl"
                //day.getJSONObject("day").getJSONObject("condition").getString("icon")
            )

            listDog.add(item)
            Log.d("MyLog", "DogListLast: $listDog")
        }
        model.liveDataList.value = listDog
        return listDog
    }
}


