package com.rest.qa.testMethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.qa.base.BaseClass;
import com.rest.qa.httpClient.RestClient;
import com.rest.qa.testData.Users;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class testPutMethod extends BaseClass {
    String baseUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    Users users;
    public testPutMethod(){
        super();
    }

    @BeforeMethod
    public void setUp(){
        baseUrl = prop.getProperty("baseUrl");


        restClient = new RestClient();

    }
    @Test
    public void verifyPutMethod() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type","application/json");

        ObjectMapper mapper = new ObjectMapper();
        users = new Users("morpheus","zion resident");
        String jsonPayload = mapper.writeValueAsString(users);
        String user2Url = "/api/users/2";
        url = baseUrl + user2Url;
        CloseableHttpResponse closeableHttpResponse = restClient.putMethod(url,jsonPayload, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

        String respBody = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        Users respUsers = mapper.readValue(respBody, Users.class);

        Assert.assertEquals(respUsers.getName(),users.getName());
        Assert.assertEquals(respUsers.getJob(),users.getJob());

    }
}
