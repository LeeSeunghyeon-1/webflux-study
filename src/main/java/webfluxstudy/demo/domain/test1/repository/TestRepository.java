package webfluxstudy.demo.domain.test1.repository;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.nio.file.Path;
import java.util.List;

@Repository
public interface TestRepository extends ReactiveMongoRepository<Member, String>, TestRepositoryCustom{
}
