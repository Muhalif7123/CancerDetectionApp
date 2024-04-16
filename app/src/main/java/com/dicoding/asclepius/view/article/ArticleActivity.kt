package com.dicoding.asclepius.view.article

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityArticleBinding
import com.dicoding.asclepius.helper.Result

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.article)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        val factory = ArticleViewModelFactory.getInstanceArticle(this)
        val viewModel: ArticleViewModel by viewModels { factory }

        val adapter = ArticleAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvArticle.adapter = adapter
        binding.rvArticle.layoutManager = layoutManager

        viewModel.getArticle().observe(this) {
            when (it) {
                is Result.Failure -> {
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    binding.progressIndicator.visibility = View.GONE
                }

                is Result.Success -> {
                    adapter.submitList(it.data)
                    binding.progressIndicator.visibility = View.GONE

                }

                is Result.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        }
    }
}