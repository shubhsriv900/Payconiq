package com.payconiq.api.support.helpers.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.payconiq.api.literals.endpoints.EndPointRepo;
import com.payconiq.api.support.utils.CommonUtils;
import com.payconiq.api.support.utils.HttpMethodUtils;
import com.payconiq.api.support.utils.JsonUtil;
import com.payconiq.api.support.models.WebResponse;
import com.payconiq.api.support.jsoncreation.GistApiRequestFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GistsOperations {

    private GistApiRequestFactory gistReqFactory = new GistApiRequestFactory();

    public WebResponse createGist(String baseUrl, Map<String, String> header, int fileCount){
        // Create JSON
        Map<String,String> fileContentMap = new HashMap<>();
        for (int i=0; i<fileCount; i++) {
            fileContentMap.put("file" + i, "description " + CommonUtils.randomWordGenerator(4));
        }

        JsonNode json = gistReqFactory.prepareCreateGistRequestBody("Test Descrption", fileContentMap, "public");

        // Create URL
        String urlCreateGist = baseUrl + EndPointRepo.CREATE_GIST;
        // Make call
        WebResponse webResponse = HttpMethodUtils.post(urlCreateGist,header, json.toString());

        return webResponse;
    }

    public WebResponse retrieveGist(String baseUrl, String gistID, Map<String,String> header) {
        String urlRetrieveGist = baseUrl + EndPointRepo.GET_GIST_WITH_ID + "/" + gistID;
        WebResponse webResponse = HttpMethodUtils.get(urlRetrieveGist, header);
        return webResponse;
    }

    public WebResponse updateGist(String baseUrl, Map<String, String> header, String gistID, String rand){
        Map<String,String> fileContentMap = new HashMap<>();

        fileContentMap.put("file1", "description "+ rand);
        fileContentMap.put("file2", "description "+ rand);
        fileContentMap.put("file3", "description "+ rand);

        JsonNode jsonObject = gistReqFactory.prepareCreateGistRequestBody("Test Descrption",fileContentMap);

        // Execute Update the Gist
        String urlUpdateGist = baseUrl + EndPointRepo.UPDATE_GIST + "/" + gistID;
        WebResponse webResponse = HttpMethodUtils.patch(urlUpdateGist,header,jsonObject.toString());
        return webResponse;
    }

    public WebResponse deleteGist(String baseUrl, Map<String, String> header, String gistID){
        String urlDeleteGist = baseUrl + EndPointRepo.DELETE_GIST + "/" + gistID;
        WebResponse webResponse = HttpMethodUtils.delete(urlDeleteGist,header);

        return webResponse;
    }

    public String retrieveGistID(WebResponse webResponse){
        String gistID = null;
        try {
            gistID = JsonUtil.parseJson(webResponse.getCurrentBody()).get("id").toString().replace("\"","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gistID;
    }


    public String getFileContent(WebResponse webResponse, String fileName){
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonUtil.parseJson(webResponse.getCurrentBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContent = jsonNode.get("files").get(fileName).get("content").toString();
        return fileContent;
    }

    public String getJsonKeyValue(WebResponse webResponse, String key){
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonUtil.parseJson(webResponse.getCurrentBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String keyValue = jsonNode.get(key).toString();
        return keyValue;
    }

    public String getGistIDWithDifferentUser(String baseUrl, Map<String,String> header, String user) throws IOException {
        String urlRetrieveGist = baseUrl + EndPointRepo.LIST_GIST_PUBLIC;
        WebResponse webResponse = HttpMethodUtils.get(urlRetrieveGist, header);

        //Sometimes we are getting gists of only the user hence we need to check agian and again
        JsonNode node = JsonUtil.parseJson(webResponse.getCurrentBody());

//        Iterator<JsonNode> it = node.iterator();
//        while (it.hasNext()){
        //Iterate on the Json Array and get the Gist ID
        for(int i=0; i<node.size();i++){
            JsonNode item = node.get(i);
            JsonNode owner = item.findPath("owner");
            String login = owner.findPath("login").asText();
            if (!login.equalsIgnoreCase(user)){
                return item.findPath("id").asText();
            }
        }

        return null;
    }


    public WebResponse starGist(String baseUrl,  Map<String,String> header, String gistID){
        String urlStarGist = baseUrl + EndPointRepo.STAR_GIST.replace("gistID", gistID);
        WebResponse webResponse = HttpMethodUtils.put(urlStarGist,header);

        return webResponse;
    }
}
