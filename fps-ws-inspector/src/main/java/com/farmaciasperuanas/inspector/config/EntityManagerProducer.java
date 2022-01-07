package com.farmaciasperuanas.inspector.config;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "legall_pu")
    private EntityManager em;
    
    @ApplicationScoped
    @Produces
    public EntityManager getEntityManager() {
        return em;
    }
    
    @ApplicationScoped
    @Produces
    public ModelMapper modelMapper() {
    	return new ModelMapper();
    }

}
