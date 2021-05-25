package tests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

public class Spec {
    public static RequestSpecification request = with()
            .baseUri("https://reqres.in")
            .filter(customLogFilter().withCustomTemplates())
            .basePath("/api")
            .log().all()
            .contentType(ContentType.JSON);
}
