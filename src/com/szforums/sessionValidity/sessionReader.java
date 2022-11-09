package com.szforums.sessionValidity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class sessionReader {

    private String filename,username;

    public sessionReader(String filename) throws IOException, ParseException {
        this.filename = filename;
        JSONObject loginInfoObject = (JSONObject) loginJsonReader(filename);
        username = (String) loginInfoObject.get("username");
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Object loginJsonReader(String filename) throws IOException, ParseException {
        FileReader loginFileReader = new FileReader(filename);
        JSONParser loginJsonParser = new JSONParser();
        Object obj = loginJsonParser.parse(loginFileReader);

        loginFileReader.close();
        return obj;
    }
}
