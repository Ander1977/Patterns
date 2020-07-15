
import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    DataGenerator(){}

    public static class Registration {
        private Registration(){}

        public static RegistrationByNamePhone generateByNamePhone(String locale){
            Faker faker = new Faker(new Locale("RU"));
            return new RegistrationByNamePhone(
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
        }
    }
}
