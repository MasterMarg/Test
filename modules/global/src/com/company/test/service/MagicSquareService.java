package com.company.test.service;

public interface MagicSquareService {
    String NAME = "test_MagicSquareService";

    /**
     * Main method to calculate the closest (by cost) magic square to a given pattern and it's cost
     * @param string is a string input of a user on main screen
     * @return a string representation of a closest magic square and it's cost
     */
    String getResult(String string);
}