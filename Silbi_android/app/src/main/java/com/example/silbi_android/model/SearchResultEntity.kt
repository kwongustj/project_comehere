package com.example.silbi_android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SearchResultEntity(
    val fullAddress: String,
    val name: String
):Parcelable