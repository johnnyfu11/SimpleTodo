package com.example.simpletodo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editItem);
        buttonSave = findViewById(R.id.buttonSave);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit item");
        Intent intent = getIntent();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theintent = new Intent();
                Editable result = editItem.getText();
                theintent.putExtra(MainActivity.THE_KEY_ITEM_TEXT, result.toString());
                Bundle bundle = getIntent().getExtras();
                theintent.putExtra(MainActivity.THE_KEY_ITEM_POSITION, bundle.getInt(MainActivity.THE_KEY_ITEM_POSITION));
                setResult(RESULT_OK, theintent);
                finish();
            }
        });
        editItem.setText(intent.getStringExtra(MainActivity.THE_KEY_ITEM_TEXT));
    }
}
