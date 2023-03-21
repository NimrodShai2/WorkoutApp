package my.nimrodshai.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import my.nimrodshai.workoutapp.databinding.ActivityHistoryBinding
import my.nimrodshai.workoutapp.db.HistoryDao
import my.nimrodshai.workoutapp.db.WorkoutApp

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.history)
        binding?.tbHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllDates(dao)
    }

    private fun getAllDates(historyDao: HistoryDao) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            println(throwable.message)
            binding?.tvHistory?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.GONE
            binding?.tvNoDataAvailable?.visibility = View.VISIBLE
            binding?.tvNoDataAvailable?.text = resources.getString(R.string.error_msg)

        }
        lifecycleScope.launch(handler) {
            historyDao.getAllDates().collect {
                if (it.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for (date in it) {
                        dates.add(date.date)
                    }
                    val adapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = adapter
                } else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}