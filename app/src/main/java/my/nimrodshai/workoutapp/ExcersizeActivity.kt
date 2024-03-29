package my.nimrodshai.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import my.nimrodshai.workoutapp.databinding.ActivityExcersizeBinding
import my.nimrodshai.workoutapp.databinding.DialogBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExcersizeActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExcersizeBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restDurationSeconds = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseDurationSeconds = 0
    private var exerciseList: ArrayList<Exercise>? = null
    private var exercisePosition = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcersizeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.tbExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        exerciseList = Constants.defaultExerciseList()
        restDurationSeconds = intent.getIntExtra("REST_DURATION", 5)
        exerciseDurationSeconds = intent.getIntExtra("EXER_DURATION", 10)
        tts = TextToSpeech(this, this)


        setUpRestView()
        setUpStatusRecyclerView()
    }

    private fun dialogForBackButton() {
        val dialog = Dialog(this)
        val dialogBinding = DialogBackConfirmationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExcersizeActivity.finish()
            dialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun setUpStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    override fun onBackPressed() {
        dialogForBackButton()
    }

    private fun setUpRestView() {
        binding?.flExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.GONE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvNextExerciseName?.visibility = View.VISIBLE
        if (exercisePosition < exerciseList?.size!! - 1) {
            val nextPos = exercisePosition + 1
            binding?.tvNextExerciseName?.text = exerciseList!![nextPos].name
        }
        binding?.tvTitle?.text = getText(R.string.Excersize_title)
        binding?.flRestView?.visibility = View.VISIBLE
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestPB()
    }

    private fun setUpExerciseView() {

        try {
            val soundURI =
                Uri.parse(
                    "android.resource://my.nimrodshai.workoutapp/"
                            + R.raw.press_start
                )
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.GONE
        binding?.tvNextExerciseName?.visibility = View.GONE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speak(exerciseList!![exercisePosition].name)

        binding?.ivImage?.setImageResource(exerciseList!![exercisePosition].image)
        binding?.tvExerciseName?.text = exerciseList!![exercisePosition].name

        setExercisePB()
    }

    private fun setRestPB() {
        binding?.pbProgress?.progress = restProgress

        restTimer = object : CountDownTimer(
            (restDurationSeconds * 1000).toLong(),
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.pbProgress?.progress = restDurationSeconds - restProgress
                binding?.tvTimer?.text = (restDurationSeconds - restProgress).toString()
                countToZero(millisUntilFinished)
            }

            override fun onFinish() {
                exercisePosition++

                exerciseList!![exercisePosition].isSelected = true
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }

        }.start()
    }

    private fun setExercisePB() {
        binding?.pbExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(
            (exerciseDurationSeconds * 1000).toLong(),
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.pbExercise?.progress = exerciseDurationSeconds - exerciseProgress
                binding?.tvExerciseInFrame?.text =
                    (exerciseDurationSeconds - exerciseProgress).toString()
                countToZero(millisUntilFinished)
            }

            override fun onFinish() {

                if (exercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![exercisePosition].isSelected = false
                    exerciseList!![exercisePosition].isCompleted = true
                    exerciseAdapter!!.notifyDataSetChanged()
                    speak("Get some rest!")
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(
                        this@ExcersizeActivity,
                        FinishActivity::class.java
                    )
                    startActivity(intent)

                }
            }


        }.start()
    }

    private fun countToZero(millisUntilFinished: Long) {
        when (millisUntilFinished.toInt() / 1000) {
            5 -> speak("5")
            4 -> speak("4")
            3 -> speak("3")
            2 -> speak("2")
            1 -> speak("1")
            10 -> speak("Ten Seconds to go!")
            20 -> speak("Twenty Seconds to go!")
            30 -> speak("30 Seconds to go!")
            60 -> speak("One Minute!")
            90 -> speak("90 Seconds!")
            120 -> speak("Two Minutes!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null) {
            player?.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Snackbar.make(
                    binding?.root as View, "Please select a valid language",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            Snackbar.make(
                binding?.root as View, "Oops! Text-to-speech failed!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}