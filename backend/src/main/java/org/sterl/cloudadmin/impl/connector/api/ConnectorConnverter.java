package org.sterl.cloudadmin.impl.connector.api;

import org.springframework.core.convert.converter.Converter;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;

class ConnectorConnverter {

    enum ToSupportedConnector implements Converter<ConnectorProvider<?>, SupportedConnector> {
        INSTANCE;

        @Override
        public SupportedConnector convert(ConnectorProvider<?> source) {
            if (source == null) return null;
            return SupportedConnector.builder()
                    .id(source.getClassName().getName())
                    .name(source.getName())
                    .build()
                    .add(source.getConfigMeta());
        }
    }
}
