import Jpql.Member;
import Jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class SubQuery {

    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction TS = EM.getTransaction();

        //JPA표준에서는 select절 서브쿼리가 안되지만, 하이버네이트는 됨.
        //from절은 안됨 > join으로 해결할수있는만큼 해결해야함

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

        //나이가 평균보다 많은 회원
        String subQuery = "select m from Member m where m.age > (select avg(m2.age) from Member m2)";
        //주문을 한 번 이상 한 회원
        String subQuery2 = "select m from Member m where 0<(select count(o) from Order o where m = o.member)";
        //TEAMA소속의 회원
        String subQuery3 = "select m from Member m where exists (select t from m.team t where t.name = 'TEAMA'";
        //상품 각각의 재고들보다 주문량이 많은 상품들
        String subQuery4 = "select o from Order o where o.orderAmount > ALL (select p.stockAmount from Prooduct p)";
        //어떤 팀이든 팀에 소속된 회원
        String subQuery5 = "select m from Member m where m.team = ANY (select t from Team t)";

        List<Member> resultList = EM.createQuery(subQuery5, Member.class).getResultList();

        for (Member member1 : resultList) {
            System.out.println(member1.getName());
        }
        TS.commit();
        EM.close();
        EMF.close();
    }
}
