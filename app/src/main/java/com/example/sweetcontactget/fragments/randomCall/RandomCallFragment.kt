package com.example.sweetcontactget.fragments.RandomCall

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.sweetcontactget.R
import com.example.sweetcontactget.databinding.FragmentRandomCallBinding
import com.example.sweetcontactget.dialog.RandomCallDialog
import kotlinx.coroutines.delay

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RandomCallFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentRandomCallBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomCallBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomCall.setOnClickListener {
            binding.ivRandomCallMain.visibility = View.INVISIBLE

            loadGif(R.raw.gacha1, binding.ivFirstGif)
            binding.ivFirstGif.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({

                if(activity != null){

                    binding.ivFirstGif.visibility = View.INVISIBLE
                    loadGif(R.raw.gacha2, binding.ivSecondGif)

                }
                //TODO 전화걸기 intent
//            binding.ivRandomCallMain.visibility = View.VISIBLE

            }, 2000)


            //Dialog 열기, delay 추가
            Handler(Looper.getMainLooper()).postDelayed({
            val dialog = RandomCallDialog(requireContext())
            dialog.show()
            }, 6000)

        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RandomCallFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadGif(loadGif: Int, imageView: ImageView) {
            Glide.with(this).asGif().load(loadGif)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: GifDrawable?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.setLoopCount(1)
                        return false
                    }

                }).into(imageView)
    }

}