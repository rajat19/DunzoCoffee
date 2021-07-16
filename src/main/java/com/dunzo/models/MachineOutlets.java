package com.dunzo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MachineOutlets {
    @JsonProperty("count_n")
    private int count;
}
