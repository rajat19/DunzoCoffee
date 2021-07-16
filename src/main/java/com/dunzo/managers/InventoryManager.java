package com.dunzo.managers;

import com.dunzo.exceptions.IngredientNotAvailableException;
import com.dunzo.exceptions.InsufficientIngredientException;
import com.dunzo.models.Beverage;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private static InventoryManager inventoryManager;
    private final Map<String, Integer> inventory;

    private InventoryManager() {
        inventory = new HashMap<>();
    }

    public static InventoryManager getInstance() {
        if (inventoryManager == null) {
            synchronized (InventoryManager.class) {
                if (inventoryManager == null) {
                    inventoryManager = new InventoryManager();
                }
            }
        }
        return inventoryManager;
    }

    public synchronized boolean inventoryUpdatedForBeverage(Beverage beverage) {
        try {
            checkAndUpdateInventory(beverage);
            return true;
        } catch (InsufficientIngredientException | IngredientNotAvailableException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void checkAndUpdateInventory(Beverage beverage) throws IngredientNotAvailableException, InsufficientIngredientException {
        Map<String, Integer> requiredIngredients = beverage.getIngredientsMap();
        for(Map.Entry<String, Integer> entry: requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            int quantityLeft = inventory.getOrDefault(ingredient, 0);
            if (quantityLeft == 0) {
                throw new IngredientNotAvailableException(beverage.getName(), ingredient);
            } else if (requiredQuantity > quantityLeft) {
                throw new InsufficientIngredientException(beverage.getName(), ingredient);
            }
        }

        for(Map.Entry<String, Integer> entry: requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int quantityUsed = entry.getValue();
            int existingQuantity = inventory.getOrDefault(ingredient, 0);
            inventory.put(ingredient, existingQuantity - quantityUsed);
        }
    }

    public void addInventory(String ingredient, int quantity) {
        inventory.put(ingredient, inventory.getOrDefault(ingredient, 0) + quantity);
    }

    public void resetInventory() {
        inventory.clear();
    }
}
