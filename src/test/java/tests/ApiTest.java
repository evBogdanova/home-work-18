package tests;

import lombok.UserData;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    @Test
    void successfulSingleUserTest() {
        UserData data = Spec.request
                .when()
                .get("/users/10")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(UserData.class);
        assertEquals(10, data.getUser().getId());
        assertEquals("byron.fields@reqres.in", data.getUser().getEmail());
        assertEquals("Byron", data.getUser().getFirstname());
        assertEquals("https://reqres.in/img/faces/10-image.jpg", data.getUser().getAvatar());
    }

    @Test
    void unsuccessfulSingleUserTest() {
                Spec.request
                .when()
                .get("/users/50")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    void successfulListUserTest() {
                Spec.request
                .when()
                .get("/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.findAll{it.id = 7}.email.flatten()",
                        hasItem("michael.lawson@reqres.in"))
                .body("data.findAll{it.id = 7}.first_name.flatten()",
                        hasItem("Michael"))
                .body("data.findAll{it.id = 7}.last_name.flatten()",
                        hasItem("Lawson"))
                .body("data.findAll{it.id = 7}.avatar.flatten()",
                        hasItem("https://reqres.in/img/faces/7-image.jpg"));
    }

    @Test
    void successfulListResourcesTest() {
                Spec.request
                .when()
                .get("/unknown?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.findAll{it.id = 7}.name.flatten()",
                        hasItem("sand dollar"))
                .body("data.findAll{it.id = 10}.year.flatten()",
                        hasItem(2009))
                .body("data.findAll{it.year > 2010}.color.flatten()",
                        hasItem("#D94F70"))
                .body("data.findAll{it.color = '#BF1932'}.pantone_value.flatten()",
                        hasItem("15-5519"));
    }

    @Test
    void unsuccessfulRegisterTest() {
                Spec.request
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}