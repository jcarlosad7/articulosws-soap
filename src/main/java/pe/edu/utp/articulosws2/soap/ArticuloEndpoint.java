package pe.edu.utp.articulosws2.soap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import pe.edu.utp.articulosws2.AddArticuloRequest;
import pe.edu.utp.articulosws2.AddArticuloResponse;
import pe.edu.utp.articulosws2.ArticuloDetalle;
import pe.edu.utp.articulosws2.DeleteArticuloRequest;
import pe.edu.utp.articulosws2.DeleteArticuloResponse;
import pe.edu.utp.articulosws2.GetAllArticulosRequest;
import pe.edu.utp.articulosws2.GetAllArticulosResponse;
import pe.edu.utp.articulosws2.GetArticuloRequest;
import pe.edu.utp.articulosws2.GetArticuloResponse;
import pe.edu.utp.articulosws2.ServiceStatus;
import pe.edu.utp.articulosws2.UpdateArticuloRequest;
import pe.edu.utp.articulosws2.UpdateArticuloResponse;
import pe.edu.utp.articulosws2.entity.Articulo;
import pe.edu.utp.articulosws2.service.ArticuloService;

@Endpoint
public class ArticuloEndpoint {
	@Autowired
	private ArticuloService service;
	
	@PayloadRoot(namespace = "http://utp.edu.pe/articulosws2", localPart = "GetAllArticulosRequest")
	@ResponsePayload
	public GetAllArticulosResponse findAll (@RequestPayload GetAllArticulosRequest request) {
		GetAllArticulosResponse response = new GetAllArticulosResponse();
		
		Pageable page = PageRequest.of(request.getOffset(), request.getLimit());
		List<Articulo> articulos;
		if(request.getTexto()==null) {
			articulos = service.findAll(page);
		}else {
			articulos=service.findByNombre(request.getTexto(), page);
		}
		
		List<ArticuloDetalle> articulosResponse=new ArrayList<>();
		for (int i = 0; i < articulos.size(); i++) {
		     ArticuloDetalle ob = new ArticuloDetalle();
		     BeanUtils.copyProperties(articulos.get(i), ob);
		     articulosResponse.add(ob);    
		}
		response.getArticuloDetalle().addAll(articulosResponse);
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/articulosws2", localPart = "GetArticuloRequest")
	@ResponsePayload
	public GetArticuloResponse findById (@RequestPayload GetArticuloRequest request) {
		GetArticuloResponse response = new GetArticuloResponse();
		Articulo entity=service.findById(request.getId());
		ArticuloDetalle articulo=new ArticuloDetalle();
		articulo.setId(entity.getId());
		articulo.setNombre(entity.getNombre());
		articulo.setPrecio(entity.getPrecio());
		response.setArticuloDetalle(articulo);		
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/articulosws2", localPart = "AddArticuloRequest")
	@ResponsePayload
	public AddArticuloResponse create (@RequestPayload AddArticuloRequest request) {
		ServiceStatus serviceStatus=new ServiceStatus();
		AddArticuloResponse response = new AddArticuloResponse();
		Articulo entity = new Articulo();
		entity.setNombre(request.getNombre());
		entity.setPrecio(request.getPrecio());
		entity=service.save(entity);
		if(entity!=null) {
			ArticuloDetalle articulo=new ArticuloDetalle();
			BeanUtils.copyProperties(entity, articulo);
			response.setArticuloDetalle(articulo);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
			response.setServiceStatus(serviceStatus);
		}else {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Already Available");
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	@PayloadRoot(namespace = "http://utp.edu.pe/articulosws2", localPart = "UpdateArticuloRequest")
	@ResponsePayload
	public UpdateArticuloResponse update (@RequestPayload UpdateArticuloRequest request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		UpdateArticuloResponse response= new UpdateArticuloResponse();
		Articulo entity = service.findById(request.getId());
		entity.setNombre(request.getNombre());
		entity.setPrecio(request.getPrecio());
		entity=service.update(entity);		
		if (entity!=null) {
			ArticuloDetalle articulo= new ArticuloDetalle();
			BeanUtils.copyProperties(entity, articulo);
			response.setArticuloDetalle(articulo);
			serviceStatus.setStatusCode("SUCCESS");
     	    serviceStatus.setMessage("Content Updated Successfully");
     	    response.setServiceStatus(serviceStatus);			
		}else {
			serviceStatus.setStatusCode("CONFLICT");
	     	serviceStatus.setMessage("Content Not Updated");
	     	response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/articulosws2", localPart = "DeleteArticuloRequest")
	@ResponsePayload
	public DeleteArticuloResponse create (@RequestPayload DeleteArticuloRequest request) {
		ServiceStatus serviceStatus=new ServiceStatus();
		DeleteArticuloResponse response = new DeleteArticuloResponse();
		boolean resp=service.delete(request.getId());
		if(resp) {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
			response.setServiceStatus(serviceStatus);
		}else {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Not Deleted");
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
}
