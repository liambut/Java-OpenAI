package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class GPTContext {
    JSONObject toSend = new JSONObject(), sys = new JSONObject(), filter = new JSONObject();
    JSONArray messages = new JSONArray();
    String instruction = "You are an assistant";

    public GPTContext(){

    }

    public String prepare(GPT input){
        this.sys.put("role", "system");
        this.sys.put("content", instruction);
        this.messages.put(this.sys);
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", input.prompt);

        this.messages.put(userMessage);
        this.toSend.put("messages", this.messages);
        this.toSend.put("model", input.model);
        return this.toSend.toString();
    }

    public void addResponse(GPT input){
        JSONObject messageContent = JSONParser.getMessage(input.result);
        JSONObject contentMessage = new JSONObject();
        contentMessage.put("role", "assistant");
        contentMessage.put("content", messageContent.getString("content"));
        this.messages.put(contentMessage);
    }

    public void clear(){
        this.messages.clear();
        this.messages.put(this.sys);
        this.toSend.clear();
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
