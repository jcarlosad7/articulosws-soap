package pe.edu.utp.articulosws2.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.utp.articulosws2.entity.Articulo;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer>{
	List<Articulo> findByNombreContaining(String nombre,Pageable page);
	Articulo findByNombre(String nombre);
}
