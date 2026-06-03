package com.assetfilter.filter.composite;

import com.assetfilter.Filter;

import java.util.Arrays;
import java.util.Map;

public class OrFilter implements Filter {

    private final Filter[] filters;

    public OrFilter(Filter... filters) {
        this.filters = filters;
    }

    @Override
    public boolean matches(Map<String, String> asset) {

        return Arrays.stream(filters)
                .anyMatch(f -> f.matches(asset));
    }

    @Override
    public String toString() {
        return "(" +
                Arrays.stream(filters)
                        .map(Filter::toString)
                        .reduce((a, b) -> a + " OR " + b)
                        .orElse("")
                + ")";
    }
}