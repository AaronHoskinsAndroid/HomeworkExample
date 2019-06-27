package examples.aaronhoskins.com.homeworkexample.model.datasource.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import examples.aaronhoskins.com.homeworkexample.model.ZooAnimal.ZooAnimal;

import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_CATEGORY;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_DIET;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_IMAGE_URL;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_LIFE_EXP;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_SOUND;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.COLUMN_SPECIES;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.DATABASE_NAME;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.DATABASE_VERSION;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.DROP_QUERY;
import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.TABLE_NAME;

public class ZooDatabaseHelper extends SQLiteOpenHelper {
    public ZooDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ZooDatabaseContract.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL(DROP_QUERY);
            onCreate(sqLiteDatabase);
        }
    }

    public void insertZooAnimal(ZooAnimal animal) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SPECIES, animal.getmSpecies());
        contentValues.put(COLUMN_CATEGORY, animal.getmCategory());
        contentValues.put(COLUMN_DIET, animal.getmDiet());
        contentValues.put(COLUMN_IMAGE_URL, animal.getmImageUrl());
        contentValues.put(COLUMN_SOUND, animal.getmSound());
        contentValues.put(COLUMN_LIFE_EXP, animal.getmLiveExpediency());

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public ArrayList<ZooAnimal> getAllAnimals() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ZooAnimal> returnList = new ArrayList<>();

        Cursor cursor = database.rawQuery(ZooDatabaseContract.SELECT_ALL_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                String species = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIES));
                String diet = cursor.getString(cursor.getColumnIndex(COLUMN_DIET));
                String sound = cursor.getString(cursor.getColumnIndex(COLUMN_SOUND));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));
                String lifeExp = cursor.getString(cursor.getColumnIndex(COLUMN_LIFE_EXP));
                returnList.add(new ZooAnimal(species, category, lifeExp, diet, imageUrl, sound));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returnList;
    }

    public ArrayList<ZooAnimal> getAnimalsByCategory(String requestCategory) {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ZooAnimal> returnList = new ArrayList<>();

        Cursor cursor = database.rawQuery(ZooDatabaseContract.getByCatagoryQuery(requestCategory), null);
        if (cursor.moveToFirst()) {
            do {
                String species = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIES));
                String diet = cursor.getString(cursor.getColumnIndex(COLUMN_DIET));
                String sound = cursor.getString(cursor.getColumnIndex(COLUMN_SOUND));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL));
                String lifeExp = cursor.getString(cursor.getColumnIndex(COLUMN_LIFE_EXP));
                returnList.add(new ZooAnimal(species, category, lifeExp, diet, imageUrl, sound));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returnList;
    }

    public ZooAnimal getIndividualZooAnimal(String species) {
        SQLiteDatabase database = this.getReadableDatabase();
        ZooAnimal animal = new ZooAnimal();

        Cursor cursor = database.rawQuery(ZooDatabaseContract.getBySpeciesQuery(species), null);
        if (cursor.moveToFirst()) {

            animal.setmSpecies(cursor.getString(cursor.getColumnIndex(COLUMN_SPECIES)));
            animal.setmDiet(cursor.getString(cursor.getColumnIndex(COLUMN_DIET)));
            animal.setmSound(cursor.getString(cursor.getColumnIndex(COLUMN_SOUND)));
            animal.setmCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
            animal.setmImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));
            animal.setmLiveExpediency(cursor.getString(cursor.getColumnIndex(COLUMN_LIFE_EXP)));
        }
        cursor.close();
        database.close();
        return animal;
    }
}
