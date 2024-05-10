package webfluxstudy.demo.domain.test1.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.util.Arrays;
import java.util.List;

@Primary
@RequiredArgsConstructor
public abstract class TestRepositoryImpl implements TestRepository{
    private final List<Member> members = Arrays.asList(
            new Member(new ObjectId(), "이승현", "test@naver.com"),
            new Member(new ObjectId(), "이유승", "test@gmail.com")
    );

    @Override
    public Member findByName(String name) {
        return members.stream().filter(members -> members.name().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }
}
