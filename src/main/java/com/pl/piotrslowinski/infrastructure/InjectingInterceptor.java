package com.pl.piotrslowinski.infrastructure;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by user on 07.01.2018.
 */
@Component
public class InjectingInterceptor extends EmptyInterceptor {

    @Autowired
    private AutowireCapableBeanFactory factory;

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        factory.autowireBean(entity);
        return super.onLoad(entity, id, state, propertyNames, types);
    }
}

