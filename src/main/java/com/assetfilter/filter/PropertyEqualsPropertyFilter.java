package com.assetfilter.filter;

import com.assetfilter.Filter;

import java.util.Map;

public class PropertyEqualsPropertyFilter implements Filter {

    private final String propertyA;
    private final String propertyB;

    public PropertyEqualsPropertyFilter(String propertyA, String propertyB) {
        this.propertyA = propertyA;
        this.propertyB = propertyB;
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        String a = asset.get(propertyA);
        String b = asset.get(propertyB);

        if (a == null || b == null) {
            return false;
        }

        return a.equalsIgnoreCase(b);
    }

    @Override
    public String toString() {
        return propertyA + " == " + propertyB;
    }
}