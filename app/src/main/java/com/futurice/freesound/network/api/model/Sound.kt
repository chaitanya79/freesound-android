/*
 * Copyright 2017 Futurice GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.futurice.freesound.network.api.model

import android.os.Parcel
import com.futurice.freesound.common.utils.*
import com.petertackage.jonty.Fieldable
import com.squareup.moshi.Json
import java.util.*

/**
 * Refer to: http://www.freesound.org/docs/api/resources_apiv2.html#sound-resources
 */
@Fieldable
data class Sound(

        val id: Long,
        // The URI for this sound on the Freesound website.
        val url: String,
        // The name user gave to the sound.
        val name: String,
        // An array of tags the user gave to the sound.
        val tags: List<String>,
        // The description the user gave to the sound.
        val description: String,
        // Latitude and longitude of the geotag separated by spaces
        // (e.g. “41.0082325664 28.9731252193”, only for sounds that have been geotagged).
        val geotag: GeoLocation?,
        // The username of the uploader of the sound.
        val username: String,
        // Thumbnail image URLs of the waveform/spectral plot
        val images: Image,
        // Preview sounds URLs
        val previews: Preview,
        // Duration in seconds
        val duration: Float,
        val created: Date) : KParcelable {
    private constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            url = parcel.readString(),
            name = parcel.readString(),
            tags = parcel.readStringList()!!,
            description = parcel.readString(),
            geotag = parcel.readTypedObjectCompat(GeoLocation.CREATOR),
            username = parcel.readString(),
            images = parcel.readTypedObjectCompat(Image.CREATOR)!!,
            previews = parcel.readTypedObjectCompat(Preview.CREATOR)!!,
            duration = parcel.readFloat(),
            created = parcel.readDate()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(url)
        writeString(name)
        writeStringList(tags)
        writeString(description)
        writeTypedObjectCompat(geotag, flags)
        writeString(username)
        writeTypedObjectCompat(images, flags)
        writeTypedObjectCompat(previews, flags)
        writeFloat(duration)
        writeDate(created)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Sound)
    }
}

data class Image(

        @Json(name = "waveform_m")
        val medSizeWaveformUrl: String,

        @Json(name = "waveform_l")
        val largeSizeWaveformUrl: String,

        @Json(name = "spectral_m")
        val medSizeSpectralUrl: String,

        @Json(name = "spectral_l")
        val largeSizeSpectralUrl: String?) : KParcelable {
    private constructor(parcel: Parcel) : this(
            medSizeWaveformUrl = parcel.readString(),
            largeSizeWaveformUrl = parcel.readString(),
            medSizeSpectralUrl = parcel.readString(),
            largeSizeSpectralUrl = parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(medSizeWaveformUrl)
        writeNullable(largeSizeWaveformUrl) { writeString(it) }
        writeNullable(medSizeSpectralUrl) { writeString(it) }
        writeNullable(largeSizeSpectralUrl) { writeString(it) }
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Image)
    }
}

data class Preview(

        @Json(name = "preview-lq-mp3")
        val lowQualityMp3Url: String,

        @Json(name = "preview-hq-mp3")
        val highQualityMp3Url: String,

        @Json(name = "preview-lq-ogg")
        val lowQualityOggUrl: String,

        @Json(name = "preview-hq-ogg")
        val highQualityOggUrl: String) : KParcelable {
    private constructor(parcel: Parcel) : this(
            lowQualityMp3Url = parcel.readString(),
            highQualityMp3Url = parcel.readString(),
            lowQualityOggUrl = parcel.readString(),
            highQualityOggUrl = parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(lowQualityMp3Url)
        writeString(highQualityMp3Url)
        writeString(lowQualityOggUrl)
        writeString(highQualityOggUrl)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Preview)
    }
}
