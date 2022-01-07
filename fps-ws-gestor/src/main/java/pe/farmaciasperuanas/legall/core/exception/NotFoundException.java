package pe.farmaciasperuanas.legall.core.exception;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Getter
@Setter
public class NotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NotFoundException(String message) {
		super(Response.status(Response.Status.NOT_FOUND).entity(message).
				type(MediaType.APPLICATION_JSON_TYPE).build());
		this.message = message;
	}

	public NotFoundException() {
	}
}
