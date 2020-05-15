package com.example.android.mypets.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypets.R;
import com.example.android.mypets.activities.adapter.PetCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.android.mypets.activities.data.PetContract;
import com.example.android.mypets.activities.data.PetDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
private static final int PET_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                startActivity(intent);
            }
        });

    }
    // we add this method to refresh the list with new pet in the database
    @Override
    protected void onStart() {
        super.onStart();
      displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
               PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT };
        // Perform a query on the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to access the pet data.
        Cursor cursor = getContentResolver().query(
                PetContract.PetEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);                  // The sort order for the returned rows

                  // Find the ListView which will be populated with the pet data
                  ListView petListView = (ListView) findViewById(R.id.list);
                  // Setup an Adapter to create a list item for each row of pet data in the Cursor.
                 // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
                 View emptyView = findViewById(R.id.empty_view);
                petListView.setEmptyView(emptyView);
                      PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);
                  // Attach the adapter to the ListView.
                  petListView.setAdapter(adapter);
    }
    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet() {
        // TODO: Insert a single pet into the database
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Garfield");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Tabby");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);
        Uri newUri = getContentResolver().insert(PetContract.PetEntry.CONTENT_URI, values);
        Log.v("catalg activity", "new row id "+newUri);
        Toast.makeText(this, "hiiiiiiii" + newUri, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertPet();
               displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader <Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader <Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader <Cursor> loader) {

    }
}
