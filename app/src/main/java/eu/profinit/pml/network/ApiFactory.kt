package eu.profinit.pml.network

import eu.profinit.pml.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private val apiInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
        chain.proceed(newRequest)
    }

    private val apiClient = OkHttpClient().newBuilder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .addInterceptor(logInterceptor)
        .addInterceptor(apiInterceptor)
        .build()

    fun retrofitApi(): Retrofit = Retrofit.Builder()
        .client(apiClient)
        .baseUrl("https://smartplans.azurewebsites.net/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val API_SERVICE: ApiService = retrofitApi()
        .create(ApiService::class.java)

}