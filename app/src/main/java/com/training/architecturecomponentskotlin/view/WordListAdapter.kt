package com.training.architecturecomponentskotlin.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.training.architecturecomponentskotlin.R
import com.training.architecturecomponentskotlin.model.Word
import kotlinx.android.synthetic.main.list_item.view.*

class WordListAdapter(private val mContext: Context, private var mItemClickListener: ItemClickListener) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private lateinit var mWords: List<Word>

    fun getWords() = mWords

    fun setWords(words: List<Word>){
        mWords = words
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view:View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        var currentWord = mWords[position]
        holder.wordTextView.text = currentWord.name

        holder.wordTextView.setOnClickListener {
            mItemClickListener.onItemClick(holder.wordTextView, position)
        }
    }

    class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var wordTextView: TextView = itemView.findViewById(R.id.textView)
    }
}