package com.vmdev.eshop.repository;

import com.vmdev.eshop.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {

}
