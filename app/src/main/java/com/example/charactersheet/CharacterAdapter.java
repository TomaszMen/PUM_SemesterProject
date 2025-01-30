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
        private final TextView healthPoints;
        private final TextView armorClass;
        private final TextView walkingSpeed;

        private final TextView str;
        private final TextView dex;
        private final TextView con;
        private final TextView inl;
        private final TextView wis;
        private final TextView cha;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.character_name);
            characterClass = itemView.findViewById(R.id.character_class);
            healthPoints = itemView.findViewById(R.id.health_points);
            armorClass = itemView.findViewById(R.id.a_c);
            walkingSpeed = itemView.findViewById(R.id.walking_speed);

            str = itemView.findViewById(R.id.character_strength);
            dex = itemView.findViewById(R.id.character_dexterity);
            con = itemView.findViewById(R.id.character_constitution);
            inl = itemView.findViewById(R.id.character_intelligence);
            wis = itemView.findViewById(R.id.character_wisdom);
            cha = itemView.findViewById(R.id.character_charisma);

            itemView.setOnClickListener(v -> {
                if (listener != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(getBindingAdapterPosition()));
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Character character) {
            if (character != null) {
                characterName.setText(character.getName() != null ? character.getName() : "N/A");
                characterClass.setText(character.getCharacterClass() != null ? character.getCharacterClass() : "N/A");
                healthPoints.setText("Maximum Health Points: " + character.getHP());
                armorClass.setText("Armor Class: " + character.getAC());
                walkingSpeed.setText("Walking Speed: " + character.getWS() + "ft");
                str.setText(String.valueOf(character.getStr()));
                dex.setText(String.valueOf(character.getDex()));
                con.setText(String.valueOf(character.getCon()));
                inl.setText(String.valueOf(character.getInt()));
                wis.setText(String.valueOf(character.getWis()));
                cha.setText(String.valueOf(character.getCha()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Character character);
    }
}
