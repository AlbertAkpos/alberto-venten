package me.alberto.albertoventen.util

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import me.alberto.albertoventen.R
import me.alberto.albertoventen.model.CarOwner
import me.alberto.albertoventen.model.FilterItem
import me.alberto.albertoventen.screens.carowner.CarOwnerAdapter
import me.alberto.albertoventen.screens.filter.FilterAdapter


@BindingAdapter("app:filterList")
fun setFilterItemRecyclerView(recyclerView: RecyclerView, list: List<FilterItem>?) {
    val adapter = recyclerView.adapter as FilterAdapter
    adapter.submitList(list)
}

@BindingAdapter("app:carOwners")
fun setCarOwnersRecyclerView(recyclerView: RecyclerView, list: List<CarOwner>?) {
    val adapter = recyclerView.adapter as CarOwnerAdapter
    adapter.submitList(list)
}


@BindingAdapter("app:loading")
fun showLoading(imageView: ImageView, status: LoadingState?) {

    Glide.with(imageView.context)
        .load(R.drawable.gear_gif)
        .into(imageView)

    imageView.visibility = when (status) {
        is Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("app:countries")
fun ChipGroup.addCountries(countries: List<String>?) {
    val chipGroup = this
    val inflator = LayoutInflater.from(this.context)

    val children = countries?.map { country ->
        val chip = inflator.inflate(R.layout.chip_layout, chipGroup, false) as Chip
        chip.text = country
        chip
    }
    chipGroup.removeAllViews()

    if (children != null) {
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }

}


@BindingAdapter("app:colors")
fun ChipGroup.addColours(colors: List<String>?) {
    val chipGroup = this
    val inflator = LayoutInflater.from(this.context)

    val children = colors?.map { color ->
        val chip = inflator.inflate(R.layout.chip_layout, chipGroup, false) as Chip
        chip.text = color
        chip.chipBackgroundColor = when (color) {
            Color.RED.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.red
                )
            )
            Color.AQUAMARINE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.aquamarine
                )
            )

            Color.BLUE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.blue
                )
            )

            Color.CRIMSON.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.crimson
                )
            )

            Color.FUSCIA.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.fushcia
                )
            )

            Color.GOLDENROD.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.goldenrod
                )
            )

            Color.GREEN.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.green
                )
            )

            Color.INDIGO.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.indigo
                )
            )

            Color.KHAKI.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.khaki
                )
            )

            Color.MAROON.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.maroon
                )
            )

            Color.MAUV.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.mauv
                )
            )

            Color.ORANGE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.orange
                )
            )

            Color.PINK.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.pink
                )
            )

            Color.PUCE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.puce
                )
            )

            Color.TEAL.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.teal
                )
            )

            Color.TURQUOISE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.turquoise
                )
            )

            Color.VIOLET.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.violet
                )
            )

            Color.YELLOW.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.yellow
                )
            )
            Color.PURPLE.color -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    R.color.purple
                )
            )

            else -> ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.white))
        }
        chip
    }
    chipGroup.removeAllViews()

    if (children != null) {
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }

}


@BindingAdapter("app:carColor")
fun Chip.setCarColor(color: String) {
    this.chipBackgroundColor = when (color) {
        Color.RED.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.red
            )
        )
        Color.AQUAMARINE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.aquamarine
            )
        )

        Color.BLUE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.blue
            )
        )

        Color.CRIMSON.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.crimson
            )
        )

        Color.FUSCIA.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.fushcia
            )
        )

        Color.GOLDENROD.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.goldenrod
            )
        )

        Color.GREEN.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.green
            )
        )

        Color.INDIGO.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.indigo
            )
        )

        Color.KHAKI.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.khaki
            )
        )

        Color.MAROON.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.maroon
            )
        )

        Color.MAUV.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.mauv
            )
        )

        Color.ORANGE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.orange
            )
        )

        Color.PINK.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.pink
            )
        )

        Color.PUCE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.puce
            )
        )

        Color.TEAL.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.teal
            )
        )

        Color.TURQUOISE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.turquoise
            )
        )

        Color.VIOLET.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.violet
            )
        )

        Color.YELLOW.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.yellow
            )
        )
        Color.PURPLE.color -> ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                R.color.purple
            )
        )
        else -> ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.white))
    }
}
