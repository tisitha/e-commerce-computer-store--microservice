import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class UserIntegrationTest {

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    public void givenLoginPayLoad_whenLogin_thenReturns200WithLoginResponseDTO(){
        String loginPayLoad = """
                {
                    "email":"user@email.com",
                    "password":"userpassword"
                }
                """;

         given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenInvalidLoginPayLoad_whenLogin_thenReturns401(){
        String loginPayLoad = """
                {
                    "email":"nonexistuser@email.com",
                    "password":"incorectuserpassword"
                }
                """;

        given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void givenToken_whenValidate_thenReturns200WithUserId(){
        String token = getUserToken();

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/validate")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();

    }

    @Test
    public void givenAdminToken_whenValidateAdmin_thenReturns200WithUserId(){
        String token = getAdminToken();

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/validate-admin")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();

    }

    @Test
    public void givenNonAdminAccountToken_whenValidateAdmin_thenReturns401(){
        String token = getUserToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/validate-admin")
                .then()
                .statusCode(401);
    }

    @Test
    public void givenInvalidToken_whenValidate_thenReturns401(){
        String token = "1234567890";

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/validate")
                .then()
                .statusCode(401);

    }

    @Test
    void userCanBeRegisteredAndLoginAndDeletedSuccessfully() {

        String registerPayLoad = """
                {
                     "email":"user2@email.com",
                
                     "password":"user2password",
                
                     "passwordRepeat":"user2password",
                
                     "firstName":"user2",
                
                     "lastName":"test",
                
                     "dob":"2000-01-01",
                
                     "address1":"no 1",
                
                     "address2":"road, city",
                
                     "contactNo":"+9412252267"
                 }
                """;

        String loginPayLoad = """
                {
                    "email":"user2@email.com",
                    "password":"user2password"
                }
                """;

        String PasswordDTO = """
                {
                    "password":"user2password"
                }
                """;

        //register
        given()
                        .contentType("application/json")
                        .body(registerPayLoad)
                        .when()
                        .post("/auth/register")
                        .then()
                        .statusCode(201);

        //login
        String userId = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .path("uid");

        //delete
        given()
                .contentType("application/json")
                .body(PasswordDTO)
                .when()
                .delete("auth/user-delete/"+userId)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenAlreadyTakenEmail_whenRegister_thenReturns409(){
        String registerPayLoad = """
                {
                     "email":"user@email.com",
                
                     "password":"userpassword",
                
                     "passwordRepeat":"userpassword",
                
                     "firstName":"user",
                
                     "lastName":"test",
                
                     "dob":"2000-01-01",
                
                     "address1":"no 1",
                
                     "address2":"road, city",
                
                     "contactNo":"+9412252267"
                 }
                """;

        given()
                .contentType("application/json")
                .body(registerPayLoad)
                .when()
                .post("auth/register")
                .then()
                .statusCode(409);
    }

    @Test
    public void givenShortPasswords_whenRegister_thenReturns400(){
        String registerPayLoad = """
                {
                     "email":"newuser@email.com",
                
                     "password":"1234567",
                
                     "passwordRepeat":"1234567",
                
                     "firstName":"user",
                
                     "lastName":"test",
                
                     "dob":"2000-01-01",
                
                     "address1":"no 1",
                
                     "address2":"road, city",
                
                     "contactNo":"+9412252267"
                 }
                """;

        given()
                .contentType("application/json")
                .body(registerPayLoad)
                .when()
                .post("auth/register")
                .then()
                .statusCode(400);
    }

    @Test
    public void givenNotMatchingPasswords_whenRegister_thenReturns409(){
        String registerPayLoad = """
                {
                     "email":"newuser@email.com",
                
                     "password":"userpassword1",
                
                     "passwordRepeat":"userpassword2",
                
                     "firstName":"user",
                
                     "lastName":"test",
                
                     "dob":"2000-01-01",
                
                     "address1":"no 1",
                
                     "address2":"road, city",
                
                     "contactNo":"+9412252267"
                 }
                """;

        given()
                .contentType("application/json")
                .body(registerPayLoad)
                .when()
                .post("auth/register")
                .then()
                .statusCode(409);
    }

    @Test
    public void givenUserData_whenUpdatingUser_thenReturns200(){
        String userId = getUid();
        String updatingUserPayLoad = """
                {
                      "email":"user@email.com",
                
                      "password":"userpassword",
                
                      "passwordRepeat":"userpassword",
                
                      "firstName":"user",
                
                      "lastName":"test",
                
                      "dob":"2000-01-01",
                
                      "address1":"no 1",
                
                      "address2":"road, city",
                
                      "contactNo":"+9412252267",
                
                      "currentPassword":"userpassword"
                  }
                """;

        given()
                .contentType("application/json")
                .body(updatingUserPayLoad)
                .when()
                .put("auth/user-update/"+userId)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenUserDataWithIncorrectPassword_whenUpdatingUser_thenReturns401(){

        String userId = getUid();
        String updatingUserPayLoad = """
                {
                      "email":"user@email.com",
                
                      "password":"userpassword",
                
                      "passwordRepeat":"userpassword",
                
                      "firstName":"user",
                
                      "lastName":"test",
                
                      "dob":"2000-01-01",
                
                      "address1":"no 1",
                
                      "address2":"road, city",
                
                      "contactNo":"+9412252267",
                
                      "currentPassword":"incorrectuserpassword"
                  }
                """;

        given()
                .contentType("application/json")
                .body(updatingUserPayLoad)
                .when()
                .put("auth/user-update/"+userId)
                .then()
                .statusCode(401);
    }

    private String getUserToken() {
        String loginPayLoad = """
                {
                    "email":"user@email.com",
                    "password":"userpassword"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();

        return response.jsonPath().getString("token");
    }

    private String getAdminToken() {
        String loginPayLoad = """
                {
                    "email":"admin@admin.com",
                    "password":"adminpassword"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();

        return response.jsonPath().getString("token");
    }

    private String getUid() {
        String loginPayLoad = """
                {
                    "email":"user@email.com",
                    "password":"userpassword"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();

        return response.jsonPath().getString("uid");
    }

}
