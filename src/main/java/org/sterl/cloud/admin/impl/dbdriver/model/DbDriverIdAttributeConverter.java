package org.sterl.cloud.admin.impl.dbdriver.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.sterl.cloud.admin.api.dbdriver.DbDriverId;

@Converter(autoApply = true)
public class DbDriverIdAttributeConverter implements AttributeConverter<DbDriverId, String> {
    @Override
    public String convertToDatabaseColumn(DbDriverId attribute) {
        if (attribute == null) return null;
        else return attribute.getValue();
    }
    @Override
    public DbDriverId convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        else return new DbDriverId(dbData);
    }
}