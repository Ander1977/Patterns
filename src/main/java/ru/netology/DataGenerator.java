package ru.netology;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    public DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationByNamePhone generateByNamePhone(String locale) {
            Faker faker = new Faker(new Locale("RU"));
            return new RegistrationByNamePhone(
                    faker.name().lastName() + " " + faker.name().firstName(),
                    faker.phoneNumber().phoneNumber());
        }
    }
}
