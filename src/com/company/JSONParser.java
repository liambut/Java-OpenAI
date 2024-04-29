package com.company;
import org.json.JSONObject;
import java.util.ArrayList;

public class JSONParser {
    public static String getString(JSONObject input){
            return input.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }
    public static JSONObject getMessage(JSONObject input){
        return input.getJSONArray("choices").getJSONObject(0).getJSONObject("message");
    }

    public static double[] getEmbeddings(JSONObject input) {
        String temp =  input.getJSONArray("data").getJSONObject(0).getJSONArray("embedding").toString();;
        String[] arr = temp.substring(1, temp.length()-1).split(",");
        ArrayList<Double> doubles = new ArrayList<>(0);
        for(int i = 0;i<arr.length;i++){
            doubles.add(Double.parseDouble(arr[i]));
        }
        double[] ret = new double[doubles.size()];
        for(int i=0;i<doubles.size();i++){
            ret[i] = doubles.get(i);
        }
        return ret;
    }

    public static Boolean getFlag(JSONObject input){
        return !input.getJSONArray("results").getJSONObject(0).getBoolean("flagged");
    }
}
