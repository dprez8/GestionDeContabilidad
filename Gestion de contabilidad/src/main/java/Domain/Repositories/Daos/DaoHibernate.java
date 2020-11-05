package Domain.Repositories.Daos;

import db.EntityManagerHelper;
import db.EntityManagerHelperTwo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DaoHibernate<T> implements IDao<T>{
    private Class<T> type;

    public DaoHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        CriteriaBuilder builder = EntityManagerHelperTwo.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        List<T> entities = EntityManagerHelperTwo.getEntityManager().createQuery(critera).getResultList();
        return entities;
    }

    @Override
    public boolean existe(int id) {
        return EntityManagerHelperTwo.getEntityManager().find(type, id) != null;
    }

    @Override
    public T buscar(int id) {
        EntityManager em = EntityManagerHelperTwo.getEntityManager();
        T object = em.find(type, id);
        return object;
    }


    @Override
    public void agregar(Object unObjeto) {
        EntityManager em = EntityManagerHelperTwo.getEntityManager();
        em.getTransaction().begin();
        em.persist(unObjeto);
        em.getTransaction().commit();
    }

    @Override
    public void modificar(Object unObjeto) {
        EntityManager em = EntityManagerHelperTwo.getEntityManager();
        em.getTransaction().begin();
        em.merge(unObjeto);
        em.getTransaction().commit();
    }

    @Override
    public void eliminar(Object unObjeto) {
        EntityManager em = EntityManagerHelperTwo.getEntityManager();
        em.getTransaction().begin();
        em.remove(unObjeto);
        em.getTransaction().commit();
    }

    public T buscarPorQuery(String query) {
        return (T) EntityManagerHelperTwo.createQuery(query).getSingleResult();
    }

}
