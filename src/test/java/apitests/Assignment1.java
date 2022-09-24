package apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Assignment1 {

    @Test
    public void api_assignment() {
        File file = new File("Config/config.properties");

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();

        try {
            assert fileInput != null;
            properties.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RestAssured.baseURI = properties.getProperty("apiurl");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        Response responseString;
        responseString = given()
                .contentType("application/json")
                .when()
                .get(baseURI)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        String address = responseString.jsonPath().getString("address");
        System.out.println("Address returned in API response :  " +address);
        Reporter.log("Address returned in API response :  " +address);

        String employeeDetails = responseString.jsonPath().getString("employeeDetails");
        System.out.println("Employee Details returned in API response :  " +employeeDetails);
        Reporter.log("Employee Details returned in API response :  " +employeeDetails);
    }

}
