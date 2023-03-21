package my.nimrodshai.workoutapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import my.nimrodshai.workoutapp.databinding.ActivityFinishBinding
import my.nimrodshai.workoutapp.db.HistoryDao
import my.nimrodshai.workoutapp.db.HistoryEntity
import my.nimrodshai.workoutapp.db.WorkoutApp
import java.text.SimpleDateFormat
import java.util.*

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

        val dao = (application as WorkoutApp).db.historyDao()
        addEntryToDb(dao)
    }

    private fun addEntryToDb(historyDao: HistoryDao){
        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        val handler = CoroutineExceptionHandler{_, throwable ->
            println(throwable.message)
            Toast.makeText(this@FinishActivity,
                resources.getString(R.string.error_msg),
                Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch(handler){
            historyDao.insert(HistoryEntity(date))
            Toast.makeText(this@FinishActivity,
                resources.getString(R.string.entry_added),
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}