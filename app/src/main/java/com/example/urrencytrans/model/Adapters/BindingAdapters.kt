package com.example.urrencytrans.model.Adapters

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.urrencytrans.model.CurrencyItem
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("app:colorValue")
fun setColorValue(textView: TextView, item: CurrencyItem){
    if (item.previous < item.value){
        textView.setTextColor(Color.GREEN)
    }else{
        textView.setTextColor(Color.RED)
    }
}

@BindingAdapter("android:onKey")
fun onKey(v: TextInputEditText, m: View.OnKeyListener){
    v.setOnKeyListener(m)
}