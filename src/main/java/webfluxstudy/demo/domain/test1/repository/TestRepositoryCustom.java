package webfluxstudy.demo.domain.test1.repository;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.nio.file.Path;
@NoRepositoryBean
public interface TestRepositoryCustom {
    Member findByName(String name);
    //    List<Member> findAll();
//    Flux<DataBuffer> readFile(Path path);
}
