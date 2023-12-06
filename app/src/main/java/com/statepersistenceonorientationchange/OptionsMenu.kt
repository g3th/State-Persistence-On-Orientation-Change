package com.statepersistenceonorientationchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.statepersistenceonorientationchange.databinding.ActivityMainBinding
import com.statepersistenceonorientationchange.databinding.OptionsFragmentBinding

class OptionsMenu: Fragment(R.layout.options_fragment) {

    private var binding: OptionsFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = OptionsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().baseContext)
        // Reset all Preferences
        prefs.edit().putInt("TIMES_REDRAWN", 0).apply()
        prefs.edit().putString("NAME", "None").apply()
        prefs.edit().putString("PHONE", "None").apply()
        // Option Buttons
        binding!!.liveData.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LiveDataFragment(), "live_data_fragment").commit()

        }

        binding!!.sharedPreferences.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SharedPreferencesFragment()).commit()
        }
    }
}