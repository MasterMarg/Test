package com.company.test.service;

import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.util.Arrays;

@Service(LinesComparisonService.NAME)
public class LinesComparisonServiceBean implements LinesComparisonService {
    @Inject
    private Messages messages;

    @Override
    public String getResult(String string) {
        StringBuilder answer = new StringBuilder();
        String[][] data = getDataFromString(string);
        if (data.length > 2 || data.length < 1)
            throw new RuntimeException(messages.getMainMessage("exception"));
        Arrays.stream(data[1]).flatMap(o -> Arrays.stream(data[0]).filter(o::contains)).distinct().
                sorted().forEach(o -> answer.append(o).append(" "));
        return answer.length() > 0 ? answer.toString().trim() : messages.getMainMessage("emptyAnswer");
    }

    /**
     * This method divides a solid string into an actual two-dimensional array of strings
     *
     * @param string is a string input of a user made by rules in the description of data field
     * @return a {@code String} two-dimensional array
     */
    private String[][] getDataFromString(String string) {
        return Arrays.stream(string.split("\n")).map(String::trim).
                map(o -> Arrays.stream(o.split(" ")).map(String::trim).toArray(String[]::new)).
                toArray(String[][]::new);
    }
}