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

    /**
     *
     * @param beverage The Beverage to be processed
     * @return true if inventory is updated else false
     */
    public synchronized boolean updateInventoryForBeverage(Beverage beverage) {
        try {
            checkAndUpdateInventory(beverage);
            return true;
        } catch (InsufficientIngredientException | IngredientNotAvailableException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param beverage The Beverage to be processed
     * @throws IngredientNotAvailableException if ingredient not available in inventory
     * @throws InsufficientIngredientException if sufficient quantity of ingredient not present in inventory
     */
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

        // If beverage can be prepared, remove beverage's ingredients from the inventory
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

    /**
     * used for clearing inventory map (used in testing)
     */
    public void resetInventory() {
        inventory.clear();
    }
}
