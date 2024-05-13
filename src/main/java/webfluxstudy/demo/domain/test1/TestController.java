package webfluxstudy.demo.domain.test1;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.service.TestService;
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    //방법1 : [MONO] just
    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello, WebFlux!");
    }

    //방법2-1 : [MONO] fromCallable
//    @GetMapping("/members/{id}")
//    public Mono<Member> getUserById(@PathVariable String id) {
//        return testService.getMemberByName(id);
//    }

    //방법2-2 : [Flux] fromCallable
    @GetMapping("/members/{id}")
    public Flux<Member> getUserById(@PathVariable String id) {
        return testService.getMemberByName2(id);
    }

    //방법3 : [Flux] fromIterable
    @GetMapping("/members")
    public Flux<Member> getAllUsers() {
        return testService.getAllMembers();
    }
    
    /**
     * [ 과제 ] -> webFlux 를 이용하여 파일 다운로드 구현
     * */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Flux<DataBuffer>> downloadFile(@PathVariable String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return ResponseEntity.ok()
                .headers(headers)
                .body(testService.downloadFile(filename));
    }

}
