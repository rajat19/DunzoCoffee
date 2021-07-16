package com.dunzo;

import java.io.IOException;
import java.io.InputStream;

public class CoffeeMachineApplication {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Input file name required");
        }
        InputStream is = CoffeeMachineApplication.class.getResourceAsStream("/test.json");
        CoffeeMachineService coffeeMachineService = new CoffeeMachineService(is);
        coffeeMachineService.startMachine();
        coffeeMachineService.stopMachine();
    }
}
