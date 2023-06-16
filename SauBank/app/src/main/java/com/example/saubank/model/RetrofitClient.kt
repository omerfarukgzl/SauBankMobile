package com.example.saugetir.data.remote.model

import android.util.Log
import com.example.saugetir.utils.Constants.BASE_URL
import com.example.saugetir.utils.Constants.VERSION
import com.example.saugetir.utils.EncryptionUtil.generateRandomKey
import com.example.saugetir.utils.EncryptionUtil.generateSignature
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private var bankApi: BankApi? = null
    private val interceptor = HttpLoggingInterceptor()

    private val customHeaderInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val request = chain.request()
        val requestUrl = request.url.toString()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readString(Charsets.UTF_8)
        val randomKey = generateRandomKey()
        Log.d("requestUrl", requestUrl);

        requestBuilder
            .addHeader("x-signature", generateSignature(randomKey, requestBodyString))
            .addHeader("x-auth-version", VERSION)
            .addHeader("x-rnd-key", randomKey)

        chain.proceed(requestBuilder.build())
    }

    fun getBank(): BankApi {
        if (bankApi == null) {
            //interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
               /* .addInterceptor(customHeaderInterceptor)*/
                .build()

            bankApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(BankApi::class.java)

        }
        return bankApi!!
    }










/*    fun getClient(baseUrl: String): Retrofit {

            val httpClient = OkHttpClient.Builder()

            // Add Authorization header to every request
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "your_token_here")
                    .build()
                chain.proceed(request)
            }

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }*/
}




/* private var merchantApi: MerchantApi? = null
    private val interceptor = HttpLoggingInterceptor()

    private val customHeaderInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val request = chain.request()
        val requestUrl = request.url.toString()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readString(Charsets.UTF_8)
        val randomKey = generateRandomKey()

        requestBuilder
            .addHeader("x-signature", generateSignature(requestUrl, randomKey, requestBodyString))
            .addHeader("x-auth-version", Constant.VERSION)
            .addHeader("x-api-key", Constant.API_KEY)
            .addHeader("x-rnd-key", randomKey)

        chain.proceed(requestBuilder.build())
    }

    fun getOderoApi(): MerchantApi {
        if (merchantApi == null) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            merchantApi = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MerchantApi::class.java)

        }
        return merchantApi!!
    }

*/