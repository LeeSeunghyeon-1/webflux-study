package webfluxstudy.demo.domain.test1.repository;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.nio.file.Path;

@Repository
public interface TestRepository extends ReactiveMongoRepository<Member, String> {
    Member findByName(String name);
    Flux<Member> findAll();
    Flux<DataBuffer> readFile(Path path);
}
