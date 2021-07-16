package com.dunzo.exceptions;

public class IngredientNotAvailableException extends Exception{
    public IngredientNotAvailableException(String beverage, String ingredient) {
        super(beverage + " cannot be prepared because " + ingredient + " is not available");
    }
}
