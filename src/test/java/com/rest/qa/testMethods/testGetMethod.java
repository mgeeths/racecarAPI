package com.rest.qa.testMethods;

import com.rest.qa.base.BaseClass;
import com.rest.qa.httpClient.RestClient;
import com.rest.qa.testUtil.testUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class testGetMethod extends BaseClass {
    RestClient restClient;
    String serviceUrl;
    String apiUsersUrl;
    String url;
    String user3Url;
    String page2Url;
    String invalidUserUrl;

    CloseableHttpResponse closeableHttpResponse;

    public testGetMethod() {
        super();
    }

    @BeforeMethod
    public void setUp() {

        serviceUrl = prop.getProperty("baseUrl");
        apiUsersUrl = prop.getProperty("apiUsersUrl");
        url = serviceUrl + apiUsersUrl;

        restClient = new RestClient();
    }

    @Test(priority = 1)
    public void verifyGetMethod() throws IOException {

        closeableHttpResponse = restClient.getMethod(url);

        //Status Code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);

        //response body in JSON format
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");//gets the entire response as a string
        JSONObject responseJson = new JSONObject(responseString);//converts the string to JSON format
        System.out.println("JSON Response is " + responseJson);

        //parse JSON Object-- single data value-- simple JSON Object
        String pageValue = testUtil.getValueByJPath(responseJson, "per_page");
        Assert.assertEquals(Integer.parseInt(pageValue), 6, "Actual number of pages " + pageValue);

        String totalValue = testUtil.getValueByJPath(responseJson, "total");
        Assert.assertEquals(Integer.parseInt(totalValue), 12, "Total value " + totalValue);

        //parse JSON array-- JSON
        String lastName = testUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        String id = testUtil.getValueByJPath(responseJson, "/data[0]/id");
        String avatar = testUtil.getValueByJPath(responseJson, "/data[0]/avatar");
        String firstName = testUtil.getValueByJPath(responseJson, "/data[0]/first_name");

        Assert.assertEquals(lastName, "Bluth", "Actual Lastname is  " + lastName);
        Assert.assertEquals(id, "1", "Actual id is  " + id);
        Assert.assertEquals(firstName, "George", "Actual firstname is  " + firstName);


        //get all response Headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println(allHeaders);

        Assert.assertEquals(allHeaders.get("Server"), "cloudflare");
        Assert.assertTrue(allHeaders.get("Content-Type").contains("application/json"));

    }

    @Test(priority = 2)
    public void verifyGetMethodWithHeaders() throws IOException {

        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");

        closeableHttpResponse = restClient.getMethodWithHeader(url, headersMap);

        //Status Code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code is " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);

        //response body in JSON format
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");//gets the entire response as a string
        JSONObject responseJson = new JSONObject(responseString);//converts the string to JSON format
        System.out.println("JSON Response is " + responseJson);

        //parse JSON Object-- single data value-- simple JSON Object
        String pageValue = testUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println(pageValue);
        Assert.assertEquals(Integer.parseInt(pageValue), 6, "Actual number of pages " + pageValue);

        String totalValue = testUtil.getValueByJPath(responseJson, "/total");
        Assert.assertEquals(Integer.parseInt(totalValue), 12, "Total value " + totalValue);

        //parse JSON array-- JSON
        String lastName = testUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        String id = testUtil.getValueByJPath(responseJson, "/data[0]/id");
        String avatar = testUtil.getValueByJPath(responseJson, "/data[0]/avatar");
        String firstName = testUtil.getValueByJPath(responseJson, "/data[0]/first_name");
        System.out.println(lastName);
        System.out.println(firstName);
        System.out.println(id);

        Assert.assertEquals(lastName, "Bluth", "Actual Lastname is  " + lastName);
        Assert.assertEquals(id, "1", "Actual id is  " + id);
        Assert.assertEquals(firstName, "George", "Actual firstname is  " + firstName);


        //get all response Headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println(allHeaders);

        Assert.assertEquals(allHeaders.get("Server"), "cloudflare");
        System.out.println(allHeaders.get("Server"));

        Assert.assertTrue(allHeaders.get("Content-Type").contains("application/json"));
        System.out.println(allHeaders.get("Content-Type"));

    }

    @Test(priority = 3)
    public void verifyGetThirdUserDetails() throws IOException {

        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "apllication/json");
        user3Url = url +"/3";
        CloseableHttpResponse closeableHttpResponse = restClient.getMethodWithHeader(user3Url, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200);

        String respBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        System.out.println(respBody);
        JSONObject jsonRespBody = new JSONObject(respBody);
        String companyField = testUtil.getValueByJPath(jsonRespBody, "/ad/company");
        System.out.println("Company Field value is " + companyField);
        Assert.assertEquals(companyField, "StatusCode Weekly");

        String firstName = testUtil.getValueByJPath(jsonRespBody, "/data/first_name");
        System.out.println("Firstname of User3 is " + firstName);
        Assert.assertEquals(firstName, "Emma");

    }

    @Test(priority = 4)
    public void verifyGetPageTwoUsers() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "apllication/json");
        page2Url = url +"?page=2";
        System.out.println(page2Url);
        CloseableHttpResponse closeableHttpResponse = restClient.getMethodWithHeader(page2Url, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200);

        String respBody = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject jsonRespBody =  new JSONObject(respBody);

        String firstName = testUtil.getValueByJPath(jsonRespBody,"/data[2]/first_name");
        Assert.assertEquals(firstName,"Tobias");
 }

    @Test(priority = 5)
    public void verifyInvalidUser() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "apllication/json");
        invalidUserUrl = url +"/23";
        System.out.println(page2Url);
        CloseableHttpResponse closeableHttpResponse = restClient.getMethodWithHeader(invalidUserUrl, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 404);

    }



    @AfterMethod
    public void tearDown() {

    }

}
