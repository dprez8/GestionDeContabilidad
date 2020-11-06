package Domain.Repositories.Daos;

import db.EntityManagerHelper;

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
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        List<T> entities = EntityManagerHelper.getEntityManager().createQuery(critera).getResultList();
        return entities;
    }

    @Override
    public boolean existe(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id) != null;
    }

    @Override
    public T buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id);
    }


    @Override
    public void agregar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void modificar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();

    }

    @Override
    public void eliminar(Object unObjeto) {

        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public T buscarPorQuery(String query) {
        return (T) EntityManagerHelper.getEntityManager().createQuery(query).getSingleResult();
    }

}
