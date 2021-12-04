package com.bobocode.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Created by Shelupets Denys on 04.12.2021.
 */
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    Long id;
    String firstName;
    String lastName;
    String email;
    String address;

}
