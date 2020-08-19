import Jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Paging {
    public static void main(String[] args) {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction ET = EM.getTransaction();

        ET.begin();

        for (int i = 0; i < 100; i++) {

            Member member = new Member();
            member.setName("member" + i);
            member.setAge(i);
            EM.persist(member);

        }
        List<Member> resultList = EM.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(10).setMaxResults(15).getResultList();

        for (Member member : resultList) {
            System.out.println(member.getName());
        }

        ET.commit();
        EM.close();
        EMF.close();
    }
}
