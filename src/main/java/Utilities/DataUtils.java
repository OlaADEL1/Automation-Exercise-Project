package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
    private final static String Test_Data_Path="src/test/resources/TestData/";
    //TODO: Reading data from Json file
    public static String getJsonData(String fileName, String field) // file+field
    {
        try {
            FileReader reader = new FileReader(Test_Data_Path+ fileName + ".json");
            JsonElement jsonElement= JsonParser.parseReader(reader);
            // Parser will divide the page and check if this file json or not
            // if there is a ";" this isn't json file
            return jsonElement.getAsJsonObject().get(field).getAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //TODO: Reading data from properties file
    public static String getPropertyValue(String fileName, String field) {
        try {
            Properties property =new Properties();
            property.load(new FileInputStream(Test_Data_Path+fileName+ ".properties"));
            return property.getProperty(field);
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        return "";
    }
}
