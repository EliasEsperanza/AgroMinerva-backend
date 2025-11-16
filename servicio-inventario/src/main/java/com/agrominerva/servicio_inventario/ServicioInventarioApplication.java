package com.agrominerva.servicio_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class ServicioInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioInventarioApplication.class, args);
	}

}
