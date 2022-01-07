package pe.farmaciasperuanas.legall.app.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import pe.farmaciasperuanas.legall.app.model.ModeloModel;

import java.util.List;
import java.util.Optional;

@Repository(forEntity = ModeloModel.class)
public interface ModeloRepository extends EntityRepository<ModeloModel, Long>{

	List<ModeloModel> findByNombre(String nombre);
	
}
