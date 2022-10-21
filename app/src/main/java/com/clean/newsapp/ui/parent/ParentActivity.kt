package com.clean.newsapp.ui.parent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.clean.newsapp.databinding.ActivityParentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
