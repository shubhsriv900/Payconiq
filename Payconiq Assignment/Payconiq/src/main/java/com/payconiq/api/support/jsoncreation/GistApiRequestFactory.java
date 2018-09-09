package com.payconiq.api.support.jsoncreation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.payconiq.api.literals.jsonkeys.JsonConstants;
import com.payconiq.api.support.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GistApiRequestFactory {

    public JsonNode prepareCreateGistRequestBody(String description, Map<String,String> fileContentMap, String scope){

        ObjectNode reqObj = JsonUtil.newObjectNode();

        reqObj.put(JsonConstants.DESCRIPTION_KEY, description);
        if (StringUtils.isNotEmpty(scope)) {
            if (scope.equalsIgnoreCase("public")) {
                reqObj.put(JsonConstants.PUBLIC_KEY, true);
            } else {
                reqObj.put(JsonConstants.PUBLIC_KEY, false);

            }
        }

        // Forming file and content
        // Input in format <file_name> , <jsonObject>
        Set<Map.Entry<String, String>> set = fileContentMap.entrySet();
        Iterator<Map.Entry<String, String>> i = set.iterator();
        // Adding the file json array to files json object
        ObjectNode fileContentJsonObject = reqObj.putObject(JsonConstants.FILES_KEY);
        while (i.hasNext()) {
            Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
            ObjectNode fileDescriptionJsonObject = fileContentJsonObject.putObject(me.getKey());
            fileDescriptionJsonObject.put(JsonConstants.CONTENT_KEY,me.getValue());
            //Creating following format: "file_name":{"content";"desc"},{"content";"desc"}
        }

        return reqObj;
    }

    // Overloading create JSON to use for Create Gist as well as Update Gist
    public JsonNode prepareCreateGistRequestBody(String description, Map<String,String> fileContentMap){
        return prepareCreateGistRequestBody(description, fileContentMap, null);
    }
}
