package com.example.bangu.main.home.data

import android.util.Log
import com.example.bangu.main.home.data.model.RequestReviewList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object HomeDataResource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://bangu.shop:443")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val HomeApi = retrofit.create(HomeAPI::class.java)
    fun requestReviewList(accessToken:String,page:Int, size:Int,type:String,callback: HomeRepository.GetDataCallback<RequestReviewList>){
        Log.d("HomeDataResource","requestReviewList")
        HomeApi.requestReviewList(accessToken,page,size,type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess(it)
            },{
                Log.d("HomeDataResource","Failure: " + it.localizedMessage)
                callback.onFailure(it)
            })
    }
    fun adjustBookmark(accessToken:String,reviewId:Int,callback: HomeRepository.GetDataCallback<Boolean>){
        Log.d("HomeDataResource","adjustBookmark")
        HomeApi.adjustBookmark(accessToken,reviewId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess(it)
            },{
                Log.d("HomeDataResource","Failure: " + it.localizedMessage)
                callback.onFailure(it)
            })
    }
}