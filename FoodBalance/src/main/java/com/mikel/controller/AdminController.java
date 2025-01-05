package com.mikel.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mikel.model.Categoria;
import com.mikel.model.Comida;
import com.mikel.model.ComidaDiario;
import com.mikel.model.ListaComida;
import com.mikel.model.MacrosRecomendados;
import com.mikel.model.Peso;
import com.mikel.model.TotalDiario;
import com.mikel.model.TotalMacro;
import com.mikel.model.Usuario;
import com.mikel.repository.CategoriaRepository;
import com.mikel.repository.ComidaDiarioRepository;
import com.mikel.repository.ComidaRepository;
import com.mikel.repository.ListaComidaRepository;
import com.mikel.repository.MacrosRecomendadosRepository;
import com.mikel.repository.PesoRepository;
import com.mikel.repository.TotalDiarioRepository;
import com.mikel.repository.TotalMacroRepository;
import com.mikel.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	// Las distintas importaciones de los repository
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private ComidaRepository comidaRepo;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ListaComidaRepository listaComidaRepo;
	
	@Autowired
	private TotalMacroRepository totalMacroRepo;
	
	@Autowired
	private TotalDiarioRepository totalDiarioRepo;
	
	@Autowired
	private ComidaDiarioRepository comidaDiarioRepo;
	
	@Autowired
	private PesoRepository pesoRepo;
	
	@Autowired
	private MacrosRecomendadosRepository macrosRecomendadosRepo;
	
	// Controlador de inicio para la pagina principal
	@RequestMapping("/inicio")
	public String paginaPrincipal(Model model, HttpSession session) {
		
		// Validacion de la sesion del usuario
	    if (session.getAttribute("usuario") != null) {
	    	
	    	// Carga de datos en el modelo 
			model.addAttribute("atr_lista_comida", comidaRepo.findAll());
			
			model.addAttribute("atr_lista_categoria", categoriaRepo.findAll());
			
			List<ListaComida> listaComidaTexto = listaComidaRepo.findAll();
			
			List<ListaComida> listaComidaSumatorio = listaComidaRepo.findAll();
			// Concatenacion de los datos de las comidas en un texto con un formato dado
		    String datosConcatenados = listaComidaTexto.stream()
		            .map(comida -> String.format("%s - Calorías: %.2fg- Proteína: %.2fg- Carbohidratos: %.2fg- Grasa: %.2fg",
		            		comida.getComida().getNombre(), comida.getCalorias(), comida.getProteina(), 
		                    comida.getCarbohidratos(), comida.getGrasa()))
		            .collect(Collectors.joining("\n"));
		    
		    model.addAttribute("atr_lista_listaComida", datosConcatenados);
		    
		    // Calculo de los distintos sumatorios de propiedades
		    double totalCalorias = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCalorias)
		            .sum();

		    double totalProteina = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getProteina)
		            .sum();

		    double totalCarbohidratos = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCarbohidratos)
		            .sum();

		    double totalGrasa = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getGrasa)
		            .sum();
		    
		    // Carga de los sumatorios al modelo
		    model.addAttribute("totalCalorias", totalCalorias);
		    model.addAttribute("totalProteina", totalProteina);
		    model.addAttribute("totalCarbohidratos", totalCarbohidratos);
		    model.addAttribute("totalGrasa", totalGrasa);
			
			return "index";
			
	    } else {
	        
	    	// Redireccion al controlador login por si el usuario esta expirado
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para el filtrado de datos en la pagina principal
	@RequestMapping("/filtro")
	public String filtroPaginaPrincipal(Model model, HttpSession session , @RequestParam("categoriaid") int categoriaid) {
		
	    if (session.getAttribute("usuario") != null) {

	    	// Cambio en la recogida de datos tomando en cuenta el parametro de categoria que se recoge
			model.addAttribute("atr_lista_comida", comidaRepo.findByCategoria(categoriaid));
			
			model.addAttribute("atr_lista_categoria", categoriaRepo.findAll());
			
			List<ListaComida> listaComidaTexto = listaComidaRepo.findAll();
			
			List<ListaComida> listaComidaSumatorio = listaComidaRepo.findAll();
			
		    String datosConcatenados = listaComidaTexto.stream()
		            .map(comida -> String.format("%s - Calorías: %.2fg- Proteína: %.2fg- Carbohidratos: %.2fg- Grasa: %.2fg",
		            		comida.getComida().getNombre(), comida.getCalorias(), comida.getProteina(), 
		                    comida.getCarbohidratos(), comida.getGrasa()))
		            .collect(Collectors.joining("\n"));
		    
		    model.addAttribute("atr_lista_listaComida", datosConcatenados);
		    
		    double totalCalorias = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCalorias)
		            .sum();

		    double totalProteina = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getProteina)
		            .sum();

		    double totalCarbohidratos = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCarbohidratos)
		            .sum();

		    double totalGrasa = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getGrasa)
		            .sum();
		    
		    model.addAttribute("totalCalorias", totalCalorias);
		    model.addAttribute("totalProteina", totalProteina);
		    model.addAttribute("totalCarbohidratos", totalCarbohidratos);
		    model.addAttribute("totalGrasa", totalGrasa);
			
			return "index";
	        
	    } else {
	    	
	        return "redirect:/login";
	        
	    }
		

	}
	
	// Controlador del login
	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		
		// Recogemos los datos de los intentos de inicio de sesion por si esta bloqueada la sesion
		Integer numeroDeIntentos = (Integer) session.getAttribute("numero_de_intentos");
		
		session.removeAttribute("numero_de_intentos");
		
		session.setAttribute("numero_de_intentos", numeroDeIntentos);
		
		model.addAttribute("mensaje_de_error", "Nombre de usuario o contraseña incorrecta. <br> Numero de intentos antes del bloqueo: " + numeroDeIntentos);
		
		if (numeroDeIntentos == 0) {
			return "bloqueo";
		} else {
			return "login";
		}
		
	}
	
	// Controlador para iniciar la sesion
	@RequestMapping("/iniciarSesion")
	public String iniciarSesion(Model model, HttpSession session, @RequestParam("usuario") String usuario, @RequestParam("contraseña") String contraseña) throws NoSuchAlgorithmException {
		
		// Se recogen los datos del usuario que intenta iniciar sesion
		List<Usuario> usuarioLocalizado = usuarioRepo.findByNombre(usuario);
		
		Integer numeroDeIntentos = (Integer) session.getAttribute("numero_de_intentos");
		
		// Se hashea la contraseña introducida para compararlo con la guardada
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(contraseña.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        
        String contraHasheada = sb.toString();
		
        // Se valida si es correcta o no
		if (usuarioLocalizado.get(0).getContraseña().equals(contraHasheada)) {
			
			session.removeAttribute("numero_de_intentos");
			
			session.setAttribute("numero_de_intentos", 3);
			
			session.setAttribute("usuario", usuarioLocalizado.get(0).getId());
			
			return "redirect:/inicio";
		} else {
			
			numeroDeIntentos = numeroDeIntentos - 1;
			
			session.removeAttribute("numero_de_intentos");
			
			session.setAttribute("numero_de_intentos",numeroDeIntentos);
			
			return "redirect:/login";
		}
		
	}
	
	// Controlador para ir a la pagina de peso y cargar los datos necesarios en este
	@RequestMapping("/peso")
	public String pesoPagina(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			model.addAttribute("atr_lista_macrosRecomendados", macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()));
			
			return "peso";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para ir a la pagina de comida historial y cargar los datos necesarios
	@RequestMapping("/comidaHistorial")
	public String comidaHistorial(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			// Recogemos la fecha actual para el calendario
			LocalDate fechaActual = LocalDate.now();
			
			model.addAttribute("fecha", fechaActual);
			
			model.addAttribute("atr_lista_macrosRecomendados", macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()));
			
			// Recogemos los datos de los macros del usuario seleccionado
			List<Double> datosMacros = new ArrayList<>();
			
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getCarbohidratos());
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getGrasa());
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getProteina());
			
			model.addAttribute("atr_lista_datosMacro", datosMacros);
			
			// De inicio recogemos los datos de las comidas de hoy
			TotalDiario registroHoy = totalDiarioRepo.findByFechaActual();
			
			// Por si hoy no hay ningun dato registrado hacemos una validacion
			if (registroHoy == null) {
			   
				List<Double> datosMacrosLista = new ArrayList<>();
				
				double totalProteinas = 0.0;
				double totalCarbohidratos = 0.0;
				double totalGrasas = 0.0;
				double totalCalorias = 0.0;
				
				datosMacrosLista.add(totalCarbohidratos);
				datosMacrosLista.add(totalGrasas);
				datosMacrosLista.add(totalProteinas);
				
				model.addAttribute("atr_lista_macrosDia", datosMacrosLista);
				model.addAttribute("caloriasEnseñar", 0);
				
				List<Double> datosMacrosComparacion = new ArrayList<>();
				
				// Recogemos los datos de los macros de usuario y hacemos la comparacion con los datos default de arriba
				List<MacrosRecomendados> listaMacrosRecomendadosComparacion = macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get());
				
				double comparacionProteinas = 0.0;
				double comparacionCarbohidratos = 0.0;
				double comparacionGrasas = 0.0;
				double comparacionCalorias = 0.0;
				
				comparacionProteinas = totalProteinas - listaMacrosRecomendadosComparacion.get(0).getProteina();
				comparacionCarbohidratos = totalCarbohidratos - listaMacrosRecomendadosComparacion.get(0).getCarbohidratos();
				comparacionGrasas = totalGrasas - listaMacrosRecomendadosComparacion.get(0).getGrasa();
				comparacionCalorias = totalCalorias - listaMacrosRecomendadosComparacion.get(0).getCalorias();
				
				datosMacrosComparacion.add(comparacionProteinas);
				datosMacrosComparacion.add(comparacionCarbohidratos);
				datosMacrosComparacion.add(comparacionGrasas);
				datosMacrosComparacion.add(comparacionCalorias);
				
				model.addAttribute("atr_lista_macrosComparacion", datosMacrosComparacion);
				
			} else {
				
				// En el caso que haya datos hacemos lo mismo pero con datos reales al principio y no los default
				Integer idUsuario = (Integer) session.getAttribute("usuario");
				
				Integer idTotalDiario = totalDiarioRepo.findByFechaActualAndUsuario(idUsuario).getId();
				
				List<ComidaDiario> comidasDiario = comidaDiarioRepo.findByIdTotalDiario(idTotalDiario);
				
				List<Double> datosMacrosLista = new ArrayList<>();
				
				double totalProteinas = 0.0;
				double totalCarbohidratos = 0.0;
				double totalGrasas = 0.0;
				double totalCalorias = 0.0;

				// Iteramos sobre la lista para calcular los sumatorios
				for (ComidaDiario comida : comidasDiario) {
				    totalProteinas += comida.getComida().getProteina();
				    totalCarbohidratos += comida.getComida().getCarbohidratos();
				    totalGrasas += comida.getGrasa();
				    totalCalorias += comida.getCalorias();
				}	

				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getCarbohidratos());
				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getGrasa());
				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getProteina());
				
				datosMacrosLista.add(totalCarbohidratos);
				datosMacrosLista.add(totalGrasas);
				datosMacrosLista.add(totalProteinas);
				
				model.addAttribute("atr_lista_macrosDia", datosMacrosLista);
				model.addAttribute("caloriasEnseñar", totalCalorias);
				
				//Parte de comparaciones
				
				List<Double> datosMacrosComparacion = new ArrayList<>();
				
				List<MacrosRecomendados> listaMacrosRecomendadosComparacion = macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get());
				
				double comparacionProteinas = 0.0;
				double comparacionCarbohidratos = 0.0;
				double comparacionGrasas = 0.0;
				double comparacionCalorias = 0.0;
				
				comparacionProteinas = totalProteinas - listaMacrosRecomendadosComparacion.get(0).getProteina();
				comparacionCarbohidratos = totalCarbohidratos - listaMacrosRecomendadosComparacion.get(0).getCarbohidratos();
				comparacionGrasas = totalGrasas - listaMacrosRecomendadosComparacion.get(0).getGrasa();
				comparacionCalorias = totalCalorias - listaMacrosRecomendadosComparacion.get(0).getCalorias();
				
				datosMacrosComparacion.add(comparacionProteinas);
				datosMacrosComparacion.add(comparacionCarbohidratos);
				datosMacrosComparacion.add(comparacionGrasas);
				datosMacrosComparacion.add(comparacionCalorias);
				
				model.addAttribute("atr_lista_macrosComparacion", datosMacrosComparacion);
				
			}
			
			return "comidaHistorial";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para ir a la pagina del historial del peso
	@RequestMapping("/pesoHistorial")
	public String pesoHistorial(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			LocalDate fechaActual = LocalDate.now();
			
			model.addAttribute("fecha", fechaActual);
			
			List<Peso> pesosMesActual = pesoRepo.findByUsuarioAndMonth(usuarioLocalizado.get());

			List<Double> promediosPorSemana = new ArrayList<>();

			LocalDate hoy = LocalDate.now();

			// Calcular el primer y último día del mes actual
			LocalDate primerDiaMes = hoy.withDayOfMonth(1);
			LocalDate ultimoDiaMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

			// Iterar semana por semana
			LocalDate semanaInicio = primerDiaMes;
			while (!semanaInicio.isAfter(ultimoDiaMes)) {
			    // Definir el fin de la semana
			    LocalDate semanaFin = semanaInicio.plusDays(6).isAfter(ultimoDiaMes) ? ultimoDiaMes : semanaInicio.plusDays(6);

			    final LocalDate inicio = semanaInicio;
			    final LocalDate fin = semanaFin;

			    // Filtrar los pesos de esta semana
			    List<Double> pesosSemana = pesosMesActual.stream()
			            .filter(p -> !p.getFecha().isBefore(inicio) && !p.getFecha().isAfter(fin))
			            .map(Peso::getPesoTotal)
			            .collect(Collectors.toList());

			    // Calcular el promedio y agregar 0.0 si no hay datos
			    if (!pesosSemana.isEmpty()) {
			        double promedioSemana = pesosSemana.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
			        promediosPorSemana.add(promedioSemana);
			    } else {
			        // Si no hay datos, agregamos 0.0
			        promediosPorSemana.add(0.0);
			    }

			    // Avanzamos a la próxima semana
			    semanaInicio = semanaFin.plusDays(1);
			}
			
			model.addAttribute("atr_lista_pesoHistorial", promediosPorSemana);
			
			return "pesoHistorial";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para ir al historial de peso segun el año
	@RequestMapping("/pesoHistorialAño")
	public String pesoHistorialAño(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			List<Peso> pesosAñoActual = pesoRepo.findByUsuarioAndCurrentYear(usuarioLocalizado.get());

			LocalDate fechaActual = LocalDate.now();
			
			model.addAttribute("fecha", fechaActual);
			
			List<Double> promediosPorMes = new ArrayList<>();

			LocalDate hoy = LocalDate.now();
			LocalDate primerDiaAño = hoy.withDayOfYear(1);
			LocalDate ultimoDiaAño = hoy.withDayOfYear(hoy.lengthOfYear());

			LocalDate mesInicio = primerDiaAño;
			while (!mesInicio.isAfter(ultimoDiaAño)) {

			    LocalDate mesFin = mesInicio.withDayOfMonth(mesInicio.lengthOfMonth());

			    final LocalDate inicio = mesInicio;
			    final LocalDate fin = mesFin;

			    List<Double> pesosMes = pesosAñoActual.stream()
			            .filter(p -> !p.getFecha().isBefore(inicio) && !p.getFecha().isAfter(fin))
			            .map(Peso::getPesoTotal)
			            .collect(Collectors.toList());

			    if (!pesosMes.isEmpty()) {
			        double promedioMes = pesosMes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
			        promediosPorMes.add(promedioMes);
			    } else {

			        promediosPorMes.add(0.0);
			    }

			    mesInicio = mesFin.plusDays(1);
			}
			
			model.addAttribute("atr_lista_pesoHistorial", promediosPorMes);
			
			return "pesoHistorialAño";
	    } else {
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para ir a la parte de administracion
	@RequestMapping("/admin")
	public String admin(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			// Validacion para saber si el usuario es admin o no y dar permiso a entrar
			if (usuarioLocalizado.get().getRol() == 1) {
				
				model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
				model.addAttribute("atr_lista_comida", comidaRepo.findAll());
				
				return "admin";
				
			} else {
				
				// Zona de denagacion de entrada
				return "adminDenegado";
			}
	    } else {
	    	
	        return "redirect:/login";
	    }
		

		
	}
	
	// Controlador para añadir la comida a la lista de comidas
	@RequestMapping("/añadirComidaALista")
	public String añadirComidaALista(Model model, HttpSession session, @RequestParam("idComida") int idComida, @RequestParam("pesoComida") double pesoComida) {
		
	    if (session.getAttribute("usuario") != null) {
	    	
			ListaComida listaComida = new ListaComida();
			
			// Se recoge la comida seleccionada segun su id y despues se inserta en listaComida
			Optional<Comida> listaComidaAñadir = comidaRepo.findById(idComida);
			
			double numeroPeso = pesoComida;
			
			listaComida.setComida(listaComidaAñadir.get());
			listaComida.setCalorias((numeroPeso/100)*listaComidaAñadir.get().getCalorias());
			listaComida.setCarbohidratos((numeroPeso/100)*listaComidaAñadir.get().getCarbohidratos());
			listaComida.setGrasa((numeroPeso/100)*listaComidaAñadir.get().getGrasa());
			listaComida.setProteina((numeroPeso/100)*listaComidaAñadir.get().getProteina());
			listaComida.setPeso(numeroPeso);
			
			listaComidaRepo.save(listaComida);
			
			return "redirect:/inicio";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para el filtro de las categorias en zona de administracion
	@RequestMapping("/filtroCategoriasAdmin")
	public String filtroCategoriasAdmin(Model model, HttpSession session, @RequestParam("categoriaid") int categoriaid) {
		
	    if (session.getAttribute("usuario") != null) {
			model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
			model.addAttribute("atr_lista_comida", comidaRepo.findByCategoria(categoriaid));
			
			return "admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para añadir una nueva comida recogiendo los distintos parametros necesarios para este 
	@RequestMapping("/añadirComida")
	public String añadirComida(Model model, HttpSession session, @RequestParam("nombre") String nombre, @RequestParam("calorias") double calorias, @RequestParam("proteina") double proteina, @RequestParam("carbohidratos") double carbohidratos, @RequestParam("grasa") double grasa, @RequestParam("categoria_id") int categoria_id) {
		
	    if (session.getAttribute("usuario") != null) {
	    	
			Optional<Categoria> listaCategorias = categoriaRepo.findById(categoria_id);
			
			Comida comida = new Comida();
			
			comida.setNombre(nombre);
			comida.setCalorias(calorias);
			comida.setCarbohidratos(carbohidratos);
			comida.setGrasa(grasa);
			comida.setProteina(proteina);
			comida.setCategoria(listaCategorias.get());
			
			comidaRepo.save(comida);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para añadir el usuario a la base de datos
	@RequestMapping("/añadirUsuarioFinal")
	public String añadirUsuarioFinal(Model model, HttpSession session, @RequestParam("nombre") String nombre, @RequestParam("contra") String contra) throws NoSuchAlgorithmException {
		
	    if (session.getAttribute("usuario") != null) {
	    	
	    	// Se hashea la contraseña antes de meterla a la base de datos
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = md.digest(contra.getBytes());
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashedBytes) {
	            sb.append(String.format("%02x", b));
	        }
			
	        String contraHasheada = sb.toString();
	        
			Usuario usuario = new Usuario();
			
			usuario.setNombre(nombre);
			usuario.setContraseña(contraHasheada);
			usuario.setRol(0);
			
			usuarioRepo.save(usuario);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		
	}
	
	// Controlador para modificar la comida cambiando los distintos parametros de este
	@RequestMapping("/modificarComida")
	public String modificarComida(Model model, HttpSession session, @RequestParam("comida_id") int comida_id) {
		
	    if (session.getAttribute("usuario") != null) {
			model.addAttribute("atr_lista_comida", comidaRepo.findByComidaId(comida_id));
			
			model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
			
			return "ModificarComida";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para modificar la categoria
	@RequestMapping("/modificarCategoria")
	public String modificarCategoria(Model model, HttpSession session, @RequestParam("categoria_id") int categoria_id) {
		
	    if (session.getAttribute("usuario") != null) {
			model.addAttribute("atr_lista_categorias", categoriaRepo.findByCategoriaId(categoria_id));
			
			return "ModificarCategoria";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador ir a la pagina para añadir un usuario nuevo
	@RequestMapping("/añadirUsuario")
	public String añadirUsuario(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
	    	return "AñadirUsuario";
	    } else {
	    	
	        return "redirect:/login";
	    }
		
		
	}
	
	// Controlador para modificar en la base de datos la comida seleccionada
	@RequestMapping("/modificarComidaFinal")
	public String modificarComidaFinal(Model model, HttpSession session, @RequestParam("nombre") String nombre, @RequestParam("calorias") double calorias, @RequestParam("proteina") double proteina, @RequestParam("carbohidratos") double carbohidratos, @RequestParam("grasa") double grasa, @RequestParam("comida_id") int id) {
		
	    if (session.getAttribute("usuario") != null) {
			comidaRepo.updateComida(id, nombre, calorias, proteina, grasa, carbohidratos);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para modificar la categoria en la base de datos
	@RequestMapping("/modificarCategoriaFinal")
	public String modificarCategoriaFinal(Model model, HttpSession session, @RequestParam("nombre") String nombre, @RequestParam("categoria_id") int id) {
		
	    if (session.getAttribute("usuario") != null) {
			categoriaRepo.updateCategoria(id, nombre);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para eliminar una comida
	@RequestMapping("/eliminarComida")
	public String eliminarComida(Model model, HttpSession session, @RequestParam("comida_id") int id) {
		
	    if (session.getAttribute("usuario") != null) {
			comidaRepo.eliminarComida(id);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para eliminar una categoria
	@RequestMapping("/eliminarCategoria")
	public String eliminarCategoria(Model model, HttpSession session, @RequestParam("categoria_id") int id) {
		
	    if (session.getAttribute("usuario") != null) {
			categoriaRepo.eliminarCategoria(id);
			
			return "redirect:/admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para añadir una categoria nueva
	@RequestMapping("/añadirCategoria")
	public String añadirCategoria(Model model, HttpSession session, @RequestParam("categoriaNombre") String categoriaNombre) {
		
	    if (session.getAttribute("usuario") != null) {
			Categoria categoria = new Categoria();
			
			categoria.setNombre(categoriaNombre);
			
			categoriaRepo.save(categoria);
			
			model.addAttribute("atr_lista_categorias", categoriaRepo.findAll());
			model.addAttribute("atr_lista_comida", comidaRepo.findAll());
			
			return "admin";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para completar un dia de comidas y guardarlas en la base de datos
	@RequestMapping("/completarDia")
	public String completarDia(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			TotalMacro totalmacro = new TotalMacro();
			ComidaDiario comidadiario = new ComidaDiario();
			
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			List<ListaComida> listaComidaSumatorio = listaComidaRepo.findAll();
			List<ListaComida> listaComidaFinalizar = listaComidaRepo.findAll();
			
			// Sumatorio de todos los macros de las distintas comidas para saber el resumen
		    double totalCalorias = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCalorias)
		            .sum();

		    double totalProteina = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getProteina)
		            .sum();

		    double totalCarbohidratos = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getCarbohidratos)
		            .sum();

		    double totalGrasa = listaComidaSumatorio.stream()
		            .mapToDouble(ListaComida::getGrasa)
		            .sum();
		    
		    totalmacro.setCalorias(totalCalorias);
		    totalmacro.setCarbohidratos(totalCarbohidratos);
		    totalmacro.setGrasa(totalGrasa);
		    totalmacro.setProteina(totalProteina);
		    
		    LocalDate fechaHoy = LocalDate.now();
		    
		    totalmacro.setFecha(fechaHoy);
		    totalmacro.setUsuario(usuarioLocalizado.get());
		    
		    // Guardamos los datos de los sumatorios en la base de datos
		    totalMacroRepo.save(totalmacro);
		    
		    TotalDiario totaldiario = new TotalDiario();
		    
		    totaldiario.setFecha(fechaHoy);
		    
		    totaldiario.setUsuario(usuarioLocalizado.get());
		    
		    totalDiarioRepo.save(totaldiario);
		    
		    // Se crea el registro y se selecciona para poder guardar los demas datos teniendolo en cuenta
		    TotalDiario ultimoRegistro = totalDiarioRepo.findLastRecord();
		    
		    for (ListaComida comidafinalizar : listaComidaFinalizar) {
		        ComidaDiario comidaDiario = new ComidaDiario();
		        comidaDiario.setCalorias(comidafinalizar.getCalorias());
		        comidaDiario.setCarbohidratos(comidafinalizar.getCarbohidratos());
		        comidaDiario.setGrasa(comidafinalizar.getGrasa());
		        comidaDiario.setPeso(comidafinalizar.getPeso());
		        comidaDiario.setComida(comidafinalizar.getComida());
		        comidaDiario.setTotalDiario(ultimoRegistro);

		        comidaDiarioRepo.save(comidaDiario);
		        
		        listaComidaRepo.deleteAll();
		        
		    }
			
			return "redirect:/inicio";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para guardar el peso en la base de datos
	@RequestMapping("/guardarPeso")
	public String guardarPeso(Model model, HttpSession session, @RequestParam("peso") double peso) {
		
	    if (session.getAttribute("usuario") != null) {
			Peso pesoAñadir = new Peso();
			LocalDate fechaHoy = LocalDate.now();
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			pesoAñadir.setFecha(fechaHoy);
			pesoAñadir.setUsuario(usuarioLocalizado.get());
			pesoAñadir.setPesoTotal(peso);
			
			pesoRepo.save(pesoAñadir);
			
			return "redirect:/inicio";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para calcular los macros de cada persona segun unos parametros en especifico
	@RequestMapping("/calcularMacros")
	public String guardarPeso(Model model, HttpSession session, @RequestParam("altura") double altura, @RequestParam("edad") double edad, @RequestParam("tdee") double tdee, @RequestParam("genero") int genero) {
		
	    if (session.getAttribute("usuario") != null) {
	    	
			//TMB
			// Despues de este punto se recogen los datos necesarios para las formulas y se hacen los calculos
	    	
			double tmb = 0;
			
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			Peso ultimoPeso = pesoRepo.findLastByUsuario(usuarioLocalizado.get());
			
			double pesoParaCalcular = ultimoPeso.getPesoTotal();
			
			if (genero == 1) {
				
				tmb = (10*pesoParaCalcular + 6.25*altura - 5*edad + 5)*tdee;
				
			} else {

				tmb = (10*pesoParaCalcular + 6.25*altura - 5*edad + 161)*tdee;
				
			}
			
			double proteina = pesoParaCalcular*2;
			
			double grasa = (tmb*0.3)/9;
			
			System.out.println(grasa);
			
			double carbohidratos = (tmb - (proteina*4) - (grasa*9))/4;
			
			MacrosRecomendados macrosrecomendados = new MacrosRecomendados();
			
			macrosrecomendados.setCalorias(tmb);
			macrosrecomendados.setCarbohidratos(carbohidratos);
			macrosrecomendados.setGrasa(grasa);
			macrosrecomendados.setProteina(proteina);
			macrosrecomendados.setUsuario(usuarioLocalizado.get());
			
			macrosRecomendadosRepo.save(macrosrecomendados);
			
			return "redirect:/peso";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para actualizar los macros recomendados para dejarlos vacios y volver a calcularlos
	@RequestMapping("/actualizarMacrosRecomendados")
	public String actualizarMacrosRecomendados(Model model, HttpSession session) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			macrosRecomendadosRepo.borrarPorUsuario(usuarioLocalizado.get());
			
			return "redirect:/peso";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}
	
	// Controlador para cambiar los datos de comida historial por la fecha
	@RequestMapping("/cambiarDatosPorFecha")
	public String cambiarDatosPorFecha(Model model, HttpSession session, @RequestParam("fechaCambioDeValores") LocalDate fechaCambioDeValores) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			model.addAttribute("fecha", fechaCambioDeValores);
			
			model.addAttribute("atr_lista_macrosRecomendados", macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()));
			
			List<Double> datosMacros = new ArrayList<>();
			
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getCarbohidratos());
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getGrasa());
			datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getProteina());
			
			model.addAttribute("atr_lista_datosMacro", datosMacros);
			
			TotalDiario registroElegido = totalDiarioRepo.findByFechaLocalDate(fechaCambioDeValores);
			
			if (registroElegido == null) {
			   
				model.addAttribute("atr_lista_macrosDia", "");
				model.addAttribute("caloriasEnseñar", 0);
				
				List<Double> datosMacrosComparacion = new ArrayList<>();
				
				double totalProteinas = 0.0;
				double totalCarbohidratos = 0.0;
				double totalGrasas = 0.0;
				double totalCalorias = 0.0;
				
				List<MacrosRecomendados> listaMacrosRecomendadosComparacion = macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get());
				
				double comparacionProteinas = 0.0;
				double comparacionCarbohidratos = 0.0;
				double comparacionGrasas = 0.0;
				double comparacionCalorias = 0.0;
				
				comparacionProteinas = totalProteinas - listaMacrosRecomendadosComparacion.get(0).getProteina();
				comparacionCarbohidratos = totalCarbohidratos - listaMacrosRecomendadosComparacion.get(0).getCarbohidratos();
				comparacionGrasas = totalGrasas - listaMacrosRecomendadosComparacion.get(0).getGrasa();
				comparacionCalorias = totalCalorias - listaMacrosRecomendadosComparacion.get(0).getCalorias();
				
				datosMacrosComparacion.add(comparacionProteinas);
				datosMacrosComparacion.add(comparacionCarbohidratos);
				datosMacrosComparacion.add(comparacionGrasas);
				datosMacrosComparacion.add(comparacionCalorias);
				
				model.addAttribute("atr_lista_macrosComparacion", datosMacrosComparacion);
				
			} else {
				
				Integer idUsuario = (Integer) session.getAttribute("usuario");
				
				TotalDiario idTotalDiario = totalDiarioRepo.findByFechaAndUsuarioLocalDate(fechaCambioDeValores, usuarioLocalizado.get());
				
				List<ComidaDiario> comidasDiario = comidaDiarioRepo.findByIdTotalDiario(idTotalDiario.getId());
				
				List<Double> datosMacrosLista = new ArrayList<>();
				
				double totalProteinas = 0.0;
				double totalCarbohidratos = 0.0;
				double totalGrasas = 0.0;
				double totalCalorias = 0.0;

				for (ComidaDiario comida : comidasDiario) {
				    totalProteinas += comida.getComida().getProteina();
				    totalCarbohidratos += comida.getComida().getCarbohidratos();
				    totalGrasas += comida.getGrasa();
				    totalCalorias += comida.getCalorias();
				}	

				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getCarbohidratos());
				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getGrasa());
				datosMacros.add(macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get()).get(0).getProteina());
				
				datosMacrosLista.add(totalCarbohidratos);
				datosMacrosLista.add(totalGrasas);
				datosMacrosLista.add(totalProteinas);
				
				model.addAttribute("atr_lista_macrosDia", datosMacrosLista);
				model.addAttribute("caloriasEnseñar", totalCalorias);
				
				List<Double> datosMacrosComparacion = new ArrayList<>();
				
				List<MacrosRecomendados> listaMacrosRecomendadosComparacion = macrosRecomendadosRepo.findByUsuario(usuarioLocalizado.get());
				
				double comparacionProteinas = 0.0;
				double comparacionCarbohidratos = 0.0;
				double comparacionGrasas = 0.0;
				double comparacionCalorias = 0.0;
				
				comparacionProteinas = totalProteinas - listaMacrosRecomendadosComparacion.get(0).getProteina();
				comparacionCarbohidratos = totalCarbohidratos - listaMacrosRecomendadosComparacion.get(0).getCarbohidratos();
				comparacionGrasas = totalGrasas - listaMacrosRecomendadosComparacion.get(0).getGrasa();
				comparacionCalorias = totalCalorias - listaMacrosRecomendadosComparacion.get(0).getCalorias();
				
				datosMacrosComparacion.add(comparacionProteinas);
				datosMacrosComparacion.add(comparacionCarbohidratos);
				datosMacrosComparacion.add(comparacionGrasas);
				datosMacrosComparacion.add(comparacionCalorias);
				
				model.addAttribute("atr_lista_macrosComparacion", datosMacrosComparacion);
				
			}
			
			return "comidaHistorial";
	    } else {
	    	
	        return "redirect:/login";
	    }
		


	}
	
	// Controlador para cambiar los datos por la fecha en la parte de peso
	@RequestMapping("/cambiarDatosPorFechaPeso")
	public String cambiarDatosPorFechaPeso(Model model, HttpSession session, @RequestParam("fechaCambioDeValores") Date fechaCambioDeValores) {
		
	    if (session.getAttribute("usuario") != null) {
			Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));
			
			model.addAttribute("fecha", fechaCambioDeValores);
			
			List<Peso> pesosDelMesElegido = pesoRepo.findByUsuarioAndSpecificMonth(usuarioLocalizado.get(), fechaCambioDeValores);


			List<Double> promediosPorSemana = new ArrayList<>();


			LocalDate hoy = LocalDate.now();


			LocalDate primerDiaMes = hoy.withDayOfMonth(1);
			LocalDate ultimoDiaMes = hoy.withDayOfMonth(hoy.lengthOfMonth());


			LocalDate semanaInicio = primerDiaMes;
			while (!semanaInicio.isAfter(ultimoDiaMes)) {

			    LocalDate semanaFin = semanaInicio.plusDays(6).isAfter(ultimoDiaMes) ? ultimoDiaMes : semanaInicio.plusDays(6);


			    final LocalDate inicio = semanaInicio;
			    final LocalDate fin = semanaFin;


			    List<Double> pesosSemana = pesosDelMesElegido.stream()
			            .filter(p -> !p.getFecha().isBefore(inicio) && !p.getFecha().isAfter(fin))
			            .map(Peso::getPesoTotal)
			            .collect(Collectors.toList());


			    if (!pesosSemana.isEmpty()) {
			        double promedioSemana = pesosSemana.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
			        promediosPorSemana.add(promedioSemana);
			    } else {

			        promediosPorSemana.add(0.0);
			    }


			    semanaInicio = semanaFin.plusDays(1);
			}
		    
			
			model.addAttribute("atr_lista_pesoHistorial", promediosPorSemana);
			
			
			return "pesoHistorial";
	    } else {
	    	
	        return "redirect:/login";
	    }

	}
	
	// Controlador para cambiar el historial del peso por el año seleccionado
	@RequestMapping("/cambiarPesoHistorialAño")
	public String cambiarPesoHistorialAño(@RequestParam("fechaCambioDeValores") Date fechaCambioDeValores, Model model, HttpSession session) {

	    if (session.getAttribute("usuario") != null) {

		    Optional<Usuario> usuarioLocalizado = usuarioRepo.findById((Integer) session.getAttribute("usuario"));

		    model.addAttribute("fecha", fechaCambioDeValores);

		    List<Peso> pesosAñoSeleccionado = pesoRepo.findByUsuarioAndSpecificYear(usuarioLocalizado.get(), fechaCambioDeValores);

		    List<Double> promediosPorMes = new ArrayList<>();

		    LocalDate fechaCambioDeValoresLocalDate = fechaCambioDeValores.toLocalDate();
		    
		    LocalDate primerDiaAño = fechaCambioDeValoresLocalDate.withDayOfYear(1);
		    LocalDate ultimoDiaAño = fechaCambioDeValoresLocalDate.withDayOfYear(fechaCambioDeValoresLocalDate.lengthOfYear());


		    LocalDate mesInicio = primerDiaAño;
		    while (!mesInicio.isAfter(ultimoDiaAño)) {

		        LocalDate mesFin = mesInicio.withDayOfMonth(mesInicio.lengthOfMonth());


		        final LocalDate inicio = mesInicio;
		        final LocalDate fin = mesFin;


		        List<Double> pesosMes = pesosAñoSeleccionado.stream()
		                .filter(p -> !p.getFecha().isBefore(inicio) && !p.getFecha().isAfter(fin))
		                .map(Peso::getPesoTotal)
		                .collect(Collectors.toList());


		        if (!pesosMes.isEmpty()) {
		            double promedioMes = pesosMes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		            promediosPorMes.add(promedioMes);
		        } else {

		            promediosPorMes.add(0.0);
		        }


		        mesInicio = mesFin.plusDays(1);
		    }

		    model.addAttribute("atr_lista_pesoHistorial", promediosPorMes);

		    return "pesoHistorialAño";
	    } else {
	    	
	        return "redirect:/login";
	    }
		

	}

	
}
