package com.ar.practice.utils.custom_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.ar.practice.R
import com.ar.practice.databinding.LayoutCustomSelectableBinding
import com.ar.practice.utils.setVisibility

class CustomInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCustomSelectableBinding = LayoutCustomSelectableBinding.inflate(LayoutInflater.from(context), this, true)

    var customLabel: String?
        get() = binding.tagLabel.text.toString()
        set(value) {
            binding.tagLabel.text = value
            binding.tagLabel.setVisibility(!value.isNullOrEmpty())
        }

    var customHint: String?
        get() = binding.hint.text.toString()
        set(value) {
            binding.hint.text = value
            binding.hint.setVisibility(!value.isNullOrEmpty())
        }

    var supportingText: String?
        get() = binding.tvInfo.text.toString()
        set(value) {
            binding.tvInfo.text = value
            binding.tvInfo.setVisibility(!value.isNullOrEmpty())
        }

    var mainText: String?
        get() = binding.mainText.text.toString()
        set(value) {
            binding.mainText.text = value
            binding.mainText.setVisibility(!value.isNullOrEmpty())
            binding.hint.setVisibility(value.isNullOrEmpty())
        }

    init {
        orientation = VERTICAL

        context.withStyledAttributes(attrs, R.styleable.CustomInputLayout) {
            customLabel = getString(R.styleable.CustomInputLayout_customLabel)
            customHint = getString(R.styleable.CustomInputLayout_customHint)
            supportingText = getString(R.styleable.CustomInputLayout_supportingText)
            mainText = getString(R.styleable.CustomInputLayout_mainText)

            getResourceId(R.styleable.CustomInputLayout_supportingIcon, 0).let { resId ->
                if (resId != 0) {
                    binding.ivInfo.setImageResource(resId)
                    binding.ivInfo.visibility = VISIBLE
                } else {
                    binding.ivInfo.visibility = GONE
                }
            }

            getResourceId(R.styleable.CustomInputLayout_trailingIcon, 0).let { resId ->
                if (resId != 0) {
                    binding.icon.setImageResource(resId)
                    binding.icon.visibility = VISIBLE
                } else {
                    binding.icon.visibility = GONE
                }
            }
        }
    }
}
