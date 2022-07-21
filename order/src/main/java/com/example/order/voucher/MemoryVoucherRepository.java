package com.example.order.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary
@Profile({"local","default"})
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    //생성
    @PostConstruct
    public void postConstruct() {
        System.out.println("=============postConstruct============");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("=============afterPropertiesSet============");
    }

    //소멸
    @PreDestroy
    public void preDestroy() {
        System.out.println("=============preDestroy============");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("=============destroy============");
    }
}
