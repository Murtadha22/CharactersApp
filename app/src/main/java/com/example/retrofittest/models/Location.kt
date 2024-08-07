package com.example.retrofittest.models

import android.os.Parcel
import android.os.Parcelable

data class Location(
    val name: String,
    val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location = Location(parcel)
        override fun newArray(size: Int): Array<Location?> = arrayOfNulls(size)
    }
}
