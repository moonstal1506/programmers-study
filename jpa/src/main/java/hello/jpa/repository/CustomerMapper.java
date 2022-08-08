package hello.jpa.repository;

import hello.jpa.repository.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    void save(Customer customer);
    Customer findById(long id);
}
