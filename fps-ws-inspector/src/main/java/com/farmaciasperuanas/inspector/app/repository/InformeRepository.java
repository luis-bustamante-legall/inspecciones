package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.InformeModel;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InformeModel.class)
public interface InformeRepository extends EntityRepository<InformeModel, Long> {
}
