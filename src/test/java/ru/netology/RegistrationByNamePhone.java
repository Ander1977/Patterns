package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class RegistrationByNamePhone {
    private String fullName;
    private String phoneNumber;

}
