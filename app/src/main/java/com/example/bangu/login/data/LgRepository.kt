package com.example.bangu.login.data

import android.util.Log
import com.example.bangu.login.data.model.AccessToken
import com.example.bangu.login.data.model.LoginRequest
import com.example.bangu.login.data.model.LoginResponse
import io.reactivex.rxjava3.core.Single

object LgRepository {
    val lgdr = LgDataResource

    fun getKakaoAuthCode(callback:GetDataCallback<AccessToken>){
        lgdr.getKakaoToken(callback)
        Log.d("LgRepository","just did LgDataService.getKakaoAuthCode")
    }
    fun getLoginToken(email:String,password:String,callback:GetDataCallback<LoginResponse>){
        lgdr.getLoginToken(email,password,callback)
        Log.d("LgRepository","just did LgDataService.getLoginToken")
    }
    //로그인 요청에 대한 응답인터페이스
    interface GetDataCallback<T>{
        fun onSuccess(data:T?)
        fun onFailure(throwable: Throwable)
    }
}