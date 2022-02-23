package com.asurspace.handlerloopermainthread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.asurspace.handlerloopermainthread.contract.ICustomTitleProvider
import com.asurspace.handlerloopermainthread.databinding.FragmentSecondBinding

class SecondFragment : Fragment(), ICustomTitleProvider {

    private val handler = Handler(Looper.getMainLooper()) {
        Log.d(TAG, "Message Processing: ${it.what}")
        when (it.what) {
            MSG_SHOW_ACTION_BUTTON -> {
                changeButtonStatus()
            }
            MSG_CLICK_ON_BACK_PRESSED -> {
                requireActivity().onBackPressed()
            }
        }
        return@Handler true
    }

    private val abolitionMessage = "abortion"

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

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

    private fun changeButtonStatus() {
        if (_binding != null) {
            binding.actionMb.isEnabled = !binding.actionMb.isEnabled
        }
    }

    private val universalListener = View.OnClickListener {
        Thread {
            when (it.id) {
                R.id.en_dis -> {
                    val msg = handler.obtainMessage(MSG_SHOW_ACTION_BUTTON)
                    handler.sendMessage(msg)
                }
                R.id.en_dis_delayed_mb -> {
                    val msg = Message.obtain(handler, MSG_SHOW_ACTION_BUTTON)
                    handler.sendMessageDelayed(msg, 2000)
                }
                R.id.en_dis_mb_wa -> {
                    val msg = handler.obtainMessage(MSG_SHOW_ACTION_BUTTON)
                    msg.obj = abolitionMessage
                    handler.sendMessageDelayed(msg, 2000)
                }
                R.id.en_dis_mb_without_handler_processing -> {
                    val msg = Message.obtain(handler){
                        Log.d(TAG, "Change button status without handler processing")
                        changeButtonStatus()
                    }
                    handler.sendMessageDelayed(msg, 2000)
                }

                R.id.abolition_mb -> handler.removeCallbacksAndMessages(abolitionMessage)

                R.id.back_mb -> {
                    val msg = handler.obtainMessage(MSG_CLICK_ON_BACK_PRESSED)
                    handler.sendMessage(msg)
                }
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        private val TAG = SecondFragment::class.java.simpleName

        @JvmStatic
        private val MSG_SHOW_ACTION_BUTTON = 1

        @JvmStatic
        private val MSG_CLICK_ON_BACK_PRESSED = 2

        @JvmStatic
        fun newInstance() =
            SecondFragment().apply {
                arguments = bundleOf(

                )
            }
    }

    override fun provideTitle() = R.string.second_variant_handler_text
}