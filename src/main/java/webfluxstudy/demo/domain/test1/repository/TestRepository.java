package webfluxstudy.demo.domain.test1.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.util.List;

@Repository
public interface TestRepository extends MongoRepository<Member, String> {
    Member findByName(String name);
    List<Member> findAll();
}
