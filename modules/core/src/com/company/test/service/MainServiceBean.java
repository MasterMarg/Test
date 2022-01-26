package com.company.test.service;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(MainService.NAME)
public class MainServiceBean implements MainService {
    @Inject
    private MagicSquareService squareService;
    @Inject
    private LinesComparisonService linesService;

    @Override
    public String getResult(int type, String data) {
        if (type == 1) return squareService.getResult(data);
        return linesService.getResult(data);
    }
}