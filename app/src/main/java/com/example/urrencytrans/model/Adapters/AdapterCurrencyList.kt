package com.example.urrencytrans.model.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.urrencytrans.databinding.CurrencyItemBinding
import com.example.urrencytrans.model.CurrencyItem
import io.reactivex.rxjava3.subjects.PublishSubject

class AdapterCurrencyList(var cil: ArrayList<CurrencyItem>)
    : RecyclerView.Adapter<AdapterCurrencyList.HolderCurrencyAdapter>(){

    var onClickItem: PublishSubject<HashMap<View, Int>> = PublishSubject.create()

    class HolderCurrencyAdapter(view: View) : RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<CurrencyItemBinding>(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCurrencyAdapter {
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderCurrencyAdapter(binding.root)
    }

    override fun onBindViewHolder(holder: HolderCurrencyAdapter, position: Int) {
        holder.binding?.item = cil[position]
        holder.itemView.setOnClickListener {
            val onNextItem = HashMap<View, Int>()
            Log.d("TAG", position.toString())
            onNextItem[it] = position
            onClickItem.onNext(onNextItem)
        }
    }

    override fun getItemCount(): Int = cil.size
}