package hello.hello.spring.repository;

import hello.hello.spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { //interface가 interface를 받을 때는 extends를 사용한다.


    //select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
