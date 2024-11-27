package com.desafiolatam.weatherlatam.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.desafiolatam.weatherlatam.WeatherApplication
import com.desafiolatam.weatherlatam.databinding.DialogEditCityNameBinding
import com.desafiolatam.weatherlatam.extension.setupWidthToMatchParent
import com.desafiolatam.weatherlatam.view.viewmodel.WeatherViewModel
import com.desafiolatam.weatherlatam.view.viewmodel.WeatherViewModelFactory

class EditCityNameDialogFragment(private val weatherInfoId: Int) : DialogFragment() {



        private val viewModel: WeatherViewModel by viewModels {
            WeatherViewModelFactory((activity?.application as WeatherApplication).repository)
        }

        private lateinit var binding: DialogEditCityNameBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            binding = DialogEditCityNameBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupWidthToMatchParent()

            binding.btnCancel.setOnClickListener {
                Toast.makeText(requireContext(), "ID : " + weatherInfoId.toString(), Toast.LENGTH_SHORT).show()
                dismiss()
            }

            binding.btnSave.setOnClickListener {
                val cityName = binding.etCityName.text.toString()
                if (cityName.isNotEmpty()) {

                    saveCityName(cityName)
                } else {
                    Toast.makeText(requireContext(), "Por favor, ingresa un nombre de ciudad" , Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun saveCityName(cityName: String) {
            // Asegúrate de que la operación de guardar se ejecute en el contexto correcto
            lifecycleScope.launchWhenCreated {
              //  weatherInfoId
               // var value =   viewModel.getWeatherDataById(weatherInfoId)

                viewModel.saveCityName(cityName,weatherInfoId )
                dismiss() // Cierra el diálogo después de guardar el nombre
            }
        }
    }