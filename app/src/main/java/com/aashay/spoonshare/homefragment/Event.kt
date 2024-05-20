import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp


data class Event(
    val eventDescription: String,
    val eventGeopoint: ParcelableGeoPoint,
    val eventHost: String,
    val eventImage: String,
    val eventName: String,
    val eventTime: Timestamp,
    val eventVolunteers: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable(ParcelableGeoPoint::class.java.classLoader) ?: ParcelableGeoPoint(0.0, 0.0),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventDescription)
        parcel.writeParcelable(eventGeopoint, flags)
        parcel.writeString(eventHost)
        parcel.writeString(eventImage)
        parcel.writeString(eventName)
        parcel.writeParcelable(eventTime, flags)
        parcel.writeLong(eventVolunteers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}

data class ParcelableGeoPoint(val latitude: Double, val longitude: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableGeoPoint> {
        override fun createFromParcel(parcel: Parcel): ParcelableGeoPoint {
            return ParcelableGeoPoint(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableGeoPoint?> {
            return arrayOfNulls(size)
        }
    }
}
