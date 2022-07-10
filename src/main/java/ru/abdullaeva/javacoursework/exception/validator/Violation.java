package ru.abdullaeva.javacoursework.exception.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {
    private final String nameOfField;
    private final String message;

}

