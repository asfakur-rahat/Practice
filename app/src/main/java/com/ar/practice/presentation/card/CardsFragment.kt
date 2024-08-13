package com.ar.practice.presentation.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ar.practice.R
import com.ar.practice.adapter.card.CardActionAdapter
import com.ar.practice.data.local.demo.DemoData
import com.ar.practice.databinding.FragmentCardsBinding
import com.ar.practice.databinding.LayoutCardSliderBinding
import com.ar.practice.utils.beGone
import com.ar.practice.utils.beVisible
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class CardsFragment : Fragment() {

    private var _binding: FragmentCardsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CardActionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCarousel()
        initAdapter()
        initActions()
        initListener()
    }

    private fun initListener() {
        binding.btnCardDetails.setOnClickListener {
            setUpPopUp()
            showPopup()
        }
        binding.popUp.btnClose.setOnClickListener {
            hidePopup()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showPopup() {
        binding.popUp.root.apply {
            alpha = 0f
            beVisible()
            animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(null)
        }
        binding.nestedScrollView.setOnTouchListener{ _, _ ->
            true
        }
        binding.rvCardActions.setOnTouchListener{ _, _ ->
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hidePopup() {
        binding.popUp.root.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    binding.popUp.root.beGone()
                }
            })
        binding.nestedScrollView.setOnTouchListener(null)
        binding.rvCardActions.setOnTouchListener(null)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpPopUp() {
        binding.popUp.name.tvValue.text = "Md Asfakur Rahat"
        binding.popUp.number.tvValue.text = "**** **** **** 3456"
        binding.popUp.expire.tvValue.text = "**/**"
        binding.popUp.code.tvValue.text = "***"
        binding.popUp.address.text = "246 bolton wood, Toronto, Canada"

        binding.popUp.name.btnAction.text = "Copy"
        binding.popUp.number.btnAction.text = "Show"
        binding.popUp.expire.btnAction.text = "Show"
        binding.popUp.code.btnAction.text = "Show"

        binding.popUp.name.btnAction.setOnClickListener {
            //binding.popUp.number.tvValue.text = "000 111 222 3456"
        }
        binding.popUp.number.btnAction.setOnClickListener {
            binding.popUp.number.tvValue.text = "0000 1111 2222 3456"
        }
        binding.popUp.expire.btnAction.setOnClickListener {
            binding.popUp.expire.tvValue.text = "25/30"
        }
        binding.popUp.code.btnAction.setOnClickListener {
            binding.popUp.code.tvValue.text = "357"
        }
    }

    private fun initAdapter() {
        adapter = CardActionAdapter { action ->
            Toast.makeText(requireContext(), action.action, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initActions() {
        binding.rvCardActions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCardActions.adapter = adapter
        adapter.submitList(DemoData.cardActions)
    }

    private fun initCarousel() {
        val carousel = binding.carousel

        carousel.registerLifecycle(viewLifecycleOwner)

        carousel.carouselListener = object : CarouselListener {

            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup,
            ): ViewBinding = LayoutCardSliderBinding.inflate(layoutInflater, parent, false)

            override fun onBindViewHolder(
                binding: ViewBinding,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as LayoutCardSliderBinding
                currentBinding.ivCards.apply {
                    setImage(item)
                }
                currentBinding.tvCardNumber.text = item.caption

                currentBinding.root.setOnClickListener {
                    Toast.makeText(requireContext(), "Card Clicked + ${item.caption}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.ic_cards,
                caption = "Expense Card **** 3456"
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.ic_cards,
                caption = "Expense Card **** 7890"
            )
        )

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.ic_cards,
                caption = "Expense Card **** 3567"
            )
        )

        carousel.setData(list)
    }

}