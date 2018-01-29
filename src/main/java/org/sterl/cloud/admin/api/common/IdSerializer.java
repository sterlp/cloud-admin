package org.sterl.cloud.admin.api.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class IdSerializer extends JsonSerializer<Id<?>> {

    @Override
    public void serialize(Id<?> value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if (value == null || value.getValue() == null) {
            gen.writeNull();
        } else {
            Object v = value.getValue();
            if (v instanceof Long) {
                gen.writeNumber((Long)v);
            } else if (v instanceof Integer) {
                gen.writeNumber((Integer)v);
            } else {
                gen.writeString(String.valueOf(v));
            }
        }
    }
}