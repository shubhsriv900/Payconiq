package com.payconiq.api.support.utils;

import com.payconiq.api.support.models.WebResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpMethodUtils {

    // Perform Get operation using CloseableHttpClient and storing the values returned.
    public static WebResponse get(String url, Map<String, String> headers){
        HttpGet request = new HttpGet(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        WebResponse webResponse  = null;
        try {

            if (headers != null) {
                Set<Map.Entry<String, String>> set = headers.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                    request.addHeader(me.getKey(), me.getValue());
                }
            }

            httpResponse = client.execute(request);
            webResponse = handleResponse(httpResponse);
        }catch (IOException e){
            System.out.println(e);
        }finally {
            // Closing the client everytime even of something fails
            try {
                client.close();
                if (null != httpResponse){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return webResponse;
    }


    public static WebResponse post(String url, Map<String, String> headers, String payload){
        HttpPost request = new HttpPost(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        WebResponse webResponse  = null;

        //adding headers
        try {

            if (headers != null) {
                Set<Map.Entry<String, String>> set = headers.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                    request.addHeader(me.getKey(), me.getValue());
                }
            }

            if (payload != null) {
                StringEntity payLoad = new StringEntity(payload);
                request.setEntity(payLoad);
            }

            httpResponse = client.execute(request);
            webResponse = handleResponse(httpResponse);
        }catch (IOException e){
            System.out.println(e);
        }finally {
            // Closing the client everytime even of something fails
            try {
                client.close();
                if (null != httpResponse){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return webResponse;
    }

    public static WebResponse patch(String url, Map<String, String> headers, String payload){
        HttpPatch request = new HttpPatch(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        WebResponse webResponse  = null;

        //adding headers
        try {

            if (headers != null) {
                Set<Map.Entry<String, String>> set = headers.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                    request.addHeader(me.getKey(), me.getValue());
                }
            }

            if (payload != null) {
                StringEntity payLoad = new StringEntity(payload);
                request.setEntity(payLoad);
            }

            httpResponse = client.execute(request);
            webResponse = handleResponse(httpResponse);
        }catch (IOException e){
            System.out.println(e);
        }finally {
            // Closing the client everytime even if something fails
            try {
                client.close();
                if (null != httpResponse){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return webResponse;
    }


    public static WebResponse delete(String url, Map<String, String> headers){
        HttpDelete request = new HttpDelete(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        WebResponse webResponse  = null;
        try {

            if (headers != null) {
                Set<Map.Entry<String, String>> set = headers.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                    request.addHeader(me.getKey(), me.getValue());
                }
            }

            httpResponse = client.execute(request);
            webResponse = handleResponse(httpResponse);
        }catch (IOException e){
            System.out.println(e);
        }finally {
            // Closing the client everytime even of something fails
            try {
                client.close();
                if (null != httpResponse){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return webResponse;
    }

    public static WebResponse put(String url, Map<String, String> headers){
        HttpPut request = new HttpPut(url);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        WebResponse webResponse  = null;
        try {

            if (headers != null) {
                Set<Map.Entry<String, String>> set = headers.entrySet();
                Iterator<Map.Entry<String, String>> i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, String> me = (Map.Entry<String, String>) i.next();
                    request.addHeader(me.getKey(), me.getValue());
                }
            }

            httpResponse = client.execute(request);
            webResponse = handleResponse(httpResponse);
        }catch (IOException e){
            System.out.println(e);
        }finally {
            // Closing the client everytime even of something fails
            try {
                client.close();
                if (null != httpResponse){
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return webResponse;
    }





    // Taking all the data from response and storing in WebResponse object
    private static  WebResponse handleResponse(CloseableHttpResponse httpresponse) throws IOException {
        WebResponse webResponse = new WebResponse();

        // Storing response code
        webResponse.setResponseCode(httpresponse.getStatusLine().getStatusCode());
        // Storing content type
        ContentType contentType1 = ContentType.getOrDefault(httpresponse.getEntity());
        webResponse.setResponseType(contentType1.getMimeType());

        // Storing the response of the request
        if (httpresponse.getEntity()!=null) {
            String body = EntityUtils.toString(httpresponse.getEntity());
            webResponse.setCurrentBody(body);
        }else{
            webResponse.setCurrentBody("null");
        }

        return webResponse;
    }
}
