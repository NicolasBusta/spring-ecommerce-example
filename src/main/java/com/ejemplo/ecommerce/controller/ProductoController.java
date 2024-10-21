package com.ejemplo.ecommerce.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ejemplo.ecommerce.model.Producto;
import com.ejemplo.ecommerce.model.Usuario;
import com.ejemplo.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show(Model model){
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create(){
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto){
		LOGGER.info("este es el producto prueba{}", producto);
		
		Usuario u = new Usuario(1,"","","","","","","");
		producto.setUsuario(u);
		productoService.save(producto);
		return "redirect:/productos";
	}
	
}
