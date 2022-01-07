package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.Marca;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Marca.class)
public interface MarcaRepository extends EntityRepository<Marca, Long>{

	@Query("SELECT u FROM Marca u WHERE u.estado = 1")
	List<Marca> listar();
}
