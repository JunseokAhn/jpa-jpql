import Jpql.Item;
import Jpql.Member;
import Jpql.MemberType;
import Jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Case {

    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction TS = EM.getTransaction();


        TS.begin();

        Member member = new Member();
//        member.setName("Junseok");
        member.setAge(29);
        member.setType(MemberType.USER);

        Team team = new Team();
        team.setName("TEAMA");

        member.changeTeam(team);

        EM.persist(member);
        EM.persist(team);

        EM.flush();


        String query = "select " +
                            "case when m.age <=20 then '학생요금' " +
                            "     when m.age >=60 then '경로요금' " +
                            "     else '성인요금' " +
                            "end " +
                        "from Member m";

        List<String> resultList = EM.createQuery(query,String.class).getResultList();

        for (String s : resultList) {
            System.out.println(s);
        }


        //COALESCE >> 하나씩 조회해서 NULL이 아니면 반환
        String query2 = "select coalesce(m.name, 'Null이면 반환되는 값') from Member m";

        List<String> resultList2 = EM.createQuery(query2, String.class).getResultList();

        for (String s : resultList2) {
            System.out.println(s);
        }

        member.setName("Junseok2");

        //NULLIF >> 해당값이랑 일치하면 NULL반환
        String query3 = "select nullif(m.name, 'Junseok') from Member m";
        List<String> resultList3 = EM.createQuery(query3, String.class).getResultList();

        for (String s : resultList3) {
            System.out.println(s);
        }

        TS.commit();
        EM.close();
        EMF.close();
    }
}
