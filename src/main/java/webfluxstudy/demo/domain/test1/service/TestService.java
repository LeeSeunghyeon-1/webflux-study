package webfluxstudy.demo.domain.test1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.repository.TestRepository;

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
        return Flux.fromIterable(testRepository.findAll());
    }

    //TODO : 다운로드 서비스 로직
}
