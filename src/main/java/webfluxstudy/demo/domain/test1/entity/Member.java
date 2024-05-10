package webfluxstudy.demo.domain.test1.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Member(
    @Id
    ObjectId id,
    @Indexed
    String name,
    @Indexed
    String email
) {
}
