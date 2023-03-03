package my.nimrodshai.workoutapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import my.nimrodshai.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate BMI"
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetric()) {
                val height: Double = binding?.etMetricUnitHeight?.text.toString().toDouble() / 100
                val weight: Double = binding?.etMetricUnitWeight?.text.toString().toDouble()
                val bmi = weight / (height * height)
                displayResult(bmi)
            } else {
                Toast.makeText(this, "Please enter a valid value", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayResult(bmi: Double) {
        val label: String
        val description: String

        if (bmi <= 15.0) {
            label = "Severely Underweight"
            description = "You must eat more and gain some weight!"
        } else if (15.0 < bmi && bmi <= 18.5) {
            label = "Underweight"
            description = "Almost there! but you still need to gain some weight!"
        } else if (18.5 < bmi && bmi <= 25.0) {
            label = "Normal"
            description = "Great! You are at the normal level, and you probably are in good shape!"
        } else if (25.0 < bmi && bmi <= 30.0) {
            label = "Overweight"
            description = "You are overweight. You need to workout more and lose some weight."
        } else if (30.0 < bmi && bmi <= 35.0) {
            label = "Moderately Obese"
            description =
                "You are obese. Though not life threatening, you need to workout more and lose some weight."
        }
        else if (35.0 < bmi && bmi <= 40.0){
            label = "Severely Obese"
            description = "You are obese. You need to lose weight immediately, as it might be dangerous."
        }
        else{
            label = "Extremely Obese"
            description = "You are in a very dangerous state. Start losing weight and see to your doctor."
        }

        val bmiValue = BigDecimal(bmi).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = label
        binding?.tvBMIDescription?.text = description
    }

    private fun validateMetric(): Boolean {
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
}