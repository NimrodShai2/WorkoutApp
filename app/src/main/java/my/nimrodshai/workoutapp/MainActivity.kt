package my.nimrodshai.workoutapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import my.nimrodshai.workoutapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : LocaleAwareCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private var currentDialogPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExcersizeActivity::class.java)
            startActivity(intent)
            
        }

        binding?.flBmi?.setOnClickListener{
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)

        }

        binding?.flHistory?.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding?.btnLg?.setOnClickListener {
            openLanguageDialog()
        }
    }

    private fun openLanguageDialog() {
        val list = Constants.languages
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose_language))
        builder.setSingleChoiceItems(list, 0
        ) { _, p1 -> currentDialogPosition = p1 }
        builder.setPositiveButton("Apply") { dialog, _ ->
            when (currentDialogPosition) {
                0 -> {
                    updateLocale(Locale.ENGLISH)
                }
                1 -> {
                    updateLocale(Locale("iw"))
                }
                else -> {
                    updateLocale(Locale("es"))
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel"){
                dialog, _ -> dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}