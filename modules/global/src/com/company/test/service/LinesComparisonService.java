package com.company.test.service;

public interface LinesComparisonService {
    String NAME = "test_LinesComparisonService";

    /**
     * This method is building a new string representation of an {@link String} array from the first line of
     * given parameter string which are substrings of a strings in a second line of a given parameter string
     *
     * @param string is a solid string input of a user on main screen
     * @return a {@code String} as a calculated result
     */
    String getResult(String string);
}