package org.sterl.cloud.admin.impl.dbquery.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class QueryCommandBE {
    public enum CommitType {
        COMMIT,
        ROLLBACK
    }
    private CommitType commitType;
    private int fetchCount = 50;
    @NotNull
    private String query;
}
