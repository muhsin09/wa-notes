package mhsn.wa_notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import mhsn.wa_notes.R
import mhsn.wa_notes.data.local.AppDatabase

class TopicListActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TopicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_list)

        recyclerView = findViewById(R.id.recyclerViewTopics)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TopicListAdapter(emptyList())
        recyclerView.adapter = adapter

        db = AppDatabase.getInstance(this)

        lifecycleScope.launch {
            val threads = db.threadDao().getAll()
            val topicTitles = threads.map { it.title }
            adapter.updateTopics(topicTitles)
        }
    }
}
