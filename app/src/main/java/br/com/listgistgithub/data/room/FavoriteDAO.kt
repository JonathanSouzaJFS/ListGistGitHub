package br.com.listgistgithub.data.room

import androidx.room.*
import br.com.listgistgithub.model.Favorite

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(fv: Favorite)

    @Delete
    fun delete(fv: Favorite)

    @Query("DELETE FROM Favorite WHERE COLUMN_OWNERID = :ownerIdU")
    fun deleteFavoriteById(ownerIdU: String)

    @Update
    fun update(fv: Favorite)

    @Query("SELECT * from favorite")
    fun getAll(): MutableList<Favorite>

    @Query("SELECT * FROM Favorite WHERE COLUMN_OWNERID = :ownerIdU")
    fun getFavoriteById(ownerIdU: String): Favorite
}