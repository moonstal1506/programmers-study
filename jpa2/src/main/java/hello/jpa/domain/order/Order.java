package hello.jpa.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "memo")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    //member_fk
    @Column(name = "member_id", insertable = false, updatable = false) //fk
    private Long memberId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
}
