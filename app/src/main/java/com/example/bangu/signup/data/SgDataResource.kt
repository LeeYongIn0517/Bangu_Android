package com.example.bangu.signup.data

import android.util.Log
import com.example.bangu.signup.data.model.SignupRequest
import com.example.bangu.signup.data.model.SignupResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SgDataResource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://bangu.shop:443")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val signupApi = retrofit.create(SignupAPI::class.java)
    /*아이디 중복확인*/
    fun checkUserId(userIdText:String,callback: SgRepository.GetDataCallback<Boolean>){
        Log.d("SgDataResource","checkUserEmail")
        signupApi.checkUserId(userIdText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess(it)
            },{
                callback.onFailure(it)
            })
    }
    /*닉네임 중복확인*/
    fun checkNickname(nickname:String,callback: SgRepository.GetDataCallback<Boolean>){
        Log.d("SgDataResource","checkNickname")
        signupApi.checkNickname(nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onSuccess(it)
            },{
                callback.onFailure(it)
            })
    }
    /*회원가입*/
    fun requestSignup(signupRequest: SignupRequest, callback: SgRepository.GetDataCallback<SignupResponse>){
        Log.d("SgDataResource","signupApi.requestSignup")
        signupApi.requestSignup(signupRequest).enqueue(object:Callback<SignupResponse>{
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                Log.d("SgDataResource","signupApi.requestSignup.onResponse")
                if(response.isSuccessful){
                    Log.d("SgDataResource","signupApi.requestSignup.onResponse.callback-Successful")
                    callback.onSuccess(response.body())
                }else{
                    val data = response.headers()
                    val data2 = response.body()
                    val data3 = response.message()
                    val data4 = response.code()
                    Log.d("SgDataResource","signupApi.requestSignup.onResponse.callback-UnSuccessful")
                    Log.d("SgDataResource.header",data.toString())
                    Log.d("SgDataResource.body",data2.toString())
                    Log.d("SgDataResource.message",data3.toString())
                    Log.d("SgDataResource.code",data4.toString())
                }
            }
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.d("SgDataResource","signupApi.requestSignup.onFailure")
                callback.onFailure(t)
            }
        })
    }
}