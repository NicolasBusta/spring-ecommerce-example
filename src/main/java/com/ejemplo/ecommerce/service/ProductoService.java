package com.ejemplo.ecommerce.service;

import java.util.Optional;

import com.ejemplo.ecommerce.model.Producto;

public interface ProductoService {

	public Producto save(Producto producto); 
	public Optional<Producto> get(Integer id); //opcion d evalidar si el objeto existe o no en la DB.
	public void update(Producto producto);
	public void delete(Integer id);
}