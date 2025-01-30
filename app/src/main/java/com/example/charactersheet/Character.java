package com.example.charactersheet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "characters")
public class Character {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;
    public String characterClass;  // Barbarian, Bard, etc.
    public String race;            // Human, Elf, etc.
    public String armorType;       // No armor, Light armor, Heavy armor
    public int level;
    public int maxHp;
    public int armorClass;
    public int walkingSpeed;

    public int strength;
    public int dexterity;
    public int constitution;
    public int intelligence;
    public int wisdom;
    public int charisma;

    public void updateArmorStats() {
        switch (armorType) {
            case "No armor":
                this.armorClass = 13;
                this.walkingSpeed = 35;
                break;
            case "Light armor":
                this.armorClass = 15;
                this.walkingSpeed = 30;
                break;
            case "Heavy armor":
                this.armorClass = 18;
                this.walkingSpeed = 20;
                break;
            default:
                this.armorClass = 10;
                this.walkingSpeed = 30;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void updateMaxHp() {
        int hpPerLevel;
        switch (characterClass) {
            case "Barbarian": hpPerLevel = 16; break;
            case "Bard":
            case "Cleric":
            case "Ranger":
            case "Rogue": hpPerLevel = 8; break;
            case "Druid":
            case "Monk":
            case "Paladin": hpPerLevel = 10; break;
            case "Fighter": hpPerLevel = 12; break;
            case "Sorcerer":
            case "Warlock":
            case "Wizard": hpPerLevel = 6; break;
            default: hpPerLevel = 8; break;
        }
        this.maxHp = hpPerLevel * this.level;
    }
}
