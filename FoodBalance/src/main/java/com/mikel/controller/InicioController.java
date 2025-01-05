package com.mikel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {
	
	// Controlador de la solicitud inicial
	@RequestMapping("/")
	public String paginaLogin(Model model, HttpSession session) {
		
		// Creacion de la sesion con contador de numero de intentos antes del bloqueo de la sesion
		session.setAttribute("numero_de_intentos", 3);
		
		return "login";
	}
	
}
