package com.farmaciasperuanas.inspector.app.repository;

import com.blazebit.persistence.deltaspike.data.FullEntityViewRepository;
import com.blazebit.persistence.deltaspike.data.Specification;
import com.farmaciasperuanas.inspector.app.model.InspeccionView;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InspeccionView.class)
public interface InspeccionViewRepository extends EntityRepository<InspeccionView,Long>,
        FullEntityViewRepository<InspeccionView, InspeccionView, Long>, Specification<InspeccionView> {

}