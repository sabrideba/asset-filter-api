package com.assetfilter.engine;

import com.assetfilter.Filter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterEngine {

    public List<Map<String, String>> apply(
            List<Map<String, String>> assets,
            Filter filter
    ) {
        return assets.stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }
}