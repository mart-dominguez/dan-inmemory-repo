package frsf.isi.dan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.springframework.data.repository.CrudRepository;

public abstract class InMemoryRepository<T> implements CrudRepository<T, Integer>{
	
	protected ConcurrentHashMap<Integer, T> db = new ConcurrentHashMap<Integer, T>();

	public <S extends T> S save(S entity) {
		Integer clave = this.getId(entity); 
		if(clave==null || clave<=0) {
			clave = 1+ findMaximo( Comparator.comparing(i -> i) ).orElse(0);
			setId(entity, clave);
		}
		db.put(clave,entity);
		return entity;
	}
	public void delete(T entity) {
		this.db.remove(getId(entity));
	}
	
	public abstract Integer getId(T entity);
	public abstract void setId(T entity,Integer id);
	
	public Optional<Integer> findMaximo(Comparator<Integer> predicado) {
		return this.db.keySet().stream().max(predicado);
	}

	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		for(S x : entities) {
			save(x);
		}
		return entities;
	}

	public Optional<T> findById(Integer id) {		
		return Optional.ofNullable(db.get(id));
	}

	public boolean existsById(Integer id) {
		return db.get(id)!=null;
	}

	public Iterable<T> findAll() {
		return db.values();
	}

	public Iterable<T> findAllById(Iterable<Integer> ids) {
		ArrayList resultado = new ArrayList<T>();
		for (Integer key : ids) {
			T elemento = db.get(key);
			if(elemento !=null) resultado.add(elemento );
		}	
		return resultado;
	}

	public long count() {
		// TODO Auto-generated method stub
		return db.values().size();
	}

	public void deleteById(Integer id) {
		db.remove(id);
	}

	
	public void deleteAll(Iterable<? extends T> entities) {
		this.db.clear();
	}

	public void deleteAll() {
		this.db.clear();
	}

}
