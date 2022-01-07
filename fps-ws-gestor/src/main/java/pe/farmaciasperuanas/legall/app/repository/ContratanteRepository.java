package pe.farmaciasperuanas.legall.app.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.ContratanteModel;

@Repository(forEntity = ContratanteModel.class)
public interface ContratanteRepository extends EntityRepository<ContratanteModel, Long>{

    Optional<ContratanteModel> findByNumeroRuc(String ruc);
}
