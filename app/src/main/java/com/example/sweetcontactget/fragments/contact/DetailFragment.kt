package com.example.sweetcontactget.fragments.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sweetcontactget.data.DataObject
import com.example.sweetcontactget.util.Util
import com.example.sweetcontactget.databinding.FragmentDetailBinding

private const val ARG_SWEETIE_ID = "userId"

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var sweetieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sweetieId = it.getInt(ARG_SWEETIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val currentSweetieInfo = sweetieId?.let { DataObject.getSweetieInfo(it) }

        currentSweetieInfo?.let {sweetie ->
            binding.run {
                ivDetailProfile.setImageResource(sweetie.imgSrc)
                tvDetailName.text = sweetie.name
                tvDetailNumber.text = sweetie.number
                rbHeartRating.rating = sweetie.heart / 20.toFloat()
                tvDetailRelationship.text = sweetie.relationship
                tvDetailMemo.text = sweetie.memo

                tvDetailMessage.setOnClickListener {
                    sweetie.number.let { number ->
                        context?.let { ctx ->
                            Util.sendMessage(ctx, number)
                        }
                    }
                }

                tvDetailCall.setOnClickListener {
                    sweetie.number.let { number ->
                        context?.let { ctx ->
                            Util.callSweetie(ctx, number)
                        }
                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SWEETIE_ID, param1)
                }
            }
    }
}