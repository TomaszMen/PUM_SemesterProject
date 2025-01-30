package com.example.charactersheet;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterAdapter extends ListAdapter<Character, CharacterAdapter.CharacterViewHolder> {
    private final OnItemClickListener listener;

    public CharacterAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Character> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = getItem(position);
        holder.bind(character);
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final TextView characterName;
        private final TextView characterClass;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.character_name);
            characterClass = itemView.findViewById(R.id.character_class);
            itemView.setOnClickListener(v -> {
                if (listener != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(getBindingAdapterPosition()));
                }
            });
        }

        public void bind(Character character) {
            if (character != null) {
                characterName.setText(character.getName() != null ? character.getName() : "N/A");
                characterClass.setText(character.getCharacterClass() != null ? character.getCharacterClass() : "N/A");
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Character character);
    }
}
