package Jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Criteria {
    public static void main(String[] args) {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hello");
        EntityManager EM = EMF.createEntityManager();
        EntityTransaction ET = EM.getTransaction();

        ET.begin();

        CriteriaBuilder CB = EM.getCriteriaBuilder();
        CriteriaQuery<Member> query = CB.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);

        CriteriaQuery<Member> query2 = query.select(m).where(CB.equal(m.get("name"), "kim"));
        List<Member> resultList = EM.createQuery(query2).getResultList();

        System.out.println(resultList);
        
        //criteria 자바쿼리로 jpql을 만들수있음 >> 쓰면 컴파일러가 오류잡아주고 편한데 개 어렵다.
        //실무에서 안씀 쓰지말자 대신 QueryDSL 권장

        ET.commit();
        EM.close();
        EMF.close();
    }
}
