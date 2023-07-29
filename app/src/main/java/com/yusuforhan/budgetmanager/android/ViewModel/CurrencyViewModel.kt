package com.yusuforhan.budgetmanager.android.ViewModel

import android.app.Application
import android.net.SocketKeepalive
import android.os.Handler
import android.telecom.Call
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.yusuforhan.budgetmanager.android.Model.BudgetModel
import com.yusuforhan.budgetmanager.android.Model.CryptoModel
import com.yusuforhan.budgetmanager.android.Service.CurrencyService
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.security.auth.callback.Callback
import kotlin.reflect.typeOf

class CurrencyViewModel(application: Application) : BaseViewModel(application = application) {
    private val service = CurrencyService()
    var crypto = MutableLiveData<List<CryptoModel>>(arrayListOf())
    var cryptoLoading = MutableLiveData<Boolean>(true)
    var cryptoError = MutableLiveData<Boolean>()
    fun getCurrency(){
        launch {
            try {
                val response = service.getData()
                if (response.isSuccessful){
                    crypto.value = response.body()
                    Toast.makeText(getApplication(),"Veriler Geldi",Toast.LENGTH_SHORT).show()
                    cryptoLoading.value = false
                    cryptoError.value = false
                }else{
                    cryptoLoading.value = false
                    cryptoError.value = true
                }
            }catch (e : Exception){
                cryptoLoading.value = false
                cryptoError.value = true
            }

        }
    }
}