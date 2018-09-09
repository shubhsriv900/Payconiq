package com.payconiq.api.apitests;

import com.payconiq.api.support.utils.AssertUtils;
import com.payconiq.api.support.utils.CommonUtils;
import com.payconiq.api.support.helpers.common.GistsOperations;
import com.payconiq.api.support.models.WebResponse;
import org.testng.annotations.Test;

public class CRUDTests extends BaseTest {

    String gistID = null;

    @Test(description = "To test the Gist Creation api")
    public void createGistTest() {

        // Initializing GistOperations
        logger.info("Create gist Operation Class");
        GistsOperations gistsOperations = new GistsOperations();

        //Create Gist
        WebResponse webResponse = gistsOperations.createGist(baseUrl, header, 3);

        // Check status code of creating gists
        logger.info("Check Gist is created successfully");
        AssertUtils.assert_equals(201, webResponse.getResponseCode());

        // Check response is not null by getting gistID from response body
        logger.info("Getting Gist ID from created Gist");
        gistID = gistsOperations.retrieveGistID(webResponse);
        AssertUtils.assert_true(gistID!=null);

        // Retrieve again using gistID, to check it exists
        WebResponse webResponse1 = gistsOperations.retrieveGist(baseUrl,gistID,header);
        logger.info("Check that newly created gist is retrieved");
        AssertUtils.assert_equals(200, webResponse1.getResponseCode());
    }

    @Test(description = "To Test Updatation of Gist already created",dependsOnMethods = "createGistTest")
    public void updateGistTest() {

        // Initializing GistOperations
        GistsOperations gistsOperations = new GistsOperations();

        // Update the Gist files with different content
        // Creating random number to be appended to gist content
        String rand = CommonUtils.randomWordGenerator(6);
        logger.info("Random word created " + rand);
        logger.info("Update the Gist created with the new random word in file descriptions " + rand);
        WebResponse webResponse = gistsOperations.updateGist(baseUrl, header, gistID, rand);

        //Check that update gist reponse is 200
        logger.info("Check that Update is done successfully");
        AssertUtils.assert_equals(200, webResponse.getResponseCode());

        // Retrieve again with gistID to check it exists
        WebResponse webResponse1 = gistsOperations.retrieveGist(baseUrl, gistID, header);
        logger.info("Check that update gist is retrieved");
        AssertUtils.assert_equals(200, webResponse1.getResponseCode());

        //Check the text which was updated is present in the file content
        logger.info("Check that newly added word exist in the file dedscription");
        AssertUtils.assert_contains(gistsOperations.getFileContent(webResponse1, "file1"), rand);
    }


    @Test(description = "To Test Deletion of Gist through API", dependsOnMethods = "updateGistTest")
    public void deleteGistTest() {

        GistsOperations gistsOperations = new GistsOperations();

        // Execute delete the Gist and check 204 is retrieved as response
        logger.info("Check that the created gist is deleted successfully");
        WebResponse webResponse = gistsOperations.deleteGist(baseUrl, header, gistID);
        AssertUtils.assert_equals(204,webResponse.getResponseCode());

        // Retrieve again to check it does not exist, where response should be 404
        logger.info("Check that the deleted gist is deleted already and we get not found code");
        WebResponse webResponse1 = gistsOperations.deleteGist(baseUrl, header, gistID);
        AssertUtils.assert_equals(404,webResponse1.getResponseCode());

    }


}
