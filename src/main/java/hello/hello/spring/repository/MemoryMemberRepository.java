package hello.hello.spring.repository;

import hello.hello.spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //왜 save만 Optional을 사용하지 않는걸까?
    //다른 메서드들은 값을 찾는 메서드이기 때문에 null값을 반환해야 하는 경우 발생
    //하지만 save메서드는 오로지 Map에 저장만 하고 저장한 값을 그대로 반환
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        
        // stream, lamda, findAny에 대해 알아보자
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    //store.values는 store에 값이 하나도 없어도 null이 아니라 빈 컬렉션을 반환
    //따라서 값을 찾는 메서드이지만 Optional을 사용하지 않는다.
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // store 안의 key, value값 전체 삭제
    public void clearStore() {
        store.clear();
    }
}
