package com.magma.tradecoach.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.networking.CoinsRepository
import com.magma.tradecoach.networking.LoginRegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginRegisterRepository: LoginRegisterRepository,
private val coinsRepository: CoinsRepository): ViewModel(){
    //Login Data
    private var _loginLiveData = MutableLiveData<FirebaseUser?>()
    var loginLiveData = _loginLiveData as LiveData<FirebaseUser?>

    private var _initialCurrencyListLiveData = MutableLiveData<List<MarketCoinModel>>()
    var initialCurrencyListLiveData = _initialCurrencyListLiveData as LiveData<List<MarketCoinModel>>

    //Register Data
    private var registerMutableData = MutableLiveData<Boolean>()
    var registerData = registerMutableData as LiveData<Boolean>

    fun login(email: String, password: String,c:Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRegisterRepository.login(email, password,c)
                .fold({
                    _loginLiveData.postValue(it)
                }) {
                    _loginLiveData.postValue(null)
                }
        }
    }

    fun register(username:String,country:String,email: String, password: String,c:Context) {
        viewModelScope.launch {
            registerMutableData.postValue(
                loginRegisterRepository.register(username,country,email, password,c)
            )
        }
    }
    fun getCoins() {
        viewModelScope.launch {
            coinsRepository.getResults().fold({ result ->
                _initialCurrencyListLiveData.postValue(result)
            },
                { throwable ->
                    throwable.printStackTrace()
                })
        }
    }
}