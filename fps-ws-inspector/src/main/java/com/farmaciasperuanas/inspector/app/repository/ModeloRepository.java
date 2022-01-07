package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.Modelo;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Modelo.class)
public interface ModeloRepository extends EntityRepository<Modelo,Long> {

	@Query("SELECT u FROM Modelo u WHERE u.estado = 1")
	List<Modelo> listar();

	List<Modelo>findByIdMarca(Integer idMarca);
}
