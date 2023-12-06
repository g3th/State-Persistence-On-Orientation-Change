package com.statepersistenceonorientationchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.statepersistenceonorientationchange.databinding.ActivityMainBinding
import com.statepersistenceonorientationchange.databinding.LiveDataFragmentBinding


class LiveDataFragment: Fragment(R.layout.live_data_fragment) {

    private var binding: LiveDataFragmentBinding? = null
    private val gen = GenerateLetters()
    private var numbers = arrayListOf<Int>()
    private lateinit var liveData: GenerateLetters

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LiveDataFragmentBinding.inflate(inflater, container, false)
        liveData = ViewModelProvider(this)[GenerateLetters::class.java]

        if (savedInstanceState != null) {
            if (liveData.liveNumberList.value != null) {
                numbers = liveData.liveNumberList.value!!
            }
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().baseContext)

        if (prefs.getInt("TIMES_REDRAWN", 0) == 0){
            binding!!.redrawnText.text = getString(R.string.just_started)
        } else {
            binding!!.redrawnText.text = getString(R.string.redrawn,
                prefs.getInt("TIMES_REDRAWN",0).toString())
        }


        liveData.liveNumberList.observe(viewLifecycleOwner){
            binding!!.textView.text = liveData.liveNumberList.value.toString()
        }

        binding!!.generate.setOnClickListener {
            numbers.clear()
            numbers = gen.start()
            liveData.liveNumberList.value = numbers
        }

        binding!!.swap.setOnClickListener {
            if (numbers.isEmpty()){
                binding!!.textView.text = getString(R.string.no_nums)
            } else {
                liveData.liveNumberList.value = gen.change(numbers)
                binding!!.textView.text = liveData.liveNumberList.value.toString()
                numbers = liveData.liveNumberList.value!!
            }
        }

        binding!!.backButton.setOnClickListener {
            parentFragmentManager. beginTransaction()
                .replace(R.id.fragmentContainerView, OptionsMenu())
                .commit()
        }
    }

    override fun onSaveInstanceState(saveBundle: Bundle) {
        super.onSaveInstanceState(saveBundle)
        if (liveData.liveNumberList.value != null) {
            gen.save(saveBundle, liveData.liveNumberList.value!!)
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().baseContext)
        val temp = prefs.getInt("TIMES_REDRAWN", 0)

        prefs.edit().putInt("TIMES_REDRAWN", temp + 1).apply()
    }

    override fun onViewStateRestored(loadBundle: Bundle?) {
        super.onViewStateRestored(loadBundle)
        if (loadBundle != null) {
            if (liveData.liveNumberList.value != null) {
                liveData.liveNumberList.value = gen.load(loadBundle)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}