package com.rest.qa.testMethods;

import com.rest.qa.base.BaseClass;
import com.rest.qa.httpClient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class testDeleteMethod extends BaseClass {
    String baseUrl;

    String url;
    RestClient restClient;

    public testDeleteMethod(){
        super();
    }

    @BeforeMethod
    public void setUp(){
        baseUrl = prop.getProperty("baseUrl");
        restClient = new RestClient();

    }

    @Test
    public void verifyDeleteMethod() throws IOException {
        String user2Url = "/api/users/2";
        url = baseUrl + user2Url;
        CloseableHttpResponse closeableHttpResponse = restClient.deleteMethod(url);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,204);
    }
}
