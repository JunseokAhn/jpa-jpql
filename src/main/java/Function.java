import Jpql.Item;
import Jpql.Member;
import Jpql.MemberType;
import Jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Function {

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

        //concat substring trim lower upper length locate abs sqrt mod size index

        String query = "select 'a' || 'b' from Member m";
        String query2 = "select concat('a','b') from Member m";
        String query3 = "select substring(m.name, 2, 3) from Member m";
        String query4 = "select locate('c', 'abc') from Member m";
        String query5 = "select size(t.members) from Team t";

        List<String> resultList = EM.createQuery(query3, String.class).getResultList();
        
        //locate = index숫자값   size = 일대다로 엮여있을때 해당테이블 크기
        List<Integer> resultList2 = EM.createQuery(query4, Integer.class).getResultList();

        for (String s : resultList) {
            System.out.println(s);
        }

        for (Integer i : resultList2) {
            System.out.println(i);
        }
        TS.commit();
        EM.close();
        EMF.close();
    }
}
