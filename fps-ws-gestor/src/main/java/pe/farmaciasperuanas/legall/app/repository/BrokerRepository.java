package pe.farmaciasperuanas.legall.app.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.BrokerModel;

@Repository(forEntity = BrokerModel.class)
public interface BrokerRepository extends EntityRepository<BrokerModel, Long>{

    Optional<BrokerModel> findByRazonSocial(String razonSocial);
}
