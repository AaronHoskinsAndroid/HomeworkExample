package examples.aaronhoskins.com.homeworkexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import examples.aaronhoskins.com.homeworkexample.model.ZooAnimal.ZooAnimal;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.MockZooDatabaseHelper;

public class SelectAnimalActivity extends AppCompatActivity {
    RecyclerView rvZooAnimalsList;
    MockZooDatabaseHelper mockZooDatabaseHelper;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<ZooAnimal> listOfAnimalsInCategory;
        sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_animal);
        mockZooDatabaseHelper = new MockZooDatabaseHelper();
        String category = sharedPreferences.getString("category", "not found");

        if(!category.equals("not found")) {
            listOfAnimalsInCategory = mockZooDatabaseHelper.queryForCategoryOfAnimals(category);
        } else {
            listOfAnimalsInCategory = mockZooDatabaseHelper.queryForAllAnimals();
        }

        AnimalListAdapter animalListAdapter = new AnimalListAdapter(listOfAnimalsInCategory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvZooAnimalsList = findViewById(R.id.recyclerView);
        rvZooAnimalsList.setLayoutManager(layoutManager);
        rvZooAnimalsList.setAdapter(animalListAdapter);
    }
}
