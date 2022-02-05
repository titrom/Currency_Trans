package com.example.urrencytrans.model

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.urrencytrans.BR
import com.example.urrencytrans.MainActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class CurrencyItem (
    var name: String,
    var charCode: String,
    var nominal: Int,
    var value: Double,
    var previous: Double,
    var valueText: String,
    var previousText: String,
    var nominalText: String
    ) : BaseObservable() {

        @get:Bindable
        var textCurrency = "$nominal"
        set(text){
            field = text
            notifyPropertyChanged(BR.textCurrency)
        }

        @get:Bindable
        var textRub = "$value"
        set(text) {
            field = text
            notifyPropertyChanged(BR.textRub)
        }
        var onKeyCurrencyEdit = View.OnKeyListener { p0, p1, p2 ->
            if (p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER){
                val edit = p0 as TextInputEditText
                if (edit.text!!.isNotEmpty()) textRub =
                    "${String.format(Locale.US,"%.5f",
                        (edit.text.toString().toFloat() / nominal) * value).toDouble()}"
                edit.clearFocus()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        }
        var onKeyRubEdit = View.OnKeyListener { p0, p1, p2 ->
            if (p2.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER){
                val edit = p0 as TextInputEditText
                if (edit.text!!.isNotEmpty()) textCurrency =
                    "${String.format(Locale.US,"%.5f", 
                        edit.text.toString().toDouble() * nominal / value).toDouble()}"
                edit.clearFocus()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        }
    }