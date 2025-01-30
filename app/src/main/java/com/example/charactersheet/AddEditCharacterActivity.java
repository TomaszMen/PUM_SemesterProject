package com.example.charactersheet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddEditCharacterActivity extends AppCompatActivity {

    private CharacterViewModel characterViewModel;
    private EditText nameEdit, descriptionEdit, levelEdit;
    private EditText strengthEdit, dexterityEdit, constitutionEdit, intelligenceEdit, wisdomEdit, charismaEdit;
    private Spinner characterClass, raceSpinner, armorSpinner;

    private int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_character);

        nameEdit = findViewById(R.id.character_name_edit);
        descriptionEdit = findViewById(R.id.character_description_edit);
        levelEdit = findViewById(R.id.character_level_edit);
        strengthEdit = findViewById(R.id.character_strength_edit);
        dexterityEdit = findViewById(R.id.character_dexterity_edit);
        constitutionEdit = findViewById(R.id.character_constitution_edit);
        intelligenceEdit = findViewById(R.id.character_intelligence_edit);
        wisdomEdit = findViewById(R.id.character_wisdom_edit);
        charismaEdit = findViewById(R.id.character_charisma_edit);
        characterClass = findViewById(R.id.character_class);
        raceSpinner = findViewById(R.id.race_spinner);
        armorSpinner = findViewById(R.id.armor_spinner);
        Button saveButton = findViewById(R.id.save_button);
        Button deleteButton = findViewById(R.id.delete_button);

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        setupSpinners();

        Intent intent = getIntent();
        if (intent.hasExtra("character_id")) {
            characterId = intent.getIntExtra("character_id", 0);
            if (characterId != 0) {
                loadCharacterData(characterId);
                deleteButton.setVisibility(View.VISIBLE);
            }
        }

        saveButton.setOnClickListener(v -> saveCharacter());
        deleteButton.setOnClickListener(v -> deleteCharacter());
    }

    private void deleteCharacter() {
        try {
            characterViewModel.getCharacterById(characterId).observe(this, character -> {
                if (character != null) {
                    new Thread(() -> {
                        characterViewModel.delete(character); // Pass the Character object
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Character deleted successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        });
                    }).start();
                } else {
                    Toast.makeText(this, "Character not found", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("DeleteError", "Error deleting character: " + e.getMessage());
            Toast.makeText(this, "Error deleting character", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this,
                R.array.character_classes, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterClass.setAdapter(classAdapter);

        ArrayAdapter<CharSequence> raceAdapter = ArrayAdapter.createFromResource(this,
                R.array.character_races, android.R.layout.simple_spinner_item);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(raceAdapter);

        ArrayAdapter<CharSequence> armorAdapter = ArrayAdapter.createFromResource(this,
                R.array.armor_types, android.R.layout.simple_spinner_item);
        armorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        armorSpinner.setAdapter(armorAdapter);
    }

    private void loadCharacterData(int characterId) {
        characterViewModel.getCharacterById(characterId).observe(this, character -> {
            if (character != null) {
                nameEdit.setText(character.name);
                descriptionEdit.setText(character.description);
                levelEdit.setText(String.valueOf(character.level));
                strengthEdit.setText(String.valueOf(character.strength));
                dexterityEdit.setText(String.valueOf(character.dexterity));
                constitutionEdit.setText(String.valueOf(character.constitution));
                intelligenceEdit.setText(String.valueOf(character.intelligence));
                wisdomEdit.setText(String.valueOf(character.wisdom));
                charismaEdit.setText(String.valueOf(character.charisma));

                characterClass.setSelection(getIndex(characterClass, character.characterClass));
                raceSpinner.setSelection(getIndex(raceSpinner, character.race));
                armorSpinner.setSelection(getIndex(armorSpinner, character.armorType));
            }
        });
    }

    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }

    private void saveCharacter() {
        String name = nameEdit.getText().toString().trim();
        String description = descriptionEdit.getText().toString().trim();
        String selectedCharacterClass = characterClass.getSelectedItem().toString();
        String race = raceSpinner.getSelectedItem().toString();
        String armorType = armorSpinner.getSelectedItem().toString();
        String lvl = levelEdit.getText().toString();
        String str = strengthEdit.getText().toString();
        String dex = dexterityEdit.getText().toString();
        String con = constitutionEdit.getText().toString();
        String inl = intelligenceEdit.getText().toString();
        String wis = wisdomEdit.getText().toString();
        String cha = charismaEdit.getText().toString();


        if (name.isEmpty() || lvl.isEmpty() || str.isEmpty() || dex.isEmpty() || con.isEmpty() || inl.isEmpty() || wis.isEmpty() || cha.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        int level = Integer.parseInt(lvl);
        int strength = Integer.parseInt(str);
        int dexterity = Integer.parseInt(dex);
        int constitution = Integer.parseInt(con);
        int intelligence = Integer.parseInt(inl);
        int wisdom = Integer.parseInt(wis);
        int charisma = Integer.parseInt(cha);

        if (level < 1 || level > 12){
            Toast.makeText(this, "Invalid data received: Level must be from 1 to 12", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( strength < 8 || strength > 20 || dexterity < 8 || dexterity > 20 || constitution < 8 || constitution > 20 || intelligence < 8 || intelligence > 20 || wisdom < 8 || wisdom > 20 || charisma < 8 || charisma > 20){
            Toast.makeText(this, "Invalid data received: Stats must be from 8 to 20", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new Thread(() -> {
                Character character = new Character();
                character.id = characterId;
                character.name = name;
                character.description = description;
                character.characterClass = selectedCharacterClass;
                character.race = race;
                character.armorType = armorType;
                character.level = level;
                character.strength = strength;
                character.dexterity = dexterity;
                character.constitution = constitution;
                character.intelligence = intelligence;
                character.wisdom = wisdom;
                character.charisma = charisma;

                character.updateArmorStats();
                character.updateMaxHp();
                if (characterId == 0) {
                    characterViewModel.insertCharacter(character);
                } else {
                    characterViewModel.updateCharacter(character);
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        } catch (Exception e) {
            Log.e("SaveError", "Error saving data: " + e.getMessage());
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}
