package org.sterl.cloudadmin.impl.common.id;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdTypeDescriptor;

/**
 * Converts the {@link Id} using its given JPA {@link AbstractIdTypeDescriptor} from
 * any supported type to the destination type for Spring.
 * 
 * Supports also toString representations like SimpleClassName(value)
 * 
 * @param <IdType>
 * @param <ValueType>
 */
public class GenericIdConverter<IdType extends Id<ValueType>, ValueType> implements GenericConverter {
    
    private static final Map<Class<?>, TypeDescriptor> typeDescriptorCache = new HashMap<>(32);
    private static final TypeDescriptor STRING_TYPE;
    
    private final AbstractIdTypeDescriptor<ValueType, IdType> type;
    private final Set<ConvertiblePair> converting;
    private final TypeDescriptor valueType;
    private final TypeDescriptor idType;
    
    private final String toStringPrefix;
    
    static {
        STRING_TYPE = getTypeDescriptor(String.class);
    }
    
    static TypeDescriptor getTypeDescriptor(Class<?> type) {
        Objects.requireNonNull(type, "Class type has to be set but is null.");
        TypeDescriptor result = typeDescriptorCache.get(type);
        if (result == null) {
            result = TypeDescriptor.valueOf(type);
            typeDescriptorCache.put(type, result);
        }
        return result;
    }
    
    public GenericIdConverter(AbstractIdTypeDescriptor<ValueType, IdType> type) {
        this.type = type;
        this.valueType = getTypeDescriptor(type.getValueClass());
        this.idType = getTypeDescriptor(type.getJavaType());
        this.toStringPrefix = type.getJavaType().getSimpleName() + "(";
        this.converting = Set.of(
            new ConvertiblePair(type.getValueClass(), type.getJavaType()),
            new ConvertiblePair(type.getJavaType(), type.getValueClass()),
            
            new ConvertiblePair(String.class, type.getJavaType()),
            new ConvertiblePair(type.getJavaType(), String.class)
        );
    }
    
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return converting;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Object result = null;
        if (value == null) {
            result = null;
        } else if (targetType.isAssignableTo(valueType) && sourceType.isAssignableTo(idType)) {
            result = ((IdType)value).getValue();
            
        } else if (sourceType.isAssignableTo(idType)) {
            result = type.unwrapUnchecked((IdType)value, targetType.getType());
        } else if (sourceType.isAssignableTo(STRING_TYPE)) {
            String v = (String)value;
            // Support also "toString" representations like Identity(1)
            if (v.length() > toStringPrefix.length() && v.startsWith(toStringPrefix)) {
                v = v.substring(toStringPrefix.length(), v.length() - 1);
            }
            result = type.wrap(v, null);
        } else if (sourceType.isAssignableTo(valueType)) {
            result = type.wrap(value, null);
        } else if (sourceType.isAssignableTo(targetType)) { // NOOP
            result = value;
        } else {
            throw new IllegalArgumentException("Cannot convert " + value + " from type " + sourceType + " to type " + targetType);
        }
        return result;
    }
}
