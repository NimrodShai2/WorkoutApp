package my.nimrodshai.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import my.nimrodshai.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExcersizeActivity::class.java)
            startActivity(intent)
            
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}