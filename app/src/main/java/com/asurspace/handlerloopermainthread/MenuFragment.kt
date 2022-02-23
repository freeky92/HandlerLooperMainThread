package com.asurspace.handlerloopermainthread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.asurspace.handlerloopermainthread.contract.ICustomTitleProvider
import com.asurspace.handlerloopermainthread.contract.navigate
import com.asurspace.handlerloopermainthread.databinding.FragmentMenuBinding

class MenuFragment : Fragment(), ICustomTitleProvider {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fisrtMb.setOnClickListener {
            navigate().showFirstHandlerVariantFragment()
        }
        binding.secondMb.setOnClickListener {
            navigate().showSecondHandlerVariantFragment()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        private val TAG = MenuFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =
            MenuFragment().apply {
                arguments = bundleOf(

                )
            }
    }

    override fun provideTitle() = R.string.main_menu
}