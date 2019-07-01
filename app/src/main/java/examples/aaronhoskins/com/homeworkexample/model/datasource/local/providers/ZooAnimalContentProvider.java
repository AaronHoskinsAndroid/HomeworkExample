package examples.aaronhoskins.com.homeworkexample.model.datasource.local.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseHelper;

import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.TABLE_NAME;

public class ZooAnimalContentProvider extends ContentProvider {
    ZooDatabaseHelper zooDatabaseHelper;

    @Override
    public boolean onCreate() {
        zooDatabaseHelper = new ZooDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query
            (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = zooDatabaseHelper.getWritableDatabase();
        Uri returnUri;
        long id = database.insert(TABLE_NAME, null, contentValues);
        if(id > 0) {
            returnUri = ZooAnimalsProviderContract.ZooAnimalsEntry.buildZooAnimalUri(id);
        } else {
            throw new UnsupportedOperationException("Row Not Inserted!!!!!");
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        //zooDatabaseHelper.deleteSpeciesFromDatabase(selectionArgs[0]);
        SQLiteDatabase database = zooDatabaseHelper.getWritableDatabase();
        final int itemsDelete = database.delete(TABLE_NAME, whereClause, whereArgs);
        database.close();
        return itemsDelete;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = zooDatabaseHelper.getWritableDatabase();
        final int itemsUpdated =
                database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        database.close();
        return itemsUpdated;
    }
}
