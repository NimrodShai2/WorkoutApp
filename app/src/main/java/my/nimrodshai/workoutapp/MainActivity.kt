package my.nimrodshai.workoutapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import my.nimrodshai.workoutapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : LocaleAwareCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private var currentLanguageDialogPosition = 0
    private var currentDurationDialogPosition = 0
    private var exerciseDurationSec = 30
    private var restDurationSec = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExcersizeActivity::class.java)
            intent.putExtra("REST_DURATION", restDurationSec)
            intent.putExtra("EXER_DURATION", exerciseDurationSec)
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
        binding?.btnInterval?.setOnClickListener {
            openDurationDialog()
        }
    }

    private fun openLanguageDialog() {
        val list = arrayOf(resources.getString(R.string.english),
            resources.getString(R.string.hebrew),
            resources.getString(R.string.spanish))
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose_language))
        builder.setSingleChoiceItems(list, currentLanguageDialogPosition
        ) { _, p1 -> currentLanguageDialogPosition = p1 }
        builder.setPositiveButton(resources.getString(R.string.apply)) { dialog, _ ->
            when (currentLanguageDialogPosition) {
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
        builder.setNegativeButton(resources.getString(R.string.cancel)){
                dialog, _ -> dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun openDurationDialog() {
        val list = arrayOf(resources.getString(R.string.quick_workout),
            resources.getString(R.string.medium_workout),
            resources.getString(R.string.long_workout))
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.choose_int))
        builder.setSingleChoiceItems(list, currentDurationDialogPosition
        ) { _, p1 -> currentDurationDialogPosition = p1 }
        builder.setPositiveButton(resources.getString(R.string.apply)) { dialog, _ ->
            when (currentDurationDialogPosition) {
                0 -> {
                    restDurationSec = 10
                    exerciseDurationSec = 30
                }
                1 -> {
                    restDurationSec = 20
                    exerciseDurationSec = 60
                }
                else -> {
                    restDurationSec = 30
                    exerciseDurationSec = 120
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.cancel)){
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