package com.ef.repository;

import com.ef.entity.BlockedIpAddress;
import org.springframework.data.repository.CrudRepository;

public interface BlockedIpAddressRepository extends CrudRepository<BlockedIpAddress, Long> {
}
