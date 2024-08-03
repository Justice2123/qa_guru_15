import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ListResourceTest extends TestBase {

    @Test
    void listDataNotEmptyTest() {
        given()
                .log()
                .all()
                .get("/unknown")
                .then().log().body()
                .statusCode(200)
                .body("data", is(notNullValue()));
    }


    @Test
    void listDataIdTest() {
        given()
                .log()
                .all()
                .get("/unknown")
                .then().log().body()
                .statusCode(200)
                .body("data[0].id", is(1));
    }

    @Test
    void unSuccessfulCreate415Test() {
        given()
                .log()
                .uri()
                .post("/users")
                .then().log().status()
                .statusCode(415);
    }

    @Test
    void successfulCreateTest() {

        String authData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }


    @Test
    void unsuccessfulCreateTest() {


        given()

                .log().uri()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);

    }


}
