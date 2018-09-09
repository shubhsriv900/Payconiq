package com.payconiq.api.support.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parseJson(String jsonString) throws IOException {
        return objectMapper.readTree(jsonString);
    }

    public static ObjectNode newObjectNode(){
        return objectMapper.createObjectNode();
    }

    public static ArrayNode newArrayNode(){
        return objectMapper.createArrayNode();
    }
}
