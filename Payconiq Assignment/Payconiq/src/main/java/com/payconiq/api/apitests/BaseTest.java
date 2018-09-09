package com.payconiq.api.apitests;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    String oAuth;
    String baseUrl;
    Map<String,String> header;
    String user;
    final static Logger logger = Logger.getLogger(BaseTest.class);

    @BeforeClass
    public void beforeMethod() {
        Properties commonProp = new Properties();
        InputStream inputStream = null;

        try{
            inputStream = new FileInputStream("src/main/resources/commonProperties.properties");
            commonProp.load(inputStream);
            oAuth = commonProp.getProperty("oAuth");
            baseUrl = commonProp.getProperty("baseUrl");
            user = commonProp.getProperty("user");
        }catch (FileNotFoundException e){
            logger.error(e.getStackTrace());
        }catch (IOException ioException){
            logger.error(ioException.getStackTrace());
        } finally {
            if (inputStream!=null){
                try{
                    inputStream.close();
                }catch (IOException ioException){
                    logger.error(ioException.getStackTrace());
                }
            }
        }

        //Creating header since its common each time
        header = new HashMap<>();
        header.put("Authorization", "token " + oAuth);
        logger.info("Create Headers for all requests");
    }

}
