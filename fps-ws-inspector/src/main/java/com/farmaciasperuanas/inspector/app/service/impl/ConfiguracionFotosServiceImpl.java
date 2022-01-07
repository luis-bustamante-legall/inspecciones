package com.farmaciasperuanas.inspector.app.service.impl;

import com.farmaciasperuanas.inspector.app.model.ConfiguracionFotos;
import com.farmaciasperuanas.inspector.app.repository.ConfiguracionFotosRepository;
import com.farmaciasperuanas.inspector.app.service.ConfiguracionFotosService;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class ConfiguracionFotosServiceImpl implements ConfiguracionFotosService {

    @Inject
    Logger logger;

    @Inject
    private ConfiguracionFotosRepository documentoAdjuntoRepository;

    @Override
    public List<ConfiguracionFotos> lista() {
        logger.info("[DOCUMENTO ADJUNTO]: Iniciando lista...");
        return documentoAdjuntoRepository.listar();
    }
}
