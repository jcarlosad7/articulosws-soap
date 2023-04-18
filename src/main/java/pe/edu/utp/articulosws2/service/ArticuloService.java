package pe.edu.utp.articulosws2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pe.edu.utp.articulosws2.entity.Articulo;
import pe.edu.utp.articulosws2.repository.ArticuloRepository;

@Service
@Slf4j
public class ArticuloService {
	@Autowired
	private ArticuloRepository repository;
	
	@Transactional(readOnly=true)
	public List<Articulo> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public List<Articulo> findByNombre(String nombre, Pageable page) {
		try {
			return repository.findByNombreContaining(nombre,page);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Transactional(readOnly=true)
	public Articulo findById(int id) {
		try {
			Articulo registro = repository.findById(id).orElseThrow();
			return registro;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Transactional
	public Articulo save(Articulo articulo) {
		try {
			if(repository.findByNombre(articulo.getNombre())!=null) {
				return null;
			}
			Articulo nuevo=repository.save(articulo);
			return nuevo;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	@Transactional
	public Articulo update(Articulo articulo) {
		try {
			Articulo registro = repository.findById(articulo.getId()).
					orElseThrow();
			Articulo registroD= repository.findByNombre(articulo.getNombre());
			
			if(registroD!=null && articulo.getId()!=registroD.getId()) {
				return null;
			}
			
			registro.setNombre(articulo.getNombre());
			registro.setPrecio(articulo.getPrecio());
			repository.save(registro);
			return registro;
		}catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Transactional
	public boolean delete(int id) {
		try {
			Articulo articulo=repository.findById(id).orElseThrow();
			repository.delete(articulo);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
}
