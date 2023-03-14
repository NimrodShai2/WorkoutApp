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
                Toast.makeText(
                    this,
                    resources.getString(R.string.validation_basic),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun displayResult(bmi: Double) {
        val label: String
        val description: String

        if (bmi <= 15.0) {
            label = resources.getString(R.string.extremley_under_lable)
            description = resources.getString(R.string.extremley_under_des)
        } else if (15.0 < bmi && bmi <= 18.5) {
            label = resources.getString(R.string.under_label)
            description = resources.getString(R.string.under_des)
        } else if (18.5 < bmi && bmi <= 25.0) {
            label = resources.getString(R.string.normal_label)
            description = resources.getString(R.string.normal_des)
        } else if (25.0 < bmi && bmi <= 30.0) {
            label = resources.getString(R.string.over_label)
            description = resources.getString(R.string.over_des)
        } else if (30.0 < bmi && bmi <= 35.0) {
            label = resources.getString(R.string.mod_ob_label)
            description = resources.getString(R.string.mod_ob_des)
        } else if (35.0 < bmi && bmi <= 40.0) {
            label = resources.getString(R.string.sev_ob_lable)
            description = resources.getString(R.string.sev_ob_des)
        } else {
            label = resources.getString(R.string.ext_ob_label)
            description = resources.getString(R.string.ext_ob_des)
        }

        val bmiValue = BigDecimal(bmi).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
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