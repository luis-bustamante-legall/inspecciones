package com.farmaciasperuanas.inspector.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class CriteriaBuilderFactoryProducer {

	@PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private CriteriaBuilderFactory criteriaBuilderFactory;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
    	
    }

    @PostConstruct
    public void createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        this.criteriaBuilderFactory = config.createCriteriaBuilderFactory(entityManagerFactory);
    }

    @Produces
    @ApplicationScoped
    public CriteriaBuilderFactory createCriteriaBuilderFactory1() {
        return criteriaBuilderFactory;
    }
}
