package examples.aaronhoskins.com.homeworkexample.model.datasource.local.filestorage;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InternalFileStorage {
    private String fileName;

    public InternalFileStorage(String fileName) {
        this.fileName = fileName;
    }

    public void writeToFile(Context context, String string) throws Exception {
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(string);
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public String readFromFile(Context context) throws Exception {
        String returnString = "";
        FileInputStream fileInputStream = context.openFileInput(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        char[] inputBuffer= new char[100];
        int charRead;
        while ((charRead=inputStreamReader.read(inputBuffer))>0) {
            // char to string conversion
            String readstring=String.copyValueOf(inputBuffer,0,charRead);
            returnString = String.format("%s%s", returnString, readstring);
            //returnString = returnString + readstring;
            //returnString +=readstring;
        }
        return returnString;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
