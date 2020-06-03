package me.alberto.albertoventen.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


typealias Filters = List<FilterItem>

@Parcelize
@JsonClass(generateAdapter = true)
data class FilterItem(
    val id: Long,
    @Json(name = "start_year")
    val startYear: Long,
    @Json(name = "end_year")
    val endYear: Long,
    val gender: String,
    val countries: List<String>,
    val colors: List<String>
) : Parcelable