package com.ef.repository;

import com.ef.entity.BlockedIpAddress;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class BlockedIpAddressRepositoryTest {
    @Autowired
    private BlockedIpAddressRepository blockedIpAddressRepository;

    @Test
    public void basicOperations() {
        BlockedIpAddress blockedIpAddress = new BlockedIpAddress("127.0.0.1", LocalDateTime.now(), "Just because");

        blockedIpAddressRepository.save(blockedIpAddress);

        List<BlockedIpAddress> persistedBlockedIpAddresses = Lists.newArrayList(blockedIpAddressRepository.findAll());

        assertThat(persistedBlockedIpAddresses.size()).isEqualTo(1);

        persistedBlockedIpAddresses.get(0).setIpAddress("192.168.1.1");

        blockedIpAddressRepository.save(persistedBlockedIpAddresses.get(0));

        persistedBlockedIpAddresses = Lists.newArrayList(blockedIpAddressRepository.findAll());

        assertThat(persistedBlockedIpAddresses.size()).isEqualTo(1);

        assertThat(persistedBlockedIpAddresses.get(0).getIpAddress()).isEqualTo("192.168.1.1");

    }
}
