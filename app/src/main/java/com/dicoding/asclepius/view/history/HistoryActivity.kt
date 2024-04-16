package com.dicoding.asclepius.view.history

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.history)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        val factory = HistoryViewModelFactory.getInstanceHistory(this)
        val viewModel: HistoryViewModel by viewModels { factory }

        val adapter = HistoryAdapter()
        val layoutManager = LinearLayoutManager(this)

        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = layoutManager

        adapter.onClickDelete = {
            viewModel.delete(it)
        }

        binding.progressIndicator.visibility = View.VISIBLE
        viewModel.getHistory().observe(this) {
            adapter.submitList(it)
            binding.progressIndicator.visibility = View.GONE
        }

    }
}