package webfluxstudy.demo.domain.test1;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.service.TestService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService userService;

    //방법1 : [MONO] just
    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello, WebFlux!");
    }

    //방법2-1 : [MONO] fromCallable
//    @GetMapping("/members/{id}")
//    public Mono<Member> getUserById(@PathVariable String id) {
//        return userService.getMemberByName(id);
//    }

    //방법2-2 : [Flux] fromCallable
    @GetMapping("/members/{id}")
    public Flux<Member> getUserById(@PathVariable String id) {
        return userService.getMemberByName2(id);
    }

    //방법3 : [Flux] fromIterable
    @GetMapping("/members")
    public Flux<Member> getAllUsers() {
        return userService.getAllMembers();
    }
    
    /**
     * [ 과제 ] -> webFlux 를 이용하여 파일 다운로드 구현
     * */
    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response) throws IOException {
        // TODO : 구현할 것

        
    }

}
