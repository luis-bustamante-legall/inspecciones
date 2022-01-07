package pe.farmaciasperuanas.legall.app.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import pe.farmaciasperuanas.legall.app.model.UsuarioModel;

@Repository(forEntity = UsuarioModel.class)
public interface UsuarioRepository extends EntityRepository<UsuarioModel, String>{

    Optional<UsuarioModel> findByUsernameAndPasswordEncrypt(String username, String password);
}
