package com.rest.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    public static Properties prop;
    public int RESPONSE_STATUS_CODE_200 = 200;
    public int RESPONSE_STATUS_CODE_201 = 201;
    public int RESPONSE_STATUS_CODE_400 = 400;
    public int RESPONSE_STATUS_CODE_500 = 500;


    public BaseClass()  {
         prop = new Properties();
        FileInputStream ip = null;
        try {
            ip = new FileInputStream("C:\\Users\\browse\\WebAutomation\\WebServiceAutomation\\src\\main\\java\\com\\rest\\qa\\config\\config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
