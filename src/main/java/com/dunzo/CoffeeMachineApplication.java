package com.dunzo;

import java.io.IOException;
import java.io.InputStream;

public class CoffeeMachineApplication {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = CoffeeMachineApplication.class.getResourceAsStream("/test.json");
        CoffeeMachineService coffeeMachineService = new CoffeeMachineService(inputStream);
        coffeeMachineService.startMachine();
        coffeeMachineService.stopMachine();
    }
}
