package pe.farmaciasperuanas.legall.app.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import pe.farmaciasperuanas.legall.app.model.EmpleadoModel;

@Repository(forEntity = EmpleadoModel.class)
public interface EmpleadoRepository extends EntityRepository<EmpleadoModel, Long> {

    EmpleadoModel findByUsername(String username);

}
