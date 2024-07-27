package com.example.sweetcontactget.fragments.randomCall

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
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.databinding.FragmentRandomCallBinding
import com.example.sweetcontactget.dialog.CallingDialog


class RandomCallFragment : Fragment() {

    private var _binding: FragmentRandomCallBinding? = null
    private val binding get() = _binding!!

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


            binding.ivFirstGif.visibility = View.VISIBLE
            loadGif(R.raw.gacha1, binding.ivFirstGif)

            Handler(Looper.getMainLooper()).postDelayed({

                if (activity != null) {

                    binding.ivFirstGif.visibility = View.INVISIBLE
                    binding.ivSecondGif.visibility = View.VISIBLE

                    Glide.with(this).asBitmap().load(R.raw.gacha1).into(binding.ivFirstGif)

                    loadGif(R.raw.gacha2, binding.ivSecondGif)
                }

            }, 1500)

            //Dialog 열기, delay 추가
            Handler(Looper.getMainLooper()).postDelayed({
                val sweetieId = DataObject.getValidKeys().random()
                val dialog = CallingDialog(
                    requireContext(),
                    sweetieId,
                    binding.ivRandomCallMain,
                    binding.ivSecondGif
                )
                dialog.show()
                Glide.with(this).asBitmap().load(R.raw.gacha2).into(binding.ivSecondGif)
                binding.ivSecondGif.visibility = View.INVISIBLE
            }, 4000)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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