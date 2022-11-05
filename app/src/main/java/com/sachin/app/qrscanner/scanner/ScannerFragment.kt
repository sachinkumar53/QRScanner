package com.sachin.app.qrscanner.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.sachin.app.qrscanner.main.MainViewModel
import com.sachin.app.qrscanner.main.ResultFragment
import com.sachin.app.qrscanner.databinding.FragmentScannerBinding
import com.sachin.app.qrscanner.db.ScanResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val dialog = ResultFragment()
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeScanner = CodeScanner(requireContext(), binding.scannerView)

        binding.autoFocusButton.apply {
            isSelected = true
            setOnClickListener {
                it.isSelected = !it.isSelected
                codeScanner.isAutoFocusEnabled = it.isSelected
            }
        }

        /*dialog.onDismissCallback = {
            Log.d("Sachin", "onCreate: Dialog dismissed")
        }*/
        binding.flashButton.setOnClickListener {
            it.isSelected = !it.isSelected
            codeScanner.isFlashEnabled = it.isSelected
        }
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                if (it.text != null) {
                    viewModel.onResult(ScanResult(it.text))
                    dialog.show(childFragmentManager, "Hello")
                }
            }
        }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            requireActivity().runOnUiThread {
                Toast.makeText(
                    requireContext(),
                    "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()

    }
}