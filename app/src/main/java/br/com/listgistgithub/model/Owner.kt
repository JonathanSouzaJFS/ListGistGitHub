package br.com.listgistgithub.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Owner(

    @SerializedName("login")
    @Expose
    var login: String? = null,

    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,

    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null

)