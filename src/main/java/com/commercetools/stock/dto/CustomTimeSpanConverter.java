package com.commercetools.stock.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CustomTimeSpanConverter implements Converter<String, TimeSpan> {
    @Override
    public TimeSpan convert(String source) {
        try {
            return TimeSpan.valueOf(source);
        } catch(Exception e) {
            return null; // or SortEnum.asc
        }
    }
}
