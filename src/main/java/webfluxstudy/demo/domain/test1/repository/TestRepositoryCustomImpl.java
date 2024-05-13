package webfluxstudy.demo.domain.test1.repository;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;
import webfluxstudy.demo.domain.test1.entity.Member;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class TestRepositoryCustomImpl implements TestRepository{
    private final MongoOperations mongoOperations;
    private final DataBufferFactory bufferFactory = new DefaultDataBufferFactory(){};
    private final List<Member> members = Arrays.asList(
            new Member(new ObjectId(), "이승현", "test@naver.com"),
            new Member(new ObjectId(), "이유승", "test@gmail.com")
    );

    @Override
    public Member findByName(String name) {
        return members.stream().filter(members -> members.name().equals(name)).findFirst().orElse(null);
    }

//    @Override
//    public List<Member> findAll() {
//        return (Flux<Member>) members;
//    }

//    @Override
//    public Flux<DataBuffer> readFile(Path path) {
//        try {
//            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
//            return Flux.create(sink -> {
//                int bufferSize = 4096;
//                ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
//                channel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
//                    @Override
//                    public void completed(Integer result, ByteBuffer buffer) {
//                        buffer.flip();
//                        sink.next(bufferFactory.wrap(buffer));
//                        buffer.clear();
//                        if (result != -1) {
//                            channel.read(buffer, buffer.position(), buffer, this);
//                        } else {
//                            sink.complete();
//                        }
//                    }
//
//                    @Override
//                    public void failed(Throwable exc, ByteBuffer buffer) {
//                        sink.error(exc);
//                    }
//                });
//            });
//        } catch (IOException e) {
//            return Flux.error(e);
//        }
//    }
//    @Override
//    public Flux<DataBuffer> readFile(Path path) {
//        DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
//
//        return Flux.<DataBuffer>create(sink -> {
//            try {
//                AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
//                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
//                readDataBuffer(channel, byteBuffer, sink, bufferFactory);
//
//                sink.onDispose(() -> {
//                    try {
//                        channel.close();
//                    } catch (Exception e) {
//                        System.err.println("Error closing file channel: " + e.getMessage());
//                    }
//                });
//            } catch (Exception e) {
//                sink.error(e);
//            }
//        }).subscribeOn(Schedulers.boundedElastic());
//    }
//
//    private void readDataBuffer(AsynchronousFileChannel channel, ByteBuffer byteBuffer, FluxSink<DataBuffer> sink, DataBufferFactory bufferFactory) {
//        channel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
//            @Override
//            public void completed(Integer result, ByteBuffer buffer) {
//                buffer.flip();
//                if (buffer.hasRemaining()) {
//                    sink.next(bufferFactory.wrap(buffer));
//                }
//                buffer.clear();
//                if (result != -1) {
//                    channel.read(buffer, buffer.position(), buffer, this);
//                } else {
//                    sink.complete();
//                }
//            }
//
//            @Override
//            public void failed(Throwable exc, ByteBuffer buffer) {
//                sink.error(exc);
//            }
//        });
//    }
}
