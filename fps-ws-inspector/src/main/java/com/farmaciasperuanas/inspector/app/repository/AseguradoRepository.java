package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.AseguradoModel;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository(forEntity = AseguradoModel.class)
public interface AseguradoRepository extends EntityRepository<AseguradoModel, Long> {

	Optional<AseguradoModel> findByNombreAndApellidoPaternoAndApellidoMaterno(String nombre,
			String apellidoPaterno, String apellidoMaterno);

}
