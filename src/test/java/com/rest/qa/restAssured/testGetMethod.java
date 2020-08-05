package com.rest.qa.restAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class testGetMethod {

    @Test
    public void testNumberOfCircuits_2017(){
        given().
        when().
                get("http://ergast.com/api/f1/2017/circuits.json").
        then().assertThat().
                statusCode(200).and().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20)).and().and().
                headers("content-length",equalTo("4551"));

    }
}
