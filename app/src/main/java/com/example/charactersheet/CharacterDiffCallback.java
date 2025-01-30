package com.example.charactersheet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import java.util.Objects;

public class CharacterDiffCallback extends DiffUtil.ItemCallback<Character> {
    @Override
    public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
        return Objects.equals(oldItem, newItem);
    }
}