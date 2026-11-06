package com.empresa.recibo.client;

import com.empresa.recibo.exception.ResourceNotFoundException;
import com.empresa.recibo.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

/** Verifica que el cliente y el servicio existan consultando a los otros microservicios. */
@Component
public class ReferenciaClient {

    private final RestClient clienteApi;
    private final RestClient servicioApi;
    private final boolean validar;

    public ReferenciaClient(@Value("${servicios.cliente.url}") String clienteUrl,
                            @Value("${servicios.servicio.url}") String servicioUrl,
                            @Value("${servicios.validar-referencias:true}") boolean validar) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(3000);
        this.clienteApi = RestClient.builder().requestFactory(factory).baseUrl(clienteUrl).build();
        this.servicioApi = RestClient.builder().requestFactory(factory).baseUrl(servicioUrl).build();
        this.validar = validar;
    }

    public void validarCliente(Long idCliente) {
        verificar(clienteApi, "/api/clientes/" + idCliente, "Cliente", idCliente);
    }

    public void validarServicio(Long idServicio) {
        verificar(servicioApi, "/api/servicios/" + idServicio, "Servicio", idServicio);
    }

    private void verificar(RestClient api, String path, String nombre, Long id) {
        if (!validar) {
            return;
        }
        try {
            api.get().uri(path).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException(nombre + " con id " + id + " no existe");
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException(
                    "No se pudo contactar al microservicio de " + nombre.toLowerCase() + ". Verifique que este levantado.");
        }
    }
}
