import Jpql.Member;
import Jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Join {

    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction TS = EM.getTransaction();

        TS.begin();

        Member member = new Member();
        member.setName("Junseok");
        member.setAge(29);

        Team team = new Team();
        team.setName("TEAMA");

        member.changeTeam(team);

        EM.persist(member);
        EM.persist(team);

        EM.flush();
        EM.clear();

        String query = "select m from Member m join m.team t";
        String query2 = "select m from Member m join m.team t where t.id = m.team";
        String query3 = "select m from Member m left join m.team t";
        String query4 = "select m from Member m, Team t where m.team = t.id";
        String query5 = "select m from Member m left join m.team t on t.name='TEAMA'";
        String query6 = "select m from Member m left join Team t on t.id = m.team";

        List<Member> resultList = EM.createQuery(query6, Member.class).getResultList();

        for (Member member1 : resultList) {
            System.out.println(member1.getName());
        }
        TS.commit();
        EM.close();
        EMF.close();
    }
}
