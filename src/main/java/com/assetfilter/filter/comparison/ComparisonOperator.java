package com.assetfilter.filter.comparison;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ComparisonOperator {
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    ;

    private final String desc;
}
