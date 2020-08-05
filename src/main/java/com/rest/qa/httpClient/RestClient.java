package com.rest.qa.httpClient;


import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    //GET method
    public CloseableHttpResponse getMethod(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // http get request
        // hit the get method with this url by clicking send(manually)
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return closeableHttpResponse;

    }

    //GET method with Request Header
    public CloseableHttpResponse getMethodWithHeader(String url, HashMap<String, String> headersHashMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // http get request
        //Add the required headers
        for (Map.Entry<String, String> entry : headersHashMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        // hit the get method with this url by clicking send(manually)
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        return closeableHttpResponse;

    }

    public CloseableHttpResponse postMethod(String url, String entityString, HashMap<String, String> reqHeadersMap) throws IOException {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //for payload to be entered in the body section of the api call
        httpPost.setEntity(new StringEntity(entityString));

        //header details for the api call
        for (Map.Entry<String, String> entry : reqHeadersMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public CloseableHttpResponse practisePost(String url, String entityString, HashMap<String, String> reqHeadersMap) throws IOException {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entityString));
        for(Map.Entry<String, String> entry : reqHeadersMap.entrySet()){
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public CloseableHttpResponse practisePostMethod(String url, String entityString, HashMap<String, String> reqHeadersMap ) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(entityString));
        for(Map.Entry<String, String> entry : reqHeadersMap.entrySet()){
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
        return closeableHttpResponse;
    }

    public CloseableHttpResponse putMethod(String url, String entityString, HashMap<String, String> reqHeadersMap) throws IOException {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(new StringEntity(entityString));
        for(Map.Entry<String, String> entry : reqHeadersMap.entrySet()){
            httpPut.addHeader(entry.getKey(),entry.getValue());
        }
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPut);
        return closeableHttpResponse;

    }

    public CloseableHttpResponse deleteMethod(String url) throws IOException {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpDelete);
        return closeableHttpResponse;
    }

}
