package com.example.urrencytrans.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.urrencytrans.BR
import com.example.urrencytrans.model.Adapters.AdapterCurrencyList
import com.example.urrencytrans.model.NewValue
import com.example.urrencytrans.model.CurrencyItem
import com.example.urrencytrans.retrofit.Common
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.abs

class MainViewModel(var app: Application): BaseObservable() {

    init {
        loadList()
        Observable.interval(5,TimeUnit.MINUTES)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    updateValue()
                },{
                    throw Exception(it.message)
                })
    }

    var isRefreshing = ObservableBoolean()

    @get:Bindable
    var currencyAdapter = AdapterCurrencyList(ArrayList()).apply {
        onClickItem.subscribe{
            var key: View? = null
            var value: Int? = null
            it.forEach{ i ->
                key = i.key
                value = i.value
            }
            onClickItem(key!!, value!!)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateValue(){
        Common.valuteApi.valuteItem()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("TAG", "Load")
                val list = HashMap<String, NewValue>()
                it.Valute.forEach { i ->
                    list[i.value.Name] = NewValue(
                        i.value.Value,
                        i.value.Previous)
                }
                currencyAdapter.cil.forEach{ i ->
                    val a = String.format(Locale.US,"%.2f",abs(list[i.name]!!.previous - list[i.name]!!.value)).toDouble()

                    val previousText = if (list[i.name]!!.previous < list[i.name]!!.value){
                        "Изменения: +(${a})"
                    }else{
                        "Изменения: -(${a})"
                    }
                    i.value = list[i.name]!!.value
                    i.previous = list[i.name]!!.previous
                    i.valueText = "Сейчас: ${list[i.name]!!.value}"
                    i.previousText = previousText
                }
            }, {
                throw Exception(it.message)

            })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadList(){
        Common.valuteApi.valuteItem()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("TAG", "Load")
                val list = ArrayList<CurrencyItem>()
                it.Valute.forEach { i ->
                    val a = String.format(Locale.US,"%.2f",abs(i.value.Previous - i.value.Value)).toDouble()
                    val previousText = if (i.value.Previous < i.value.Value){
                        "Изменения: +(${a})"
                    }else{
                        "Изменения: -(${a})"
                    }
                    list.add(
                        CurrencyItem(
                            i.value.Name,
                            i.value.CharCode,
                            i.value.Nominal,
                            i.value.Value,
                            i.value.Previous,
                            "Стоимось: ${i.value.Value}",
                            previousText,
                            "Номинал: ${i.value.Nominal}"
                        )
                    )
                }
                list.sortBy { i -> i.charCode }
                currencyAdapter.cil.addAll(list)
                currencyAdapter.notifyDataSetChanged()
                isRefreshing.set(false)
            }, {
                throw Exception(it.message)
            })
    }

    fun onRefresh(){
        isRefreshing.set(true)
        currencyAdapter.cil.clear()
        loadList()
    }

    private fun onClickItem(v: View, id: Int){
    }
}