package com.rest.qa.testMethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.qa.base.BaseClass;
import com.rest.qa.httpClient.RestClient;
import com.rest.qa.testData.Register;
import com.rest.qa.testData.Users;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class testPostMethod extends BaseClass {
    RestClient restClient;
    String serviceUrl;
    String apiUrl;
    String url;
    String registerUrl;
    String apiUrl2;
    CloseableHttpResponse closeableHttpResponse;
    Users users;

    public testPostMethod() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        serviceUrl = prop.getProperty("baseUrl");
        apiUrl = prop.getProperty("apiUsersUrl");
        url = serviceUrl + apiUrl;

        apiUrl2 = prop.getProperty("apiRegisterUrl");
        registerUrl = serviceUrl + apiUrl2;

        restClient = new RestClient();
    }

    @Test
    public void verifyPostMethod() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "application/json");

        //using Jackson API - mapping the data to POJO
        ObjectMapper mapper = new ObjectMapper();
        //pass the test data value as parameters to the users class constructor which will set the data
        Users users = new Users("morpheus", "leader");

        System.out.println(users.getName());
        System.out.println(users.getJob());

        //Object()POJO) to JSON conversion to a dummy file
        mapper.writeValue(new File("C:\\Users\\browse\\WebAutomation\\WebServiceAutomation\\src\\main\\java\\com\\rest\\qa\\testData\\userData.json"), users);

        //POJO to JSON format string
        String userJsonString = mapper.writeValueAsString(users);
        System.out.println(userJsonString);

        closeableHttpResponse = restClient.postMethod(url, userJsonString, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 201);

        String respString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject jsonResp = new JSONObject(respString);
        System.out.println("The response from the API is " + jsonResp);
 /*
        //parse JSON
        String name = testUtil.getValueByJPath(jsonResp,"/name");
        String job = testUtil.getValueByJPath(jsonResp,"/job");
        Assert.assertEquals(name, "Morpheus");
        Assert.assertEquals(job, "leader");
  */
        //JSON to Java Object conversion - unmarshelling
        Users respUsers = mapper.readValue(respString, Users.class);

        System.out.println(respUsers.getName());
        System.out.println(respUsers.getJob());

        Assert.assertEquals(respUsers.getName(), users.getName());
        Assert.assertEquals(respUsers.getJob(), users.getJob());
    }

    public void verifyPractisePostMethod() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "application/json");
        reqHeadersMap.put("username", "jdffhkjdshf");
        reqHeadersMap.put("password", "dfjfhgkjdf");

        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("Addie", "Intern");
        String jsonUser = mapper.writeValueAsString(users);

        closeableHttpResponse = restClient.practisePost(url, jsonUser, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

        String respString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject jsonObject = new JSONObject(respString);

        Users respUsers = mapper.readValue(respString, Users.class);

        Assert.assertEquals(respUsers.getName(), users.getName());


    }

    @Test
    public void verifyPractisePost() throws IOException {

        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("Anna", "Big Sister");
        String jsonUsers = mapper.writeValueAsString(users);

        closeableHttpResponse = restClient.practisePost(url, jsonUsers, reqHeadersMap);
        String respString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        Users respUsers = mapper.readValue(respString, Users.class);

        Assert.assertEquals(respUsers.getName(), users.getName());

    }

    @Test
    public void verifyRegisterPostMethod() throws IOException {
        HashMap<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        Register register = new Register("eve.holt@reqres.in", "pistol");
        String jsonPayloads = mapper.writeValueAsString(register);

        closeableHttpResponse = restClient.postMethod(registerUrl, jsonPayloads, reqHeadersMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        Assert.assertTrue(statusCode == 200);

        String respBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        //unmarshall
        Register respRegister = mapper.readValue(respBody, Register.class);
        Assert.assertEquals(respRegister.getId(), "4");
    }


}
