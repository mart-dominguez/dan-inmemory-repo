package frsf.isi.dan.ejemplo;

import java.util.Comparator;

import frsf.isi.dan.InMemoryRepository;

public class DatoRepository extends InMemoryRepository<Dato> {

	@Override
	public Integer getId(Dato entity) {
		return entity.getId();
	}

	@Override
	public void setId(Dato entity, Integer id) {
		entity.setId(id);
	}




}
