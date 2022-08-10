package hello.jpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Furniture extends Item{
    private int width;
    private int height;
}
