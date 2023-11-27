package com.api.javalabsapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Tipos records não podem ter seus valores alterados, por isso eles só tem setters gerados automaticamente e por default são privados e do tipo final
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {    
}