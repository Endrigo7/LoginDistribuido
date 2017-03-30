/**
 * 
 */
package br.ufpe.servidorautenticacao.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ufpe.servidorautenticacao.model.Usuario;
import br.ufpe.servidorautenticacao.util.HibernateUtil;

/**
 * @author Endrigo
 *
 */
public class UsuarioDAO {
	private EntityManager entityManager;
	
	public UsuarioDAO(){
		entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
	}

	private EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void save(Usuario u){
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(u);
		getEntityManager().getTransaction().commit();
	}
	
	public Usuario findById(int id){
		return getEntityManager().find(Usuario.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Usuario> findAll(){
		return getEntityManager().createQuery("SELECT a FROM Usuario a").getResultList();
	}
	
	public void close(){
		getEntityManager().close();
	}
}
