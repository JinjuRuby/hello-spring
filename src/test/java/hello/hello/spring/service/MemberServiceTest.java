package hello.hello.spring.service;

import hello.hello.spring.domain.Member;
import hello.hello.spring.repository.MemberRepository;
import hello.hello.spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;


    //이 메서드를 사용하지 않으면 클래스에서 MemoryMemberRepository 객체를 계속 생성해야 하는 문제 발생(변수들이 static이라 크게 문제는 없지만 메모리 낭비)
    //따라서 beforeEach를 통해 하나의 MemoryMemberRepository 객체를 생성
    //MemberService입장에서는 MemoryMemberRepository를 외부에서 넣어준다. -> Dependency injection(DI)
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    //이 메서드가 없으면 클래스에서 전체 실행했을 경우 오류 발생
    //(동일한 객체를 다른 메서드에서 계속 만들기 때문)
    //따라서 메서드가 끝나면 데이터를 클리어할 필요가 있다. (AfterEach: 메서드가 끝날때마다 이 메서드 실행)
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/


        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}