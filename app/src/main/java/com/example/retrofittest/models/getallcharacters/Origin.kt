package com.example.retrofittest.models.getallcharacters

import android.os.Parcel
import android.os.Parcelable

data class Origin(
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

    companion object CREATOR : Parcelable.Creator<Origin> {
        override fun createFromParcel(parcel: Parcel): Origin = Origin(parcel)
        override fun newArray(size: Int): Array<Origin?> = arrayOfNulls(size)
    }
}
