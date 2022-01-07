package com.farmaciasperuanas.inspector.app.repository;

import com.farmaciasperuanas.inspector.app.model.ConfiguracionFotos;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ConfiguracionFotos.class)
public interface ConfiguracionFotosRepository extends EntityRepository<ConfiguracionFotos, Integer> {

    @Query("SELECT u FROM ConfiguracionFotos u ORDER BY u.orden ASC")
    List<ConfiguracionFotos> listar();
}
