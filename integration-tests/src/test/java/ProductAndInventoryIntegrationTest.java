import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ProductAndInventoryIntegrationTest {

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    public void givenSize_whenGetTopProducts_thenReturns200WithListOfProduct(){

        given()
                .when()
                .get("product/get-top-picks/10")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenSize_whenGetNewProducts_thenReturns200WithListOfProduct(){

        given()
                .when()
                .get("product/get-new/10")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenSearchTermAndSize_whenSearch_thenReturns200WithListOfProduct(){

        given()
                .when()
                .get("product/search/p/10")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenSize_whenGetDealProducts_thenReturns200WithListOfProduct(){

        given()
                .when()
                .get("product/get-deals/10")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenId_whenGetAProductsFromCasings_thenReturns200WithTheProduct(){

        given()
                .when()
                .get("product/category/casings/item/5911913c-ab8f-4b4c-824e-bbf8338c3501")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void whenGetFilerOptionsOfCasing_thenReturns200withFilterOptionsOfCasing(){

        given()
                .when()
                .get("product/category/casings/filters")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void givenCasingGetRequestDTO_whenGetProductCasings_thenReturns200WithProductPageSortDtoOfCasings(){
        String CasingGetRequestDTO = """
                {
                    "pageNumber":0,
                    "pageSize":1,
                    "sortBy":"name",
                    "dir":"asc",
                    "brand":[],
                    "caseType":[],
                    "maxGPULength":[],
                    "includedFans":[]
                }
                """;

        given()
                .contentType("application/json")
                .body(CasingGetRequestDTO)
                .when()
                .get("product/category/casings/get")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
    }

    @Test
    public void adminLoginAndAddProductItemAndUpdateAndDeleteSuccessfully(){
        String loginPayLoad = """
                {
                    "email":"admin@admin.com",
                    "password":"adminpassword"
                }
                """;

        String casing = """
                {
                  "name": "NovaTech phantom-x7",
                  "imgUrl": "https://example.com/images/phantom-x7.jpg",
                  "description": "A mid-tower case with tempered glass panels and RGB lighting.",
                  "price": 25000,
                  "isNew": true,
                  "isTop": false,
                  "deal": 23000,
                  "brand": "NovaTech",
                  "caseType": "Mid Tower",
                  "maxGPULength": "360mm",
                  "includedFans": "3x 120mm RGB fans",
                  "quantity": 43
                }
                """;

        String updatedCasing = """
                {
                  "name": "NovaTech phantom-x7",
                  "imgUrl": "https://example.com/images/phantom-x7.jpg",
                  "description": "A mid-tower case with tempered glass panels and RGB lighting.",
                  "price": 25000,
                  "isNew": true,
                  "isTop": false,
                  "deal": 0,
                  "brand": "NovaTech",
                  "caseType": "Mid Tower",
                  "maxGPULength": "360mm",
                  "includedFans": "3x 120mm RGB fans",
                  "quantity": 40
                }
                """;

        String token = given()
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

        String productId = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(casing)
                .when()
                .post("admin/product/category/casings/add")
                .then()
                .statusCode(201)
                .body(notNullValue())
                .extract()
                .response()
                .path("id");

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(updatedCasing)
                .when()
                .put("admin/product/category/casings/update/"+productId)
                .then()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/admin/product/category/casings/delete/"+productId)
                .then()
                .statusCode(200);
    }

    @Test
    public void nonAdminLoginAndAddProductThenFailed(){
        String loginPayLoad = """
                {
                    "email":"user@email.com",
                    "password":"userpassword"
                }
                """;

        String casing = """
                {
                  "name": "NovaTech phantom-x7",
                  "imgUrl": "https://example.com/images/phantom-x7.jpg",
                  "description": "A mid-tower case with tempered glass panels and RGB lighting.",
                  "price": 25000,
                  "isNew": true,
                  "isTop": false,
                  "deal": 23000,
                  "brand": "NovaTech",
                  "caseType": "Mid Tower",
                  "maxGPULength": "360mm",
                  "includedFans": "3x 120mm RGB fans",
                  "quantity": 43
                }
                """;

        String token = given()
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

        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(casing)
                .when()
                .post("admin/product/category/casings/add")
                .then()
                .statusCode(401);
    }
}
