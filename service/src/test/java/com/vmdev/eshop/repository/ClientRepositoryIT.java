package com.vmdev.eshop.repository;

import com.querydsl.core.types.Predicate;
import com.vmdev.eshop.dto.ClientFilter;
import com.vmdev.eshop.entity.Client;
import com.vmdev.eshop.entity.QClient;
import com.vmdev.eshop.entity.enums.Role;
import com.vmdev.eshop.filter.QPredicate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ClientRepositoryIT extends IntegrationTestBase {

    private final ClientRepository clientRepository;

    @Test
    void findAllClientsWithFirstnameFilter() {
        ClientFilter filter = getFilter("Ivan", null, null, null, null, null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getFirstname()).isEqualTo(filter.getFirstName()));
    }

    @Test
    void findAllClientsWithLastnameFilter() {
        ClientFilter filter = getFilter(null, "Ivanov", null, null, null, null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getLastname()).isEqualTo(filter.getLastName()));
    }

    @Test
    void findClientWithEmailFilter() {
        ClientFilter filter = getFilter(null, null, "vanya@gmail.com", null, null, null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getEmail()).isEqualTo(filter.getEmail()));
    }

    @Test
    void findClientWithPhoneFilter() {
        ClientFilter filter = getFilter(null, null, null, "+79034219402", null, null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getPhone()).isEqualTo(filter.getPhone()));
    }

    @Test
    void findAllClientsWithAddressFilter() {
        ClientFilter filter = getFilter(null, null, null, null, "Moscow", null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getAddress()).isEqualTo(filter.getAddress()));
    }

    @Test
    void findAllClientsWithRoleFilter() {
        ClientFilter filter = getFilter(null, null, null, null, null, Role.USER);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        actualResult.forEach(client -> assertThat(client.getRole()).isSameAs(filter.getRole()));
    }

    @Test
    void findNoClientsWithWrongFilter() {
        ClientFilter filter = getFilter(null, "null", null, null, null, null);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findClientsWithSeveralFilters() {
        ClientFilter filter = getFilter("Andrey", "Andreev", "drus@gmail.com",
                "+79033079405", "Vatikan", Role.ADMIN);
        Predicate predicate = getPredicate(filter);
        Iterable<Client> actualResult = clientRepository.findAll(predicate);

        assertThat(actualResult).hasSize(1);
    }

    public Predicate getPredicate(ClientFilter filter) {
        return QPredicate.builder()
                .add(filter.getFirstName(), QClient.client.firstname::eq)
                .add(filter.getLastName(), QClient.client.lastname::eq)
                .add(filter.getEmail(), QClient.client.email::eq)
                .add(filter.getPhone(), QClient.client.phone::eq)
                .add(filter.getAddress(), QClient.client.address::eq)
                .add(filter.getRole(), QClient.client.role::eq)
                .build();
    }

    public ClientFilter getFilter(String firstname, String lastname, String email, String phone, String address, Role role) {
        return ClientFilter.builder()
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .phone(phone)
                .address(address)
                .role(role)
                .build();
    }
}