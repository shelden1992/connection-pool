package com.bobocode.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Created by Shelupets Denys on 04.12.2021.
 */
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Note {
    Long id;
    String body;
}
