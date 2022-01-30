package com.company.test.service;

import java.io.IOException;

public interface MainService {
    String NAME = "test_MainService";

    /**
     * This method is a Main Service's method to handle incoming calls and send it to a proper minor service,
     * just in case you have a lot of tasks, services etc.
     *
     * @param type is an int number, representing a type of a chosen task on main screen
     * @param data is a string input of a user on main screen
     * @return a {@code String} representation of a calculated result
     */
    String getResult(int type, String data) throws IOException;
}