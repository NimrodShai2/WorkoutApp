package my.nimrodshai.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.nimrodshai.workoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarFinishActivity?.setNavigationOnClickListener { onBackPressed() }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }
    }
}