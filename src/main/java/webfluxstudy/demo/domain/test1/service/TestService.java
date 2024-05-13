package webfluxstudy.demo.domain.test1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import webfluxstudy.demo.domain.test1.entity.Member;
import webfluxstudy.demo.domain.test1.repository.TestRepository;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public Mono<Member> getMemberByName (String name) {
        return Mono.just(testRepository.findByName(name));
    }

    public Flux<Member> getMemberByName2 (String name) {
        return Flux.just(testRepository.findByName(name));
    }

//    public Flux<Member> getAllMembers () {
//        return Flux.fromIterable(testRepository.findAll().iterator());
//    }

    //다운로드 서비스 로직
    public Flux<DataBuffer> downloadFile(String filename) {
        Path path = Path.of("C:\\image", filename);
        log.info("path: {}", path);
        return readFile(path);
    }

    public Flux<DataBuffer> readFile(Path path) {
        DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
        return Flux.<DataBuffer>create(sink -> {
            try {
                AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                readDataBuffer(channel, byteBuffer, sink, bufferFactory);
                sink.onDispose(() -> {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        log.error("Error closing file channel", e);
                    }
                });
            } catch (Exception e) {
                log.error("Failed to open file channel", e);
                sink.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void readDataBuffer(AsynchronousFileChannel channel, ByteBuffer byteBuffer, FluxSink<DataBuffer> sink, DataBufferFactory bufferFactory) {
        channel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                log.debug("Read {} bytes", result);
                buffer.flip();
                if (buffer.hasRemaining()) {
                    sink.next(bufferFactory.wrap(buffer));
                }
                buffer.clear();
                if (result == -1) {
                    sink.complete();
                } else {
                    channel.read(buffer, buffer.position(), buffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                log.error("Read data failed", exc);
                sink.error(exc);
            }
        });
    }


//    public Flux<DataBuffer> downloadFile(String filename) {
//        Path path = Path.of("C:\\image", filename);
//        return testRepository.readFile(path);
//    }
}
