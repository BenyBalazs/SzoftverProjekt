package database;

import model.Dolgozok;
import org.tinylog.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public static List<Dolgozok> findByName(String selectedName) {
        EntityManager em = EmfGetter.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Dolgozok> cq = cb.createQuery(Dolgozok.class);
        Root<Dolgozok> from = cq.from(Dolgozok.class);

        cq.select(from).where(cb.like(from.get("name"), selectedName));
        try {
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return new ArrayList<>();
    }

    public static void insertEmployee(Dolgozok newEmployee){
        EntityManager em = EmfGetter.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(newEmployee);
            em.getTransaction().commit();
        }catch (Exception e){

        }finally {
            em.close();
        }
    }

    public static void  commitChange(Dolgozok change){
        EntityManager em = EmfGetter.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(change);
            em.getTransaction().commit();
        }catch (Exception e){
            Logger.error("ERROR.");
        }finally {
            em.close();
        }
    }

    public static void removeEmployee(Dolgozok entity){
        EntityManager em = EmfGetter.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        }catch (Exception e) {
        } finally {
            em.close();
        }
    }
}