package br.com.listgistgithub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.listgistgithub.model.Gist
import br.com.listgistgithub.model.Owner

@Entity(tableName = "favorite")
data class Favorite(
    @ColumnInfo(name = "COLUMN_OWNERID") var ownerId: String? = null,
    @ColumnInfo(name = "COLUMN_OWNERNAME") var ownerName: String? = null,
    @ColumnInfo(name = "COLUMN_OWNERPHOTO") var ownerPhoto: String? = null,
    @ColumnInfo(name = "COLUMN_OWNERDESCRIPTION") var ownerDescription: String? = null,
    @ColumnInfo(name = "COLUMN_OWNERTYPE") var type: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}

fun Favorite.toGist() =
    Gist(
        id = ownerId,
        owner = Owner(
            avatarUrl = ownerPhoto,
            login = ownerName
        ),
        files = mapOf("files" to mapOf("type" to type!!)),
        description = ownerDescription,
        isFavorite = true
    )
