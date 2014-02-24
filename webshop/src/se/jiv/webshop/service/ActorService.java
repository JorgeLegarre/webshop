package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ActorModel;
import se.jiv.webshop.repository.ActorRepository;

public class ActorService
{
	private final ActorRepository actorRepository;
	
	public ActorService(ActorRepository actorRepository){
		this.actorRepository = actorRepository;
	}
	
	public ActorModel addActor( ActorModel actor) throws WebshopAppException{
		return actorRepository.addActor(actor);
	}
	
	public boolean updateActor(ActorModel actor) throws WebshopAppException{
		return actorRepository.updateActor(actor);
	}
	
	public ActorModel getActor(int id) throws WebshopAppException{
		return actorRepository.getActor(id);
	}
	
	public List<ActorModel> search(String name){
		return actorRepository.search(name);
	}
	
	public List<ActorModel> getAllActors() throws WebshopAppException{
		return actorRepository.getAllActors();
	}
	
	public boolean deleteActor(int id) throws WebshopAppException{
		return actorRepository.deleteActor(id);
	}

}
