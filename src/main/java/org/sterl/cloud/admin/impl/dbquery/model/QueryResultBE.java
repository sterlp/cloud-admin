package org.sterl.cloud.admin.impl.dbquery.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class QueryResultBE {
    @Data
    public class ColumnHeaderBE {
        private final String name;
        private final String typeName;
    }

    private String query;
    private long queryTime;
    private List<ColumnHeaderBE> headers = new ArrayList<>();
    private List<List<Object>> data = new ArrayList<>();

    public QueryResultBE(String query) {
        this.query = query;
    }
    public void addHeader(String columnName, String typeName) {
        headers.add(new ColumnHeaderBE(columnName, typeName));
    }
    public void addRow(List<Object> row) {
        data.add(row);
    }
}
