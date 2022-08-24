package com.rah.operaciones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OperacionController {

	@Autowired
	private RestTemplate clienteRest;

	@GetMapping("/sumar")
	public String sumar(@RequestParam float a, @RequestParam float b) {
		String resultado = Float.toString(a + b);
		return clienteRest.getForObject("http://localhost:9001/resultado/" + resultado, String.class);
	}

	@GetMapping("/restar")
	public ResponseEntity<String> main(@RequestParam float a, @RequestParam float b) {

		String resultado = Float.toString(a - b);

		ResponseEntity<String> responseEntity = clienteRest.getForEntity("http://localhost:9001/resultado/" + resultado,
				String.class);
		return responseEntity;

	}
}
