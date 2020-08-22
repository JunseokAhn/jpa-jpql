import Jpql.Item;
import Jpql.Member;
import Jpql.MemberType;
import Jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Type {

    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction TS = EM.getTransaction();


        TS.begin();

        Member member = new Member();
        member.setName("Junseok");
        member.setAge(29);
        member.setType(MemberType.USER);

        Team team = new Team();
        team.setName("TEAMA");

        member.changeTeam(team);

        EM.persist(member);
        EM.persist(team);

        EM.flush();
        EM.clear();


        String query = "select m.name, 'HELLO', true from Member m " +
                "where m.type = Jpql.MemberType.USER";

        List<Object[]> resultList = EM.createQuery(query).getResultList();


        //패키지명이 길어질경우 아래방법으로 임포트해서 쓸수있다.
        String query2 = "select m.name, 'HELLO', true from Member m " +
                "where m.type = :myType";

        List<Object[]> resultList2 = EM.createQuery(query2).setParameter("myType", MemberType.USER).getResultList();


        //DType이 Book인 Item만 찾는다
        String query3 = "select i from Item i " +
                "where type(i) = Book";

        List<Item> resultList3 = EM.createQuery(query3, Item.class).getResultList();

        for (Object[] objects : resultList2) {
            System.out.println(objects[0]);
            System.out.println(objects[1]);
            System.out.println(objects[2]);
        }

        TS.commit();
        EM.close();
        EMF.close();
    }
}
