package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class MyGenerator implements IdentifierGenerator {

    public static final String generatorName = "id_generator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) throws HibernateException {
        return new Random().nextInt();
    }
}
