package org.sterl.cloudadmin.impl.system.model.jpa;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.sterl.cloudadmin.api.system.SystemId;

/**
 * https://thoughts-on-java.org/custom-sequence-based-idgenerator/ 
 */
public class SystemIdSequenceGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return new SystemId((Long)super.generate(session, object));
    }
 
    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
    }
}