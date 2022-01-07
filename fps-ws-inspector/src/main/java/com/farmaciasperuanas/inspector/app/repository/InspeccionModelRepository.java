package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.InspeccionModel;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InspeccionModel.class)
public interface InspeccionModelRepository extends EntityRepository<InspeccionModel, Long> {

}
