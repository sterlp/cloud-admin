package org.sterl.cloudadmin.impl.common.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

public interface CloudConverter<Source, Target> extends Converter<Source, Target> {

    default List<Target> convert(Collection<Source> s) {
        if (s == null) return Collections.emptyList();
        return s.stream().map(this::convert).collect(Collectors.toList());
    }
}
