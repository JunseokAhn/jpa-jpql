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

        //쿼리가 날아가기전, flush가 실행됨
        List<Member> member = EM.createQuery(
                "select m from Member m where m.name like '%kim%'",
                Member.class).getResultList();
        for (Member i : member) {
            System.out.println(i);
        }

        List<Member> member2 = EM.createNativeQuery(
                "select * from member where name like '%kim%'",
                Member.class).getResultList();

        for (Member i : member2) {
            System.out.println(i);
        }

        //이름기준찾기 =:Column  위치기준찾기 ?1  ?2  (인덱스)
        Member member3 = EM.createQuery("select m from Member m where m.name =:name",
                Member.class)
                .setParameter("name", "Junsoek")
                .getSingleResult();

        System.out.println(member3);

        ET.commit();
        EM.close();
        EMF.close();
    }
}
