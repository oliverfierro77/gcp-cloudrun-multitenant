package com.pudulabs.cloudrun.dataclean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCleanResponse {

    private boolean status;
    private int rows;
    private String country;
}