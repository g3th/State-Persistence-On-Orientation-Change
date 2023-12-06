package com.statepersistenceonorientationchange

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.statepersistenceonorientationchange.databinding.PreferencesFragmentBinding

class SharedPreferencesFragment: Fragment(R.layout.preferences_fragment) {

    private var binding: PreferencesFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PreferencesFragmentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().baseContext)
        val name = prefs.getString("NAME", "None")
        val phone = prefs.getString("PHONE", "None")

        if (name == "None") {
            binding!!.whatDoPls.text = getString(R.string.enter_name)
        } else if (phone == "None"){
            binding!!.infoView.text = name
            binding!!.nameBox.visibility = View.INVISIBLE
            binding!!.phoneBox.visibility = View.VISIBLE
            binding!!.whatDoPls.text = getString(R.string.enter_number)
        } else {
            binding!!.infoView.text = name
            binding!!.displayInfoBox.text = phone
            binding!!.nameBox.visibility = View.INVISIBLE
            binding!!.phoneBox.visibility = View.INVISIBLE
            binding!!.whatDoPls.visibility = View.INVISIBLE
        }

        binding!!.nameBox.setOnKeyListener { _, event, _ ->
            if (event == KeyEvent.KEYCODE_ENTER){
                binding!!.nameBox.visibility = View.INVISIBLE
                binding!!.phoneBox.visibility = View.VISIBLE
                binding!!.whatDoPls.text = getString(R.string.enter_number)
                prefs.edit().putString("NAME", binding!!.nameBox.text.toString()).apply()
                binding!!.infoView.text = prefs.getString("NAME", "None").toString()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding!!.phoneBox.setOnKeyListener { _, event, value ->
            if (event == KeyEvent.KEYCODE_ENTER){
                binding!!.phoneBox.visibility = View.INVISIBLE
                prefs.edit().putString("PHONE", binding!!.phoneBox.text.toString()).apply()
                binding!!.whatDoPls.visibility = View.INVISIBLE
                binding!!.displayInfoBox.text = prefs.getString("PHONE", "None").toString()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }


        binding!!.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, OptionsMenu())
                .commit()
        }
    }
}