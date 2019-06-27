package examples.aaronhoskins.com.homeworkexample.model.datasource.local.database;

import java.util.Locale;

public class ZooDatabaseContract {
    public static final String DATABASE_NAME = "zoo_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "animal_table";
    public static final String COLUMN_SPECIES = "species";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SOUND = "sound";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_LIFE_EXP = "life_exp";
    public static final String COLUMN_DIET = "diet";
    public static final String DROP_QUERY = String.format("DROP TABLE %s", TABLE_NAME);
    public static final String SELECT_ALL_QUERY = String.format("SELECT * FROM %s", TABLE_NAME);

    public static String getCreateQuery() {
        return String.format(
                Locale.US,
                "CREATE TABLE %s( %s TEXT PRIMARY_KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, COLUMN_SPECIES, COLUMN_CATEGORY, COLUMN_SOUND, COLUMN_IMAGE_URL, COLUMN_LIFE_EXP, COLUMN_DIET);
    }

    public static String getByCatagoryQuery(String category) {
        return String.format("%s WHERE %s = \"%s\"",
                SELECT_ALL_QUERY, COLUMN_CATEGORY, category);
    }

    public static String getBySpeciesQuery(String species) {
        return String.format("%s WHERE %s = \"%s\"",
                SELECT_ALL_QUERY, COLUMN_SPECIES, species);
    }



}
