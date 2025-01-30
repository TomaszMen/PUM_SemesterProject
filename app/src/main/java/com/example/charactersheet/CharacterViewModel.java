package com.example.charactersheet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharacterViewModel extends AndroidViewModel {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final CharacterDao characterDao;
    private final LiveData<List<Character>> allCharacters;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        CharacterDatabase database = CharacterDatabase.getDatabase(application);
        characterDao = database.characterDao();
        allCharacters = characterDao.getAllCharacters();
    }

    public LiveData<List<Character>> getAllCharacters() {
        return allCharacters;
    }

    public LiveData<Character> getCharacterById(int id) {
        return characterDao.getCharacterById(id);
    }

    public void insertCharacter(Character character) {
        executorService.execute(() -> characterDao.insert(character));
    }

    public void updateCharacter(Character character) {
        executorService.execute(() -> characterDao.update(character));
    }

    public void delete(Character character) {
        executorService.execute(() -> characterDao.delete(character));
    }
}
