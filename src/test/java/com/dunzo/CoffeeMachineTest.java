package com.dunzo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeMachineTest {

    @Test
    public void testValidInput3Outlets() throws IOException {
        InputStream inputStream = CoffeeMachineTest.class.getResourceAsStream("/test1.json");
        CoffeeMachineService coffeeMachineService = new CoffeeMachineService(inputStream);
        coffeeMachineService.startMachine();
        assertEquals(4, coffeeMachineService.getCoffeeMachineInput().getMachine().getBeverages().size());
        assertEquals(2, coffeeMachineService.getTotalSuccessfulBeverages());
        coffeeMachineService.stopMachine();
    }

    @Test
    public void testValidInput1OutletInsufficientIngredient() throws IOException {
        InputStream inputStream = CoffeeMachineTest.class.getResourceAsStream("/test2.json");
        CoffeeMachineService coffeeMachineService = new CoffeeMachineService(inputStream);
        coffeeMachineService.startMachine();
        assertEquals(4, coffeeMachineService.getCoffeeMachineInput().getMachine().getBeverages().size());
        assertEquals(0, coffeeMachineService.getTotalSuccessfulBeverages());
        coffeeMachineService.stopMachine();
    }

    @Test
    public void testValidInput4Outlets() throws IOException {
        InputStream inputStream = CoffeeMachineTest.class.getResourceAsStream("/test3.json");
        CoffeeMachineService coffeeMachineService = new CoffeeMachineService(inputStream);
        coffeeMachineService.startMachine();
        assertEquals(7, coffeeMachineService.getCoffeeMachineInput().getMachine().getBeverages().size());
        assertEquals(5, coffeeMachineService.getTotalSuccessfulBeverages());
        coffeeMachineService.stopMachine();
    }
}
