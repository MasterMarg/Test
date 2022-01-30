package com.company.test.service;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;

@Service(MainService.NAME)
public class MainServiceBean implements MainService {
    @Inject
    private MagicSquareService squareService;
    @Inject
    private LinesComparisonService linesService;

    @Override
    public String getResult(int type, String data) throws IOException {
        if (type == 1) return squareService.getResult(data);
        return linesService.getResult(data);
    }
}