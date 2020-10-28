package br.com.listgistgithub.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @ColumnInfo(name = "COLUMN_OWNERID") var ownerId: String = " ",
    @ColumnInfo(name = "COLUMN_OWNERNAME") var ownerName: String = " ",
    @ColumnInfo(name = "COLUMN_OWNERPHOTO") var ownerPhoto: String = " ",
    @ColumnInfo(name = "COLUMN_OWNERDESCRIPTION") var ownerDescription: String = " "
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}