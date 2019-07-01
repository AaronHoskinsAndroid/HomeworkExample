package examples.aaronhoskins.com.homeworkexample.model.datasource.local.providers;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ZooAnimalsProviderContract implements BaseColumns {
    public static final String CONTENT_AUTHORITY =
            "examples.aaronhoskins.com.homeworkexample.model.datasource.local.providers";
    public static final Uri CONTENT_URI =
            Uri.parse(String.format("content://%s", CONTENT_AUTHORITY));
    public static final String PATH_ZOOANIMAL = "zoo_animals";

    public static class ZooAnimalsEntry implements BaseColumns {
        public static final Uri ZOO_ANIMAL_CONTENT_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_ZOOANIMAL).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir" + ZOO_ANIMAL_CONTENT_URI + "/" + PATH_ZOOANIMAL;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + ZOO_ANIMAL_CONTENT_URI + "/" + PATH_ZOOANIMAL;
        public static final Uri buildZooAnimalUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
