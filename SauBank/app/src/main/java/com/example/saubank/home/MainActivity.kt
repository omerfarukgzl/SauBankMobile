package com.example.saubank.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.saubank.R
import com.example.saubank.databinding.ActivityMainBinding
import com.example.saugetir.data.remote.model.BankRepository
import com.example.saugetir.data.remote.model.RetrofitClient
import com.example.saugetir.data.remote.model.response.BankInfoResponse
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        getbankInfo()




        setContentView(view)
    }



    fun getbankInfo()
    {
        try {

            val repository = BankRepository(RetrofitClient.getBank())
            val call = repository.requestBankInfo()

            call.enqueue(object : Callback<BankInfoResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<BankInfoResponse>, response: Response<BankInfoResponse>) {
                    Log.d("TreeDSecureFragmentNew", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val bankResponse = response.body()!!
                            Log.d("BankResponse", bankResponse.toString())
                            binding.amountTextView.text = bankResponse.amount.toString()

                        }
                    }
                    else {

                        Toast.makeText(this@MainActivity, "İstek Başarısız", Toast.LENGTH_LONG).show()
                        Log.d("MainActivity","else Hatası")
                        Log.d("MainActivity", response.errorBody().toString())
                    }
                }
                // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
                override fun onFailure(call: Call<BankInfoResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivityError", t.message!!)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

















}