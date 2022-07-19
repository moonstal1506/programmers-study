package com.example.order;

import java.util.Optional;
import java.util.UUID;

//구현체 바뀔 수 있으므로 인터페이스 정의
public interface VoucherRepository {

    //Voucher 없을 수 있으므로 Optional
    Optional<Voucher> findById(UUID voucherId);
}
