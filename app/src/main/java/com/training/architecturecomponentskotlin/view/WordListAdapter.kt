package com.training.architecturecomponentskotlin.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.training.architecturecomponentskotlin.databinding.ListItemBinding
import com.training.architecturecomponentskotlin.model.Word

class WordListAdapter(
    private val mContext: Context,
    private var mItemClickListener: ItemClickListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private lateinit var mWords: List<Word>

    private lateinit var binding: ListItemBinding

    fun getWords() = mWords

    fun setWords(words: List<Word>) {
        mWords = words
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return WordViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return mWords.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = mWords[position]
        holder.wordTextView.text = currentWord.name

        holder.wordTextView.setOnClickListener {
            mItemClickListener.onItemClick(holder.wordTextView, position)
        }
    }

    class WordViewHolder(binding: ListItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        var wordTextView: TextView = binding.textView
    }
}