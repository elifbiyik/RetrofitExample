package com.eb.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3 yöntemde de retrofit nesnesi oluşturulur.
        val BASE_URL = "https://api.github.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)


        // List<User> dönüş tipi
        lifecycleScope.launch {
            try {
                val users = apiService.getAllUser()
                Log.d("Users (List<User>)", users.toString())

            } catch (e: Exception) {
                Log.d("Users (List<User>) Hata", e.message.toString())
            }
        }

        lifecycleScope.launch {
            try {
                val users = apiService.getUserId("1")
                Log.d("UsersId (User)", users.toString())

            } catch (e: Exception) {
                Log.d("UsersId (User) Hata", e.message.toString())
            }
        }




        // Call<User> dönüş tipi
        val call = apiService.getAllUserCall()
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    Log.d("Users (Call<User>)", users.toString())
                } else {
                    Log.e("Users (Call<User>) Hata", response.code().toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Users (Call<User>) Hata", t.message.toString())

            }
        })


        // Call<List<User>> dönüş tipi
        val callList = apiService.getAllUserCallList()
        callList.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    Log.d("Users (Call<List) ", users.toString())
                } else {
                    Log.e("Users (Call<List) Hata", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Users (Call<List) Hata", t.message.toString())

            }
        })


        // Response<List<User>> dönüş tipi
        lifecycleScope.launch {
            try {
                val response = apiService.getAllUserResponse()

                if (response.isSuccessful) {
                    val users = response.body()
                    val usersResponse = response
                    Log.d("Users (Response) Body", users.toString())
                    Log.d("Users (Response) Response", usersResponse.toString())

                } else {
                    Log.e("Users Response Hata", response.code().toString())

                }
            } catch (e: Exception) {
                Log.e("Users Response Hata", e.message.toString())

            }
        }

    }
}