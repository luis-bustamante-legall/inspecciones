package pe.farmaciasperuanas.legall.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@JsonbPropertyOrder(value = {"fecha","estado","mensajeSistema","mensajeTecnico", "detalle","response"})
public class BaseResponse<T> {

	private String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
	private String estado;
	private String mensajeSistema;
	private String mensajeTecnico;
	List<BaseErrorResponse> detalle;
	private T response;

	public BaseResponse(String estado, String mensaje) {
		this.estado = estado;
		this.mensajeSistema = mensaje;
	}
	
	public BaseResponse(String estado, String mensaje, String mensajeTecnico) {
		this.estado = estado;
		this.mensajeSistema = mensaje;
		this.mensajeTecnico = mensajeTecnico;
	}

	public BaseResponse(String estado, String mensaje, List<BaseErrorResponse>s) {
		this.estado = estado;
		this.mensajeSistema = mensaje;
		this.detalle = s;
	}
}

