package com.dunzo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Machine {
    private MachineOutlets outlets;
    @JsonProperty("total_items_quantity")
    private Map<String, Integer> itemsQuantity;
    private Map<String, Map<String, Integer>> beverages;
}
