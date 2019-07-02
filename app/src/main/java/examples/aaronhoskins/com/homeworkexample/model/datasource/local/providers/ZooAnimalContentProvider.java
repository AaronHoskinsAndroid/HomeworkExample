package examples.aaronhoskins.com.homeworkexample.model.datasource.local.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract;
import examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseHelper;

import static examples.aaronhoskins.com.homeworkexample.model.datasource.local.database.ZooDatabaseContract.TABLE_NAME;

public class ZooAnimalContentProvider extends ContentProvider {
    public static final int ZOO_ANIMAL = 100;
    public static final int ZOO_ANIMAL_ITEM = 101;
    ZooDatabaseHelper zooDatabaseHelper;
    UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        zooDatabaseHelper = new ZooDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query
            (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = zooDatabaseHelper.getWritableDatabase();
        Cursor retCursor = null;
        switch(uriMatcher.match(uri)) {
            case ZOO_ANIMAL:
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ZOO_ANIMAL_ITEM:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        TABLE_NAME,
                        projection,
                        ZooDatabaseContract.COLUMN_SPECIES + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){
            case ZOO_ANIMAL:
                return ZooAnimalsProviderContract.ZooAnimalsEntry.CONTENT_TYPE;
            case ZOO_ANIMAL_ITEM:
                return ZooAnimalsProviderContract.ZooAnimalsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
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

    public static UriMatcher buildUriMatcher(){
        String content = ZooAnimalsProviderContract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, ZooAnimalsProviderContract.PATH_ZOO_ANIMAL, ZOO_ANIMAL);
        matcher.addURI(content, ZooAnimalsProviderContract.PATH_ZOO_ANIMAL + "/#", ZOO_ANIMAL_ITEM);
        return matcher;
    }
}
