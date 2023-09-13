package com.example.spentperliterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.spentperliterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCalculateMainActivity.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_calculate_main_activity) calculate()
    }

    private enum class EnumDurationToast {
        short, long
    }

    private fun showToast(text: String, duration: EnumDurationToast) {
        val durationToast = if (duration == EnumDurationToast.short) {
            Toast.LENGTH_SHORT
        } else {
            Toast.LENGTH_LONG
        }
        Toast.makeText(this, text, durationToast).show()
    }

    private fun validateInfos(): Boolean {
        val distance = binding.editDistanceMainActivity.text.toString()
        val price = binding.editPricePerLiterActivity.text.toString()
        val autonomy = binding.editAutonomyVehicleMainActivity.text.toString()
        if (distance == "") {
            showToast(getString(R.string.validation_fill_distance_field), EnumDurationToast.short)
            return false
        }
        if (price == "") {
            showToast(getString(R.string.validation_fill_price_field), EnumDurationToast.short)
            return false
        }
        if (autonomy == "") {
            showToast(getString(R.string.validation_fill_autonomy_field), EnumDurationToast.short)
            return false
        }
        try {
            val distance = binding.editDistanceMainActivity.text.toString().toFloat()
            val price = binding.editPricePerLiterActivity.text.toString().toFloat()
            val autonomy = binding.editAutonomyVehicleMainActivity.text.toString().toFloat()
        } catch (e: Exception) {
            showToast(getString(R.string.validation_error_invalid_values), EnumDurationToast.long)
            return false
        }
        if (autonomy.toFloat() == 0f) {
            showToast(getString(R.string.validation_invalid_zero_autonomy), EnumDurationToast.short)
            return false
        }
        return true
    }

    private fun calculate() {
        if (!validateInfos()) return

        val distance = binding.editDistanceMainActivity.text.toString().toFloat()
        val price = binding.editPricePerLiterActivity.text.toString().toFloat()
        val autonomy = binding.editAutonomyVehicleMainActivity.text.toString().toFloat()

        val roundTripMultiplier = if (binding.switchRoundTrip.isChecked) 2 else 1
        val result = (distance * price / autonomy) * roundTripMultiplier

        binding.textFinalCostMainActivity.text = "R$ ${"%.2f".format(result)}"
    }
}