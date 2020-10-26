package br.com.listgistgithub.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.listgistgithub.model.Favorite

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(fv: Favorite)

    @Delete
    fun delete(fv: Favorite)

    @Update
    fun update(fv: Favorite)

    @Query("SELECT * from favorite")
    fun getAll(): LiveData<Favorite>

    @Query("SELECT * FROM Favorite WHERE COLUMN_OWNERID = :ownerIdU")
    fun getFavoriteById(ownerIdU: String): Favorite
}