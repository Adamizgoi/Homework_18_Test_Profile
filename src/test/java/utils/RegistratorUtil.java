package utils;

import data.RegistrationInfo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static io.restassured.RestAssured.given;

/**
 * Класс регистрирует пользователей в системе. Для регистрации в метод register нужно передать настройки RequestSpecification
 * и информацию о юзере (объект RegistrationInfo)
 */

@UtilityClass
public class RegistratorUtil {

    @UtilityClass
    public static class Registrator {
        private RequestSpecification requestSpec;

        public static void registrateUser(RegistrationInfo user) {
            given().spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static void setUpWebApi() {
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri("http://localhost")
                    .setPort(9999)
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();
        }
    }
}