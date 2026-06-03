package com.assetfilter.filter;

import com.assetfilter.Filter;

import java.util.Map;

public class PropertyEqualsFilter implements Filter {

    private final String property;
    private final String expected;

    public PropertyEqualsFilter(
            String property,
            String expected) {

        this.property = property;
        this.expected = expected;
    }

    @Override
    public boolean matches(Map<String, String> asset) {

        String value = asset.get(property);

        return value != null
                && value.equalsIgnoreCase(expected);
    }

    @Override
    public String toString() {
        return "property " + property + " = '" + expected + "'";
    }
}