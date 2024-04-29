package com.company;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GPT {

    configGetter configurationClass = new configGetter("config.properties");
    private String apiKey = configurationClass.getConfigProperty("API_KEY");
    String url = configurationClass.getConfigProperty("COMPLETIONS_URL");
    URL link;
    String prompt, model = configurationClass.getConfigProperty("MODEL");
    HttpURLConnection con;
    OutputStreamWriter writer;
    BufferedReader br;
    StringBuilder response;
    JSONObject result, filterObject;
    GPTContext messages = new GPTContext();
    public void setPrompt(String Prompt){
        this.prompt = Prompt;
    }

    public void setModel(String Model){
        this.model = Model;
    }
    public void setKey(String key){
        this.apiKey = key;
    }
    public void printResponse(){System.out.println(JSONParser.getString(this.result));}
    public String getResponse(){
        return JSONParser.getString(this.result);
    }
    public String getApiKey() {return apiKey;}
    public String getPrompt() {return prompt;}
    public void connect(Boolean isFilter){
        try {
            if(isFilter){
                this.link = new URL(configurationClass.getConfigProperty("MODERATION"));
            }else{
                this.link = new URL(url);
            }
            this.con = (HttpURLConnection) this.link.openConnection();
            this.con.setRequestMethod("POST");
            this.con.setRequestProperty("Authorization", "Bearer " + this.apiKey);
            this.con.setRequestProperty("Content-Type", "application/json");
            this.con.setDoOutput(true);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void sendPost(){
        try {
            this.connect(false);
            this.writer = new OutputStreamWriter(this.con.getOutputStream());
            this.writer.write(this.messages.prepare(this));
            this.GET();
            this.result = new JSONObject(this.response.toString());
            //System.out.println(this.response.toString());
        }catch(Exception e){
            System.out.println(e);
        }
        //System.out.println(this.response.toString());
    }

    public void GET() throws IOException {
        this.writer.flush();
        this.writer.close();
        this.response = new StringBuilder();
        String responseLine;
        this.br = new BufferedReader(new InputStreamReader(this.con.getInputStream()));
        while ((responseLine = this.br.readLine()) != null) {
            this.response.append(responseLine);
        }
        this.br.close();
    }
}