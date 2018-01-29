package org.sterl.cloud.admin.impl.dbdriver.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class DbMetaDataBE {
    @Getter @RequiredArgsConstructor
    public static class DbSchemaBE {
        private final String name;
        private List<String> tables = new ArrayList<>();
        private List<String> views = new ArrayList<>();
        private Map<String, List<String>> others = new LinkedHashMap<>();
        
        public void addTable(String name) {
            this.tables.add(name);
        }
        public void addView(String name) {
            this.views.add(name);
        }
        public void addOther(String name, String type) {
            final List<String> values = others.getOrDefault(type, new ArrayList<>());
            values.add(name);
        }
    }
    public static final String DEFAULT_SCHEMA_NAME = "Default";
    private Map<String, DbSchemaBE> schemas = new LinkedHashMap<>();
    
    public DbMetaDataBE addTable(String schemaName, String tableName) {
        DbSchemaBE schema = schemas.getOrDefault(schemaName, new DbSchemaBE(schemaName));
        schema.addTable(tableName);
        return this;
    }
    public DbMetaDataBE addView(String schemaName, String viewName) {
        DbSchemaBE schema = schemas.getOrDefault(schemaName, new DbSchemaBE(schemaName));
        schema.addView(viewName);
        return this;
    }
    /**
     * Adds any other object which type is DB specific or just not supported here.
     * 
     * @param schemaName the schema this object belongs too
     * @param otherName the name of it
     * @param otherType the type to group it
     * @return
     */
    public DbMetaDataBE addOther(String schemaName, String otherName, String otherType) {
        DbSchemaBE schema = schemas.getOrDefault(schemaName, new DbSchemaBE(schemaName));
        schema.addOther(otherName, otherType);
        return this;
    }
}