import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderIntegrationTest {

    static String token;
    static String uid;

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @BeforeAll
    public static void givenLoginPayLoad_whenLogin_thenReturns200WithLoginResponseDTO(){
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

        token = response.path("token");
        uid = response.path("uid");
    }

    @Test
    public void userAddAndUpdateAndDeleteCartItem(){

        String CartItemRequestDTO = """
                {
                        "productId": "5911913c-ab8f-4b4c-824e-bbf8338c3501",
                        "category": "Casing",
                        "quantity": 2,
                        "customerId": "fa29d06d-ad5e-4673-9a9d-e9d15fe8678c"
                }
                """;

        //add
        String cartItemId = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(CartItemRequestDTO)
                .when()
                .post("cart/add")
                .then()
                .statusCode(201)
                .body(notNullValue())
                .extract()
                .response()
                .path("id");

        //update
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .put("cart/update?id="+cartItemId+"&quantity=1")
                .then()
                .statusCode(201);

        //delete
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("cart/"+cartItemId)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenUserToken_whenGetCartOfUser_then200WithCart(){

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("cart/user/get")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenUserTokenAndOrderGetRequestDTO_whenGetOrderHistoryOfUser_then200WithOrders(){

        String OrderGetRequestDTO = """
                {
                  "pageNumber": 0,
                  "pageSize": 1,
                  "sortBy": "dateTime",
                  "dir": "desc"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .contentType("application/json")
                .body(OrderGetRequestDTO)
                .get("/order/get")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void adminLoginAndGetAllOrdersHistorySuccessfully(){

        String loginPayLoad = """
                {
                    "email":"admin@admin.com",
                    "password":"adminpassword"
                }
                """;

        String adminToken = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("auth/login")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response()
                .path("token");

        String OrderGetRequestDTO = """
                {
                  "pageNumber": 0,
                  "pageSize": 1,
                  "sortBy": "dateTime",
                  "dir": "desc"
                }
                """;

        given()
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .contentType("application/json")
                .body(OrderGetRequestDTO)
                .get("/admin/order")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void NonAdminLoginAndGetAllOrdersHistoryThenFailed(){

        String OrderGetRequestDTO = """
                {
                  "pageNumber": 0,
                  "pageSize": 1,
                  "sortBy": "dateTime",
                  "dir": "desc"
                }
                """;

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .contentType("application/json")
                .body(OrderGetRequestDTO)
                .get("/admin/order")
                .then()
                .statusCode(401)
                .body(notNullValue())
                .extract()
                .response();
    }
}
