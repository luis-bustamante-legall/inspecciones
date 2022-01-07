package pe.farmaciasperuanas.legall.app.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.TramiteModel;

import java.util.Optional;

@Repository(forEntity = TramiteModel.class)
public interface TramiteRepository extends EntityRepository<TramiteModel, Long>{

    Optional<TramiteModel> findByNumeroTramiteSeguro(String numero);
}
