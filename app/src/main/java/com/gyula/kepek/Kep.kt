package com.gyula.kepek


import android.os.Parcel
import android.os.Parcelable


class   Kep(var albumId: String?, var Id: String?, var title: String?, var url: String?,
            var thumbnailUrl: String?) : Parcelable {

    override fun toString(): String {
        return "Kep(albumId=$albumId, Id=$Id, title=$title, url=$url, thumbnailUrl=$thumbnailUrl)"
    }


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(albumId)
        parcel.writeString(Id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Kep> {
        override fun createFromParcel(parcel: Parcel): Kep {
            return Kep(parcel)
        }

        override fun newArray(size: Int): Array<Kep?> {
            return arrayOfNulls(size)
        }
    }

}