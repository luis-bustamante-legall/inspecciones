package pe.farmaciasperuanas.legall.app.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.VehiculoModel;

@Repository(forEntity = VehiculoModel.class)
public interface VehiculoAseguradoRepository extends EntityRepository<VehiculoModel,Long>{

    Optional<VehiculoModel> findByPlaca(String placa);

}
