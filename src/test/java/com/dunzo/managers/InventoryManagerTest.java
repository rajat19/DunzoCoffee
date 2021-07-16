package com.dunzo.managers;

import com.dunzo.exceptions.IngredientNotAvailableException;
import com.dunzo.exceptions.InsufficientIngredientException;
import com.dunzo.models.Beverage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    private final InventoryManager inventoryManager = InventoryManager.getInstance();

    @AfterEach
    public void setUp() {
        inventoryManager.resetInventory();
    }

    @Test
    public void testCheckAndUpdateFor1ValidBeverage() {
        Beverage beverage = new Beverage("A", new HashMap<>());
        beverage.getIngredientsMap().put("ing1", 10);
        beverage.getIngredientsMap().put("ing2", 20);

        inventoryManager.addInventory("ing1", 100);
        inventoryManager.addInventory("ing2", 50);
        inventoryManager.addInventory("ing3", 10);

        boolean actual = inventoryManager.inventoryUpdatedForBeverage(beverage);
        assertTrue(actual);
    }

    @Test
    public void testCheckAndUpdateForMultipleValidBeverages() {
        Beverage beverage1 = new Beverage("A", new HashMap<>());
        beverage1.getIngredientsMap().put("ing1", 10);
        beverage1.getIngredientsMap().put("ing2", 20);

        Beverage beverage2 = new Beverage("A", new HashMap<>());
        beverage2.getIngredientsMap().put("ing2", 10);
        beverage2.getIngredientsMap().put("ing3", 10);

        Beverage beverage3 = new Beverage("A", new HashMap<>());
        beverage3.getIngredientsMap().put("ing1", 70);
        beverage3.getIngredientsMap().put("ing2", 20);

        inventoryManager.addInventory("ing1", 100);
        inventoryManager.addInventory("ing2", 50);
        inventoryManager.addInventory("ing3", 10);

        boolean actual1 = inventoryManager.inventoryUpdatedForBeverage(beverage1);
        boolean actual2 = inventoryManager.inventoryUpdatedForBeverage(beverage2);
        boolean actual3 = inventoryManager.inventoryUpdatedForBeverage(beverage3);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testCheckAndUpdateForInsufficientIngredientException() throws InsufficientIngredientException, IngredientNotAvailableException {
        Beverage beverage1 = new Beverage("A", new HashMap<>());
        beverage1.getIngredientsMap().put("ing1", 10);
        beverage1.getIngredientsMap().put("ing2", 20);

        Beverage beverage2 = new Beverage("A", new HashMap<>());
        beverage2.getIngredientsMap().put("ing2", 10);
        beverage2.getIngredientsMap().put("ing3", 10);

        Beverage beverage3 = new Beverage("A", new HashMap<>());
        beverage3.getIngredientsMap().put("ing1", 70);
        beverage3.getIngredientsMap().put("ing2", 30);

        inventoryManager.addInventory("ing1", 100);
        inventoryManager.addInventory("ing2", 50);
        inventoryManager.addInventory("ing3", 10);

        inventoryManager.checkAndUpdateInventory(beverage1);
        inventoryManager.checkAndUpdateInventory(beverage2);
        InsufficientIngredientException exception = assertThrows(InsufficientIngredientException.class, () ->inventoryManager.checkAndUpdateInventory(beverage3));
        assertTrue(exception.getMessage().contains("is not sufficient"));
    }

    @Test
    public void testCheckAndUpdateForIngredientNotAvailableException() throws InsufficientIngredientException, IngredientNotAvailableException {
        Beverage beverage1 = new Beverage("A", new HashMap<>());
        beverage1.getIngredientsMap().put("ing1", 10);
        beverage1.getIngredientsMap().put("ing2", 20);

        Beverage beverage2 = new Beverage("A", new HashMap<>());
        beverage2.getIngredientsMap().put("ing2", 10);
        beverage2.getIngredientsMap().put("ing3", 10);

        Beverage beverage3 = new Beverage("A", new HashMap<>());
        beverage3.getIngredientsMap().put("ing1", 70);
        beverage3.getIngredientsMap().put("ing4", 30);

        inventoryManager.addInventory("ing1", 100);
        inventoryManager.addInventory("ing2", 50);
        inventoryManager.addInventory("ing3", 10);

        inventoryManager.checkAndUpdateInventory(beverage1);
        inventoryManager.checkAndUpdateInventory(beverage2);
        IngredientNotAvailableException exception = assertThrows(IngredientNotAvailableException.class, () ->inventoryManager.checkAndUpdateInventory(beverage3));
        assertTrue(exception.getMessage().contains("is not available"));
    }
}
