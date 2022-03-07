package com.example.bangu.signup.data

import android.util.Log
import com.example.bangu.signup.data.model.SignupModel
import java.sql.Timestamp

object SgRepository {
    private val remoteDataService = SgDataResource
    fun requestSignup(birth:Long, createAt: String, email:String, gender:String,nickname:String,
                      password:String, updateAt: String, ott:MutableMap<String,Boolean>,callback:GetDataCallback<SignupModel>){
        remoteDataService.requestSignup(birth,createAt, email,gender,nickname,password,updateAt,ott,callback)
        Log.d(" SgRepository","just did remoteDataService.requestSignup")
    }
    interface GetDataCallback<T>{
        fun onSuccess(data:T?)
        fun onFailure(throwable: Throwable)
    }
}