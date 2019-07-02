package examples.aaronhoskins.com.homeworkexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


import examples.aaronhoskins.com.homeworkexample.model.ZooAnimal.ZooAnimal;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.MockZooDatabaseHelper;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseHelper;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.filestorage.InternalFileStorage;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.providers.ZooAnimalsProviderContract;

import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_CATEGORY;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_DIET;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_IMAGE_URL;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_LIFE_EXP;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_SOUND;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_SPECIES;

public class SelectAnimalActivity extends AppCompatActivity {
    RecyclerView rvZooAnimalsList;
    ZooDatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<ZooAnimal> listOfAnimalsInCategory;
        sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        databaseHelper = new ZooDatabaseHelper(this);
        checkIfFirstRun();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_animal);
        String category = sharedPreferences.getString("category", "not found");

        if(!category.equals("not found")) {
            listOfAnimalsInCategory = databaseHelper.getAnimalsByCategory(category);
        } else {
            listOfAnimalsInCategory = databaseHelper.getAllAnimals();
        }

        AnimalListAdapter animalListAdapter = new AnimalListAdapter(getZooAnimalsFromContentProvider());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvZooAnimalsList = findViewById(R.id.recyclerView);
        rvZooAnimalsList.setLayoutManager(layoutManager);
        rvZooAnimalsList.setAdapter(animalListAdapter);

        getZooAnimalsFromContentProvider();
    }

    @Override
    protected void onResume() {
        //printFileToLogcat();
        super.onResume();
    }

    private void printFileToLogcat() {
        try {
            InternalFileStorage internalFileStorage
                    = new InternalFileStorage("fav_animals.txt");
            Log.d("TAG", "printFileToLogcat: "
                    + internalFileStorage.readFromFile(this));
            internalFileStorage = null;
        } catch(Exception e) {
            Log.e("TAG", "printFileToLogcat: ", e);
        }
    }

    public void checkIfFirstRun() {
        //if(sharedPreferences.getBoolean("first_load", false)){
            ArrayList<ZooAnimal> initList= new MockZooDatabaseHelper().queryForAllAnimals();
            for(ZooAnimal animal : initList) {
                databaseHelper.insertZooAnimal(animal);
            }
            sharedPreferences.edit().putBoolean("first_load", true).apply();
        //}
    }

    public ArrayList<ZooAnimal> getZooAnimalsFromContentProvider() {
        ArrayList<ZooAnimal> returnZooAnimalList = new ArrayList<>();
        Uri uri = ZooAnimalsProviderContract.ZooAnimalsEntry.ZOO_ANIMAL_CONTENT_URI;

        Cursor returnCursorFromProvider = getContentResolver().query(
                uri,
                null, null, null,null);
       // Log.d("TAG", "getZooAnimalsFromContentProvider: " + returnCursorFromProvider.toString());
        if (returnCursorFromProvider.moveToFirst()) {
            do {
                String species = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_SPECIES));
                String diet = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_DIET));
                String sound = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_SOUND));
                String category = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_CATEGORY));
                String imageUrl = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_IMAGE_URL));
                String lifeExp = returnCursorFromProvider.getString(
                        returnCursorFromProvider.getColumnIndex(COLUMN_LIFE_EXP));
                returnZooAnimalList.add(new ZooAnimal(species, category, lifeExp, diet, imageUrl, sound));
            } while (returnCursorFromProvider.moveToNext());
        }
        returnCursorFromProvider.close();
        Log.d("TAG", "getZooAnimalsFromContentProvider: " + returnZooAnimalList.get(0).getmSpecies());
        return returnZooAnimalList;
    }
}
