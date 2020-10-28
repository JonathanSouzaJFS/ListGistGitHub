package br.com.listgistgithub.model

import br.com.listgistgithub.data.model.Favorite
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class Gist(

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("html_url")
    var htmlUrl: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("comments")
    var comments: Int? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null,

    @SerializedName("owner")
    var owner: Owner? = null,

    @SerializedName("files")
    var files: @RawValue Map<String, Map<String, Any>>? = null,

    var isFavorite: Boolean = false
)

// favorite DTO
fun Gist.toFavorite() = Favorite(
    ownerId = id,
    type = (files!!.values.firstOrNull()?.getValue("type") ?: "Undefined") as String,
    ownerName = owner!!.login,
    ownerPhoto = owner!!.avatarUrl,
    ownerDescription = description
)