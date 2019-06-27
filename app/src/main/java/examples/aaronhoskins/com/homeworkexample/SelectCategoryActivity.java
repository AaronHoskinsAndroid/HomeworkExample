package examples.aaronhoskins.com.homeworkexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.MockZooDatabaseHelper;

public class SelectCategoryActivity extends AppCompatActivity {
    ListView lstCategoryList;
    MockZooDatabaseHelper mockZooDatabaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category_activty);
        sharedPreferences = getSharedPreferences("shared_pref", MODE_PRIVATE);
        mockZooDatabaseHelper = new MockZooDatabaseHelper();

        lstCategoryList = findViewById(R.id.lstCategory);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mockZooDatabaseHelper.getAllCategories());
        lstCategoryList.setAdapter(arrayAdapter);
        lstCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), SelectAnimalActivity.class);
                setCategoryInSharedPref(mockZooDatabaseHelper.getAllCategories().get(i));
                startActivity(intent);
            }
        });
    }

    private void setCategoryInSharedPref(String category) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("category", category);
        editor.apply();  //Thread Safe, recommended
        //editor.commit();
        editor.clear();
    }
}
