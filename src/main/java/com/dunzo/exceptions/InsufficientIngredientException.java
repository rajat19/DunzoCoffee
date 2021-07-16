package com.dunzo.exceptions;

public class InsufficientIngredientException extends Exception{
    public InsufficientIngredientException(String beverage, String ingredient) {
        super(beverage + " cannot be prepared because " + ingredient + " is not sufficient");
    }
}
