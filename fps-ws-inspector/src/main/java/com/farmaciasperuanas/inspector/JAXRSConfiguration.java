package com.farmaciasperuanas.inspector;

import com.farmaciasperuanas.inspector.util.Constantes;
import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({Constantes.ROL_SUPER_USUARIO, Constantes.ROL_INSPECTOR})
public class JAXRSConfiguration extends Application {

}
