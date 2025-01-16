package com.ejemplo.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ejemplo.ecommerce.model.Producto;
import com.ejemplo.ecommerce.model.Usuario;
import com.ejemplo.ecommerce.service.ProductoService;
import com.ejemplo.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UploadFileService upload;
	
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
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException{//se agrega otro parametro el Multi
		LOGGER.info("este es el producto prueba{}", producto);
		Usuario u = new Usuario(1,"","","","","","","");
		producto.setUsuario(u);
		
		//logica de subida de imagen con upload:
		
		if (producto.getId()==null) {// cuando se crea un producto
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);	
		}else {
			if (file.isEmpty()) {//editamos el producto pero no cambiams la imagen.
				Producto p= new Producto();
				p= productoService.get(producto.getId()).get();
				producto.setImagen(p.getImagen());
			}else {
				String nombreImagen = upload.saveImage(file);
				producto.setImagen(nombreImagen);	
			}
		}
		//GUARDAR 
		productoService.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto=new Producto();
		Optional<Producto> optionalProducto= productoService.get(id); //ESTO ES LO QUE NOS DEVUELVE AL BUSCAR UN OBJETO
		producto= optionalProducto.get();
		
		LOGGER.info("PRODUCTO BUSCADO: {}", producto);
		model.addAttribute("producto", producto);
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productoService.delete(id);
		return "redirect:/productos";
	}
	
	
}
