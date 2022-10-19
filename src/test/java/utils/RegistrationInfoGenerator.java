package utils;

import com.github.javafaker.Faker;
import data.RegistrationInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RegistrationInfoGenerator {

    @UtilityClass
    public static class RegistrationGenerator {
        private RegistrationInfo user;
        private Faker faker = new Faker();

        public static RegistrationInfo generateUser(String statusActiveOrBlocked) {
            String name = faker.name().username();
            String password = faker.internet().password();
            String status = statusActiveOrBlocked;
            return new RegistrationInfo(name, password, status);
        }
    }
}
