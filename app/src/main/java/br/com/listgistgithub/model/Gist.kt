package br.com.listgistgithub.model

import br.com.listgistgithub.data.model.Favorite
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

data class Gist(

    @SerializedName("id")
    var id: String = " ",

    @SerializedName("html_url")
    var htmlUrl: String =   " ",

    @SerializedName("description")
    var description: String =  " ",

    @SerializedName("comments")
    var comments: Int = 0,

    @SerializedName("created_at")
    var createdAt: String = " ",

    @SerializedName("updated_at")
    var updatedAt: String = " ",

    @SerializedName("owner")
    var owner: Owner,

    @SerializedName("files")
    var files: @RawValue Map<String, Map<String, Any>>,

    var isFavorite: Boolean = false
)

// favorite DTO
fun Gist.toFavorite() = Favorite(
    ownerId = id,
    type = (files.values.firstOrNull()?.getValue("type") ?: "Undefined") as String,
    ownerName = owner.login,
    ownerPhoto = owner.avatarUrl,
    ownerDescription = description,
    comments = comments
)