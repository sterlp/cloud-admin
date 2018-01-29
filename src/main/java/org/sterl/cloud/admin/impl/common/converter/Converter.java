package org.sterl.cloud.admin.impl.common.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main purpose of a converter is to translate between a BE and an API object.
 * 
 * @param <IN> the source class to convert
 * @param <OUT> the destination class
 */
public interface Converter<IN, OUT> 
    extends org.springframework.core.convert.converter.Converter<IN, OUT> {
    /**
     * Converts the input class to the output class.
     * 
     * @param in the data to convert
     * @return the converted data
     */
    OUT convert(IN in);

    /**
     * Converts a list of elements.
     * @param values the source list
     * @return result, never null but maybe empty.
     */
    default List<OUT> convert(Collection<IN> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptyList();
        }
        return values.stream().map(this::convert).collect(Collectors.toList());
    }
}