package hello.jpa.domain.parent;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParentId implements Serializable {
    private String id1;
    private String id2;
}
