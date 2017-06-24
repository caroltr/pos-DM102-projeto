package br.inatel.pos.mobile.dm102.projeto.model;

import java.util.Collection;

public interface GenericDAO<T> {

	void criar(T obj);
	Collection<T> listar();
	
}
