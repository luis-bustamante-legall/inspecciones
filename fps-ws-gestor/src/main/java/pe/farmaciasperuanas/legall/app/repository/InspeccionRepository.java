package pe.farmaciasperuanas.legall.app.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.InspeccionModel;

@Repository(forEntity = InspeccionModel.class)
public interface InspeccionRepository extends EntityRepository<InspeccionModel, Long>{

}
