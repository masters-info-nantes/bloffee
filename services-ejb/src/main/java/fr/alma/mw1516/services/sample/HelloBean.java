package fr.alma.mw1516.services.sample;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
@Stateless
@Remote(HelloRemote.class)
public class HelloBean implements HelloRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Country sayHello(String name) {
        Query query = em.createQuery("from Country where name=:toto");
        query.setParameter("toto", name);
        List<Country> countries = query.getResultList();

        Country result;
        if (countries != null && !countries.isEmpty()) {
            result = countries.iterator().next();
        } else {
            result = new Country();
            result.setName(name);
            em.persist(result);
        }

        return result;
    }

}
