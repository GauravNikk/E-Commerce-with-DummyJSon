package com.gaurav.testapp.model
import android.os.Parcelable
import android.os.Parcel

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val thumbnail: String
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readDouble(),
                parcel.readString()!!
            )
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeDouble(price)
        parcel.writeString(thumbnail)
    }

}
data class ProductResponse(
    val products: List<Product>
)

