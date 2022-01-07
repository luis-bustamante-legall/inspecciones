package pe.farmaciasperuanas.legall.app.controller;

import pe.farmaciasperuanas.legall.app.model.ContratanteModel;
import pe.farmaciasperuanas.legall.app.model.ModeloModel;
import pe.farmaciasperuanas.legall.app.repository.ModeloRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/modelo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloController {

    @Inject
    ModeloRepository modeloRepository;

    @GET
    public List<ModeloModel> lista(){
        return modeloRepository.findAll();
    }
}
