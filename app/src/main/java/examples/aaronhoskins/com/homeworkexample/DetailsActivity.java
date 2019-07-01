package examples.aaronhoskins.com.homeworkexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

import examples.aaronhoskins.com.homeworkexample.model.ZooAnimal.ZooAnimal;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.filestorage.InternalFileStorage;

public class DetailsActivity extends AppCompatActivity {
    TextView textView;
    InternalFileStorage internalFileStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        textView = findViewById(R.id.tvName);

        if(intent != null) {
            ZooAnimal zooAnimal = intent.getParcelableExtra("animal");
            if(zooAnimal != null) {
                textView.setText(zooAnimal.getmSpecies());
                saveAnimalToInternalFileStorage(zooAnimal);
            }
        }
    }

    private void saveAnimalToInternalFileStorage(ZooAnimal passedAnimal){
        try{
           internalFileStorage = new InternalFileStorage("fav_animals.txt");
           final String stringToSaveToFile
                   = String.format(Locale.US,
                   "{\"animal\": \"%s\", \"category\" : \"%s\"}",
                   passedAnimal.getmSpecies(), passedAnimal.getmCategory());
           internalFileStorage.writeToFile(this, stringToSaveToFile);
           internalFileStorage = null;
        } catch(Exception e) {
            Log.e("TAG", "saveAnimalToInternalFileStorage: ", e);
        }
    }
}
