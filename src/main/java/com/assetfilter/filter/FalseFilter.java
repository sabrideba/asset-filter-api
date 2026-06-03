package com.assetfilter.filter;

import com.assetfilter.Filter;

import java.util.Map;

public class FalseFilter implements Filter {

    @Override
    public boolean matches(Map<String, String> asset) {
        return false;
    }

    @Override
    public String toString() {
        return "false";
    }
}