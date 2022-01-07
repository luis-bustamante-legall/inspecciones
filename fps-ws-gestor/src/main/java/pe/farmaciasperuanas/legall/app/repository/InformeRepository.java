package pe.farmaciasperuanas.legall.app.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import pe.farmaciasperuanas.legall.app.model.InformeModel;
import pe.farmaciasperuanas.legall.app.model.UsuarioModel;

@Repository(forEntity = InformeModel.class)
public interface InformeRepository extends EntityRepository<InformeModel, Long> {
}
