package org.sterl.cloud.admin.impl.common.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.sterl.cloud.admin.api.common.Id;

public class IdConverterFactory implements ConverterFactory<Object, Id<?>> {

    @Override
    public <T extends Id<?>> Converter<Object, T> getConverter(Class<T> targetType) {
        return new IdConverter<>(targetType);
    }
    
    static class IdConverter<T extends Id<?>> implements Converter<Object, T> {
        private final Class<T> targetType;
        private final Class<?> valueClass;
        private final Constructor<T> constructor;

        public IdConverter(Class<T> targetType) {
            super();
            this.targetType = targetType;
            valueClass = (Class<?>)((ParameterizedType)targetType.getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                constructor = targetType.getConstructor(valueClass);
            } catch (Exception e) {
                throw new RuntimeException("No constructor found for value class " + valueClass + " of ID " + targetType.getSimpleName());
            }
        }

        @Override
        public T convert(Object in) {
            if (in == null) return null;
            try {
                T result;
                if (valueClass.isAssignableFrom(in.getClass())) {
                    result = constructor.newInstance(in);
                } else {
                    String val = String.valueOf(in);
                    if (valueClass.isAssignableFrom(Long.class)) {
                        result = constructor.newInstance(Long.valueOf(val));
                    } else {
                        result = constructor.newInstance(String.valueOf(in));
                    }
                }
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create ID from value " + in.getClass().getSimpleName() 
                        + " (" + in
                        + ") for type " + targetType.getSimpleName(), e);
            }
        }
    }
}
