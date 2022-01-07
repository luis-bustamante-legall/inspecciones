package pe.farmaciasperuanas.legall.app.service;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

public class BaseService {
  
    @Inject
    protected Logger logger;

    @Inject
    protected ModelMapper modelMapper;
}
