package mhsn.wa_notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mhsn.wa_notes.R

class TopicListAdapter(private var topics: List<String>) : RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.textView.text = topics[position]
    }

    override fun getItemCount(): Int = topics.size

    fun updateTopics(newTopics: List<String>) {
        topics = newTopics
        notifyDataSetChanged()
    }

    class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewTopicName)
    }
}
