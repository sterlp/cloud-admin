package org.sterl.cloudadmin.impl.common.id;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.convert.TypeDescriptor;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.identity.model.jpa.HibernateIdentityConverters.IdentityIdType;

@Execution(ExecutionMode.CONCURRENT) // https://www.swtestacademy.com/junit5-parallel-test-execution/
class GenericIdConverterTest {
    
    final GenericIdConverter<IdentityId, Long> subject = new GenericIdConverter<>(IdentityIdType.INSTANCE);
    final static IdentityId ONE = new IdentityId(1L);
    
    /**
     *  source - sourceType - target - targetType
     */
    private static Stream<Arguments> sumProvider() {
        return Stream.of(
            Arguments.of(null, null, null, null),
            Arguments.of(1L,  Long.class, ONE, IdentityId.class),
            Arguments.of("1",  String.class, ONE, IdentityId.class),
            Arguments.of("IdentityId(1)",  String.class, ONE, IdentityId.class),
            
            Arguments.of(null, IdentityId.class, null, String.class),
            Arguments.of(ONE, IdentityId.class, 1L, Long.class),
            Arguments.of(ONE, IdentityId.class, "1", String.class),
            
            Arguments.of(ONE, IdentityId.class, ONE, IdentityId.class)
        );
    }
    
    @MethodSource("sumProvider")
    @ParameterizedTest
    void testGenericIdConverter(Object source, Class<?> sourceType, Object expected, Class<?> targetType) {
        
        assertThat(subject
                .convert(source, TypeDescriptor.valueOf(sourceType), TypeDescriptor.valueOf(targetType)))
                .isEqualTo(expected);
    }
}

