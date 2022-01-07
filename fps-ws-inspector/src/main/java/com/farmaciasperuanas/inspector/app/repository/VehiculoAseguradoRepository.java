package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.VehiculoModel;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository(forEntity = VehiculoModel.class)
public interface VehiculoAseguradoRepository extends EntityRepository<VehiculoModel,Long>{

    Optional<VehiculoModel> findByPlaca(String placa);

}
