package pe.farmaciasperuanas.legall.app.repository;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.MarcaModel;

@Repository(forEntity = MarcaModel.class)
public interface MarcaRepository extends EntityRepository<MarcaModel, Long>{

	List<MarcaModel> findByNombre(String nombre);
}
