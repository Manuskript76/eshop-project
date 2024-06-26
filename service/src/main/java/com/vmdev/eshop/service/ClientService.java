package com.vmdev.eshop.service;

import com.vmdev.eshop.dto.ClientCreateEditDto;
import com.vmdev.eshop.dto.ClientReadDto;
import com.vmdev.eshop.mapper.ClientCreateEditMapper;
import com.vmdev.eshop.mapper.ClientReadMapper;
import com.vmdev.eshop.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateEditMapper clientCreateEditMapper;

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::map)
                .toList();
    }

    public Optional<ClientReadDto> findById(Long id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::map);
    }

    public Optional<ClientReadDto> findByEmail(String username) {
        return clientRepository.findByEmail(username)
                .map(clientReadMapper::map);
    }

    @Transactional
    public ClientReadDto create(ClientCreateEditDto clientDto) {
        return Optional.of(clientDto)
                .map(clientCreateEditMapper::map)
                .map(clientRepository::save)
                .map(clientReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ClientReadDto> update(Long id, ClientCreateEditDto clientDto) {
        return clientRepository.findById(id)
                .map(client -> clientCreateEditMapper.map(clientDto, client))
                .map(clientRepository::saveAndFlush)
                .map(clientReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    clientRepository.delete(client);
                    clientRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByEmail(username)
                .map(client -> new User(
                        client.getEmail(),
                        client.getPassword(),
                        Collections.singleton(client.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user:" + username));
    }
}
