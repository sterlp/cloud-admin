package org.sterl.cloudadmin.impl.connector.api;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.sterl.cloudadmin.api.connector.ConfigMetaData;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @Builder @EqualsAndHashCode(of = "id", callSuper = false)
public class SupportedConnector {
    private final String id;
    private final String name;
    private final Set<ConfigMetaData> configMetaData = new LinkedHashSet<>();
    
    public SupportedConnector add(ConfigMetaData c) {
        this.configMetaData.add(c);
        return this;
    }

    public SupportedConnector add(List<ConfigMetaData> newConfig) {
        configMetaData.addAll(newConfig);
        return this;
    }
}
