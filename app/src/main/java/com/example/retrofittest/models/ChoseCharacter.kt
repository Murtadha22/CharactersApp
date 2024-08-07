package com.example.retrofittest.models.chosecharacter

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ChoseCharacter(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
) : Parcelable {
    data class Location(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
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

    data class Origin(
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
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

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Location::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readParcelable(Origin::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created)
        parcel.writeStringList(episode)
        parcel.writeString(gender)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeParcelable(location, flags)
        parcel.writeString(name)
        parcel.writeParcelable(origin, flags)
        parcel.writeString(species)
        parcel.writeString(status)
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ChoseCharacter> {
        override fun createFromParcel(parcel: Parcel): ChoseCharacter = ChoseCharacter(parcel)
        override fun newArray(size: Int): Array<ChoseCharacter?> = arrayOfNulls(size)
    }
}
