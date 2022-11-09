package com.szforums.sessionValidity;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class sessionWriter {
    public sessionWriter(String filename,String Username) throws IOException {
        JSONObject loginObject = new JSONObject();
        loginObject.put("username",Username);

        Files.write(Paths.get(filename), loginObject.toJSONString().getBytes());
    }
}
