package com.payconiq.api.apitests;

import com.fasterxml.jackson.databind.JsonNode;
import com.payconiq.api.literals.endpoints.EndPointRepo;
import com.payconiq.api.support.utils.AssertUtils;
import com.payconiq.api.support.utils.HttpMethodUtils;
import com.payconiq.api.support.helpers.common.GistsOperations;
import com.payconiq.api.support.models.WebResponse;
import com.payconiq.api.support.jsoncreation.GistApiRequestFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CornerTests extends BaseTest{

    @Test(description = "To Test truncated value becomes true only when file size is greater than 300")
    public void truncatedValueTest() {
        // Initializing GistOperations
        GistsOperations gistsOperations = new GistsOperations();

        //Create Gist with more than 300 files
        logger.info("Create a new gist with more than 300 file with pattern file<i>");
        WebResponse webResponse = gistsOperations.createGist(baseUrl, header, 301);

        // Check truncated value should be true as file number is more than 300
        String truncatedValue = gistsOperations.getJsonKeyValue(webResponse, "truncated");
        logger.info("Check that the created gist has tuncated value as true");
        AssertUtils.assert_equals(truncatedValue,"true");
    }


    @Test(description = "Test that deletion of Gist who has a different user is not allowed")
    public void deleteDifferentOwnerGistTest(){
        GistsOperations gistsOperations = new GistsOperations();
        String gistID = null;
        //Get Gist available public
        try {
            gistID = gistsOperations.getGistIDWithDifferentUser(baseUrl, header, user);
            if (gistID.isEmpty()){
                logger.error("There were no GistID with other users and hence failing");
                AssertUtils.assert_fail();
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }

        WebResponse webResponse = gistsOperations.deleteGist(baseUrl,header,gistID);

        logger.info("Check that the gist created by someone else cannot be deleted");
        AssertUtils.assert_equals(404,webResponse.getResponseCode());

    }


    @Test(description = "Check no response when wrong authorization is provided")
    public void wrongAuthorizationTest() {
        // Initializing GistOperations
        GistsOperations gistsOperations = new GistsOperations();

        //Create Gist with wrong authorization code token
        logger.info("Create an authorization code with dummy values");
        Map<String,String> headerNew = new HashMap<>();
        headerNew.put("Authorization", "token " + "abc");
        WebResponse webResponse = gistsOperations.createGist(baseUrl, headerNew, 3);

        // Check create gist is forbidden with wrong authorization code
        logger.info("Check that the gist is not created with wrong authorization");
        AssertUtils.assert_equals(401,webResponse.getResponseCode());
    }

    @Test(description = "Check create request with malformed json body in request")
    public void malformedJsonBodyTest() {
        // Initializing GistOperations
        GistsOperations gistsOperations = new GistsOperations();

        //Create Gist
        // Create JSON
        GistApiRequestFactory requestFactory = new GistApiRequestFactory();
        logger.info("Create JSON Payload with blank filename and description");
        Map<String,String> fileContentMap = new HashMap<>();
        // Put no file and content in the JSON
        fileContentMap.put("", "");

        JsonNode jsonObject = requestFactory.prepareCreateGistRequestBody("", fileContentMap, "public");

        // Create URL
        String urlCreateGist = baseUrl + EndPointRepo.CREATE_GIST;
        logger.info("Create URL created is " + urlCreateGist);
        // Make call
        WebResponse webResponse = HttpMethodUtils.post(urlCreateGist,header,jsonObject.toString());

        // Check status code of creating gists
        logger.info("Check that the created gist cannot be created");
        AssertUtils.assert_equals(422, webResponse.getResponseCode());
    }

    @Test(description = "Test that Gist of a different owner is allowed to be starred")
    public void starDifferentOwnerGistTest(){
        GistsOperations gistsOperations = new GistsOperations();
        String gistID = null;

        //Get Gist available public
        try {
            gistID = gistsOperations.getGistIDWithDifferentUser(baseUrl, header, user);
            if (gistID.isEmpty()){
                logger.error("There were no GistID with other users and hence failing");
                AssertUtils.assert_fail();
            }

        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }

        WebResponse webResponse = gistsOperations.starGist(baseUrl,header,gistID);
        logger.info("Check that Gist with different owner can be starred");
        AssertUtils.assert_equals(204,webResponse.getResponseCode());
    }
}
