package com.example.charactersheet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final CharacterAdapter adapter = new CharacterAdapter(character -> {
            Intent intent = new Intent(MainActivity.this, AddEditCharacterActivity.class);
            intent.putExtra("character_id", character.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CharacterViewModel viewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
        viewModel.getAllCharacters().observe(this, adapter::submitList);

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCharacterActivity.class);
            startActivity(intent);
        });
    }
}