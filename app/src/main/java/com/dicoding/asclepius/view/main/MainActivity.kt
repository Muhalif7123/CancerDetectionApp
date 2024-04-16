package com.dicoding.asclepius.view.main

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.uriToFile
import com.dicoding.asclepius.view.result.ResultActivity
import com.dicoding.asclepius.view.article.ArticleActivity
import com.dicoding.asclepius.view.history.HistoryActivity
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private var currentImageUri: Uri? = null
    private var result: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.historyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.articleButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ArticleActivity::class.java)
            startActivity(intent)
        }
        binding.analyzeButton.setOnClickListener {
            if (currentImageUri == null) {
                showToast(getString(R.string.no_data))
            } else {
                currentImageUri?.let { uri ->
                    analyzeImage(uri)
                }
                moveToResult()
            }

        }


        binding.cropButton.setOnClickListener {
            if (currentImageUri == null) {
                showToast(getString(R.string.choose_image_first))
            }
            currentImageUri?.let { uri ->
                cropImage(uri)
            }
        }
    }

    private val resultGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            currentImageUri = it
            cropImage(currentImageUri!!)
        }
    }

    private val cropImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val result = UCrop.getOutput(it.data!!)
            if (result != null) {
                val bitmap = BitmapFactory.decodeFile(result.encodedSchemeSpecificPart)
                binding.previewImageView.setImageBitmap(bitmap)
                currentImageUri = result
            }

        } else if (it.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(it.data!!)
            Log.e(TAG, error.toString())
        }
    }


    private fun cropImage(imageUri: Uri): Intent {
        val destinationUri = Uri.fromFile(uriToFile(imageUri, this@MainActivity))
        val options = UCrop.Options().apply {
            setCompressionQuality(90)
            setFreeStyleCropEnabled(true)
        }
        val intent = UCrop.of(imageUri, destinationUri)
            .withOptions(options)
            .getIntent(this@MainActivity)

        cropImageLauncher.launch(intent)
        return intent
    }


    private fun startGallery() {
        resultGalleryLauncher.launch("image/*")
    }

    private fun analyzeImage(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ImageClassifierListener {
                override fun onResults(results: List<Classifications>?) {
                    runOnUiThread {
                        results?.let {

                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val sortedCategories =
                                    it[0].categories.sortedByDescending { it?.score }

                                result = sortedCategories.joinToString("\n") {
                                    "${it.label} " + NumberFormat.getPercentInstance()
                                        .format(it.score).trim()
                                }
                            }
                        }
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        showToast(error)
                    }
                }
            }
        )

        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_URI, currentImageUri.toString())
        intent.putExtra(EXTRA_RESULT, result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity"
        const val EXTRA_URI = "extra_uri"
        const val EXTRA_RESULT = "extra_result"
    }

}