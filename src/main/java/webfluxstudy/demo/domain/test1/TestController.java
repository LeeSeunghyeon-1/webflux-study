package webfluxstudy.demo.domain.test1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.service.TestService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/test")
@Slf4j
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
//    @GetMapping("/members")
//    public Flux<Member> getAllUsers() {
//        return testService.getAllMembers();
//    }
    
    /**
     * [ 과제 ] -> webFlux 를 이용하여 파일 다운로드 구현
     * */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Flux<DataBuffer>> downloadFile(@PathVariable String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        log.info("filename: {}", filename);
        log.info("headers: {}", headers);
        return ResponseEntity.ok()
                .headers(headers)
                .body(testService.downloadFile(filename));
    }

    @GetMapping("/download")
    public Flux<DataBuffer> getTest() throws IOException {
        Resource resource = new FileSystemResource("C:\\image\\mmmm.png");
        System.out.println("resource.contentLength()" + resource.contentLength());
        return DataBufferUtils.read(
                resource,
                new DefaultDataBufferFactory(),
                resource.getContentAsByteArray().length
        );
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("file : {}", file);
        log.info("fileName : {}", file.getOriginalFilename());
        log.info(file.getContentType());
        return "파일 업로드에 성공하였습니다. ";
    }

}
