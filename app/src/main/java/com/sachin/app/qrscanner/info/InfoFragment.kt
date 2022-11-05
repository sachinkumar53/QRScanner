package com.sachin.app.qrscanner.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sachin.app.qrscanner.BuildConfig
import com.sachin.app.qrscanner.R
import com.sachin.app.qrscanner.databinding.FragmentInfoBinding
import com.sachin.app.qrscanner.util.openDeveloperPage
import com.sachin.app.qrscanner.util.openPlayStore
import com.sachin.app.qrscanner.util.sendAppLink
import com.sachin.app.qrscanner.util.sendFeedback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appVersion.text = String.format("Version %s", BuildConfig.VERSION_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class InfoPrefFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.prefs_main, rootKey)

            findPreference<Preference>("rate")?.setOnPreferenceClickListener {
                requireActivity().openPlayStore()
                true
            }

            findPreference<Preference>("share")?.setOnPreferenceClickListener {
                requireContext().sendAppLink()
                true
            }

            findPreference<Preference>("feedback")?.setOnPreferenceClickListener {
                requireActivity().sendFeedback()
                true
            }

            findPreference<Preference>("more_apps")?.setOnPreferenceClickListener {
                requireActivity().openDeveloperPage()
                true
            }
        }
    }
}