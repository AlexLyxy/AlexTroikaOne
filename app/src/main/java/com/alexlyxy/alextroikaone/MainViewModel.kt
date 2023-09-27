package com.alexlyxy.alextroikaone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexlyxy.alextroikaone.adapters.DoCoWeModel

class MainViewModel  : ViewModel (){
    val liveDataCurrent = MutableLiveData<DoCoWeModel>()
    val liveDataList = MutableLiveData<List<DoCoWeModel>>()
}