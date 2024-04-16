package com.dicoding.asclepius.view.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.history.HistoryViewModelFactory
import com.dicoding.asclepius.view.article.ArticleActivity
import com.dicoding.asclepius.view.history.HistoryActivity
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.main.MainActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var history = HistoryEntity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.result)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        val factory = HistoryViewModelFactory.getInstanceHistory(this)
        val viewModel: HistoryViewModel by viewModels { factory }

        val uri = intent.getStringExtra(MainActivity.EXTRA_URI)
        val result = intent.getStringExtra(MainActivity.EXTRA_RESULT)



        history.result = result
        history.photo = uri

        binding.resultImage.setImageURI(uri?.toUri())
        binding.resultText.text = result


        binding.articleResButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, ArticleActivity::class.java)
            startActivity(intent)
        }

        binding.saveButton.setOnClickListener {
            history.let {
                viewModel.insert(it)
                Toast.makeText(this, "Berhasil menyimpan data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.historyResButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

}