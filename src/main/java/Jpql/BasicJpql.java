package Jpql;

import Jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class BasicJpql {

    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction ET = EM.getTransaction();

        ET.begin();
        List<Member> member = EM.createQuery(
                "select m from Member m where m.name like '%kim%'",
                Member.class).getResultList();
        for (Member i : member) {
            System.out.println(i);
        }
        ET.commit();
        EM.close();
        EMF.close();
    }
}
