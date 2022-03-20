package com.example.bangu.signup.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bangu.Event
import com.example.bangu.signup.data.SgRepository
import com.example.bangu.signup.data.model.Signup
import com.example.bangu.signup.data.model.SignupResponse

class SignupViewModel:ViewModel(){
    private val repo = SgRepository
    private var _requestOk = MutableLiveData<Event<Boolean>>()
    val requestOk: LiveData<Event<Boolean>> = _requestOk
    private var _emailOk = MutableLiveData<Event<String>>()
    val emailOk: LiveData<Event<String>> = _emailOk
    private var _nicknameOk = MutableLiveData<Event<String>>()
    val nicknameOk: LiveData<Event<String>> = _nicknameOk
    private var _emailText = MutableLiveData<String>()
    val emailText : LiveData<String> = _emailText
    private var _nicknameText = MutableLiveData<String>()
    val nicknameText : LiveData<String> = _nicknameText

    fun checkUserEmail(emailText:String){
        repo.checkUserEmail(emailText,object :SgRepository.GetDataCallback<Boolean>{
            override fun onSuccess(data: Boolean?) {
                if (data != null) {
                    //LiveData로 액티비티에 성공신호 제공
                    _emailOk.postValue(Event("emailOk"))
                }
            }

            override fun onFailure(throwable: Throwable) {
                //실패신호는 어떻게 제공?
                _emailOk.postValue(Event("emailFail"))
            }
        })
    }
    fun checkNickname(nickname:String){
        repo.checkNickname(nickname,object :SgRepository.GetDataCallback<Boolean>{
            override fun onSuccess(data: Boolean?) {
                if (data != null) {
                    //LiveData로 액티비티에 성공신호 제공
                    _nicknameOk.postValue(Event("nicknameOk"))
                }
            }

            override fun onFailure(throwable: Throwable) {
                _nicknameOk.postValue(Event("nicknameFail"))
            }
        })
    }
    fun requestSignup(birth:Long, createAt: String, email:String, gender:String, nickname:String,
                      password:String, updateAt: String, ott:MutableMap<String,Boolean>){
        val signup = Signup(
            birth = birth,
            createAt = createAt,
            email = email,
            gender = gender,
            netflix = ott.get("netflix"),
            nickname = nickname,
            password = password,
            tving = ott.get("tving"),
            updateAt = updateAt,
            watcha = ott.get("watcha"),
            wavve = ott.get("wavve")
        )
        repo.requestSignup(signup, object : SgRepository.GetDataCallback<SignupResponse>{
            override fun onSuccess(data: SignupResponse?){
                if (data != null) {
                        Log.d(" Test",data.birth.toString())
                    //LiveData로 액티비티에 성공신호 제공
                    _requestOk.postValue(Event(true))
                    Log.d(" SignupViewModel","override fun onSuccess")
                }
            }

            override fun onFailure(throwable: Throwable){
            //경고창 띄우기
                Log.d(" SignupViewModel","override fun onFailure")
            }
       })
    }
}