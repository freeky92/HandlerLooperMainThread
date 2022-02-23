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
import com.asurspace.handlerloopermainthread.databinding.FragmentFirstBinding

class FirstFragment : Fragment(), ICustomTitleProvider {

    private val handler = Handler(Looper.getMainLooper())

    private val abolitionMessage = "abortion"

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.forEach {
            if (it is Button) {
                it.setOnClickListener(universalListener)
            }
        }

    }

    private fun changeActionButtonStatus() {
        if (_binding != null) {
            binding.actionMb.isEnabled = !binding.actionMb.isEnabled
        }
    }

    private val universalListener = View.OnClickListener {
        Thread {
            when (it.id) {
                R.id.en_dis -> {
                    handler.post {
                        changeActionButtonStatus()
                    }
                }
                R.id.en_dis_delayed_mb -> {
                    handler.postDelayed({
                        changeActionButtonStatus()
                    }, 2000)
                }
                R.id.en_dis_mb_wa -> {
                    handler.postDelayed({
                        changeActionButtonStatus()
                    }, abolitionMessage, 2000)
                }

                R.id.abolition_mb -> handler.removeCallbacksAndMessages(abolitionMessage)
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        private val TAG = FirstFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =
            FirstFragment().apply {
                arguments = bundleOf(

                )
            }
    }

    override fun provideTitle() = R.string.first_variant_handler_text
}