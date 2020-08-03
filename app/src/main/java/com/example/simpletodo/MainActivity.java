package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    EditText enteritem;
    RecyclerView revealItems;
    ItemsAdapter theitemsAdapter;

    List<String> theitems;

    public static final String THE_KEY_ITEM_TEXT = "item_text";
    public static final String THE_KEY_ITEM_POSITION = "item_position";
    public static final int THE_EDIT_TEXT_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ItemsAdapter.OnClickListener theonClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position"+position);
                String theitem = theitems.get(position);
                Intent intent;
                intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(THE_KEY_ITEM_TEXT, theitem);
                intent.putExtra(THE_KEY_ITEM_POSITION, position);
                startActivityForResult(intent, THE_EDIT_TEXT_CODE);
            }
        };
        ItemsAdapter.OnLongClickListener theonLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                theitems.remove(position);
                theitemsAdapter.notifyItemRemoved(position);
                Toast toast = Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT);
                toast.show();
                saveItems();
            }
        };
        loadItems();
        buttonAdd = findViewById(R.id.buttonAdd);
        enteritem = findViewById(R.id.enteritem);
        revealItems = findViewById(R.id.revealitems);
        theitemsAdapter = new ItemsAdapter(this.theitems, theonLongClickListener, theonClickListener);
        revealItems.setAdapter(theitemsAdapter);
        revealItems.setLayoutManager(new LinearLayoutManager(this));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theitems.add(enteritem.getText().toString());
                int theitemsize = theitems.size()-1;
                theitemsAdapter.notifyItemInserted(theitemsize);
                enteritem.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT);
                toast.show();
                saveItems();
            }
        });

    }

    private File getDataFile()
    {
        File thefile = new File(getFilesDir(), "data.txt");
        return thefile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!((requestCode == THE_EDIT_TEXT_CODE) && (resultCode == RESULT_OK))){
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
        else {
            int pos = data.getExtras().getInt(THE_KEY_ITEM_POSITION);
            String it_text = data.getStringExtra(THE_KEY_ITEM_TEXT);
            theitems.set(pos, it_text);
            theitemsAdapter.notifyItemChanged(pos);
            saveItems();
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Item updated successfully!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void loadItems(){
        try {
            File thefile = getDataFile();
            Charset thedefaultcharset = Charset.defaultCharset();
            theitems = new ArrayList<>(FileUtils.readLines(thefile, thedefaultcharset));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading item", e);
            theitems = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            File thefile = getDataFile();
            FileUtils.writeLines(thefile, theitems);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing item", e);
        }
    }
}
