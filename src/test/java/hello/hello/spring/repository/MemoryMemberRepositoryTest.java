package hello.hello.spring.repository;

import hello.hello.spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//Assertions.assertThat(A).isEqualTo(B);: A와 B가 같은지 비교(실패하면 오류 발생, 참조형, 기본형 모두 가능)
import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //이 메서드가 없으면 클래스에서 전체 실행했을 경우 오류 발생
    //(동일한 객체를 다른 메서드에서 계속 만들기 때문)
    //따라서 메서드가 끝나면 데이터를 클리어할 필요가 있다. (AfterEach: 메서드가 끝날때마다 이 메서드 실행)
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {

        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
