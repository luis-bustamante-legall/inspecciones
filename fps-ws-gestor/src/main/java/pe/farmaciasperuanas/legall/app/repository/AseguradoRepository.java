package pe.farmaciasperuanas.legall.app.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.AseguradoModel;

@Repository(forEntity = AseguradoModel.class)
public interface AseguradoRepository extends EntityRepository<AseguradoModel, Long> {

	Optional<AseguradoModel> findByNombreAndApellidoPaternoAndApellidoMaterno(String nombre,
			String apellidoPaterno, String apellidoMaterno);

}
