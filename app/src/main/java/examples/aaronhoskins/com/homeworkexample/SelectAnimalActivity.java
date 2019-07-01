package examples.aaronhoskins.com.homeworkexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


import examples.aaronhoskins.com.homeworkexample.model.ZooAnimal.ZooAnimal;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.MockZooDatabaseHelper;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseHelper;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.filestorage.InternalFileStorage;

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

        AnimalListAdapter animalListAdapter = new AnimalListAdapter(listOfAnimalsInCategory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvZooAnimalsList = findViewById(R.id.recyclerView);
        rvZooAnimalsList.setLayoutManager(layoutManager);
        rvZooAnimalsList.setAdapter(animalListAdapter);
    }

    @Override
    protected void onResume() {
        printFileToLogcat();
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
}
