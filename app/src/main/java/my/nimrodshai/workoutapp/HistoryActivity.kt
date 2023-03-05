package my.nimrodshai.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.nimrodshai.workoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "History"
        binding?.tbHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}