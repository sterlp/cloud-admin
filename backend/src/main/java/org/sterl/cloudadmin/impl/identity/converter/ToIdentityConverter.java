package org.sterl.cloudadmin.impl.identity.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.sterl.cloudadmin.api.identity.Identity;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;

@Component
public class ToIdentityConverter implements Converter<IdentityBE, Identity> {

    @Override
    public Identity convert(IdentityBE source) {
        if (source == null) return null;
        var result = new Identity();
        result.setId(source.getStrongId());
        result.setEmail(source.getEmail());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setName(source.getName());
        return result;
    }
}
