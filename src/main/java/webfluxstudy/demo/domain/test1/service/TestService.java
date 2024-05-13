package webfluxstudy.demo.domain.test1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.repository.TestRepository;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public Mono<Member> getMemberByName (String name) {
        return Mono.just(testRepository.findByName(name));
    }

    public Flux<Member> getMemberByName2 (String name) {
        return Flux.just(testRepository.findByName(name));
    }

    public Flux<Member> getAllMembers () {
        return Flux.fromIterable(testRepository.findAll().toIterable());
    }

    //다운로드 서비스 로직
    public Flux<DataBuffer> downloadFile(String filename) {
        Path path = Path.of("C:\\image", filename);
        return testRepository.readFile(path);
    }
}
