import Jpql.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Projection {
    public static void main(String[] args) {

        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction ET = EM.getTransaction();

        ET.begin();

        Member member = new Member();
        member.setName("Junsoku");
        member.setAge(29);

        Team team = new Team();
        team.setName("TeamA");

        member.setTeam(team);

        Order order = new Order();
        Address address = new Address();
        order.setAddress(address);

        EM.persist(member);
        EM.persist(team);
        EM.persist(order);

        EM.flush();
        EM.clear();

        //select 뒤에 distinct넣으면 중복제거  "select distinct ...."

        //Entity Projection
        List<Member> found = EM.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println(found.get(0));

        List<Team> found2 = EM.createQuery("select m.team from Member m", Team.class).getResultList();
        System.out.println(found2.get(0));

        //EmbeddedType Projection
        List<Address> found3 = EM.createQuery("select o.address from Order o", Address.class).getResultList();
        System.out.println(found3.get(0));

        //ScalaType Projection >> Object타입으로 받은뒤, Object배열로 형변환
        List found4 = EM.createQuery("select m.name, m,age from Member m").getResultList();

        Object o = found4.get(0);
        Object[] result = (Object[]) o;

        for (Object o2 : result) {
            System.out.println(o2);
        }

        //ScalaType Projection >> Object 배열타입으로 받는 방법
        List<Object[]> found5 = EM.createQuery("select m.name, m,age from Member m").getResultList();

        Object[] result2 = found5.get(0);
        for (Object o3 : result2) {
            System.out.println(o3);
        }

        //조회하려는 객체타입을 만들어두고 new생성자로 받는방법 >> new DTO
        //패키지가 길어지면 입력해주는 텍스트가 길어지는 문제가있지만, 추후 쿼리DSL을 사용하면 해결되므로, 가장 깔끔한 방법
        List<MemberDTO> found6 = EM.createQuery("select new Jpql.MemberDTO(m.name, m.age) from Member m", MemberDTO.class).getResultList();
        MemberDTO result3 = found6.get(0);
        System.out.println(result3.getName());

        ET.commit();
        EM.close();
        EMF.close();
    }
}
