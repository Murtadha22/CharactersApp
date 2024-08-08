package com.example.retrofittest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
@Entity
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addCharacter(character: Character)

    @Query("SELECT * FROM  character_table ORDER BY id ASC")
     fun getAllCharacters(): LiveData<List<Character>>

}