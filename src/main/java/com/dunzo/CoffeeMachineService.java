package com.dunzo;

import com.dunzo.managers.InventoryManager;
import com.dunzo.models.Beverage;
import com.dunzo.models.CoffeeMachineInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoffeeMachineService {
    @Getter
    private final CoffeeMachineInput coffeeMachineInput;
    private final ThreadPoolExecutor threadPoolExecutor;
    @Getter
    private int totalSuccessfulBeverages;

    public CoffeeMachineService(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        coffeeMachineInput = objectMapper.readValue(inputStream, CoffeeMachineInput.class);
        int totalOutlets = coffeeMachineInput.getMachine().getOutlets().getCount();
        threadPoolExecutor = new ThreadPoolExecutor(totalOutlets, totalOutlets, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));
        totalSuccessfulBeverages = 0;
    }

    /**
     * Start processing of beverages
     */
    public void startMachine() {
        InventoryManager inventoryManager = InventoryManager.getInstance();

        Map<String, Integer> ingredients = coffeeMachineInput.getMachine().getItemsQuantity();
        ingredients.keySet().forEach(key -> inventoryManager.addInventory(key, ingredients.get(key)));

        Map<String, Map<String, Integer>> beverages = coffeeMachineInput.getMachine().getBeverages();
        beverages.keySet().forEach(key -> {
            Beverage beverage = new Beverage(key, beverages.get(key));
            processBeverage(beverage);
        });
    }

    /**
     * Add a new beverage thread in the threadpool
     * All beverages are checked and processed in parallel
     * @param beverage The Beverage to be processed
     */
    public void processBeverage(Beverage beverage) {
        threadPoolExecutor.execute(() -> {
            if (InventoryManager.getInstance().updateInventoryForBeverage(beverage)) {
                System.out.println(beverage.getName() + " is prepared");
                totalSuccessfulBeverages++;
            }
        });
    }

    public void stopMachine() {
        threadPoolExecutor.shutdown();
    }
}
