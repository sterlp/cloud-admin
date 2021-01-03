package org.sterl.cloudadmin.impl.common.id;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.system.converter.SystemIdConverter;

@Execution(ExecutionMode.CONCURRENT) // https://www.swtestacademy.com/junit5-parallel-test-execution/
class AbstractAttributeConverter {
    
    final SystemIdConverter subject = SystemIdConverter.INSTANCE;
    final static SystemId ONE = new SystemId(1L);
    
    private static Stream<Arguments> convertProvider() {
        return Stream.of(
            Arguments.of(null, null),
            Arguments.of(1L,  ONE),
            Arguments.of("1", ONE)
        );
    }
    
    @MethodSource("convertProvider")
    @ParameterizedTest
    void testConvert(Long source, Object expected) {
        
        assertThat(subject.convert(source))
                .isEqualTo(expected);
    }
    
    private static Stream<Arguments> convertToDatabaseColumn() {
        return Stream.of(
            Arguments.of(null, null),
            Arguments.of(ONE, Long.valueOf(1L))
        );
    }
    
    @MethodSource("convertToDatabaseColumn")
    @ParameterizedTest
    void testConvertToDatabaseColumn(SystemId source, Long expected) {
        
        assertThat(subject.convertToDatabaseColumn(source))
                .isEqualTo(expected);
    }
}

