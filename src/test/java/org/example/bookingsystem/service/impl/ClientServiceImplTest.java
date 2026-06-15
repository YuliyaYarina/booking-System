package org.example.bookingsystem.service.impl;

import org.example.bookingsystem.exceptions.UserAlreadyExistsException;
import org.example.bookingsystem.exceptions.UserNotFoundException;
import org.example.bookingsystem.model.Client;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void shouldReturnCurrentClient() {

        // given
        Client client = new Client();
        client.setUsername("alex");

        Authentication authentication =
                mock(Authentication.class);

        when(authentication.getName())
                .thenReturn("alex");

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        // when
        when(clientRepository.findByUsername("alex"))
                .thenReturn(Optional.of(client));

        Client result =
                clientService.getCurrentClient();

        // then
        assertThat(result).isEqualTo(client);
    }

    @Test
    void shouldThrowExceptionWhenWorkerNotFound() {

        // given
        Authentication authentication =
                mock(Authentication.class);

        when(authentication.getName())
                .thenReturn("alex");

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        // when
        when(clientRepository.findByUsername("alex"))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(
                () -> clientService.getCurrentClient())
                .isInstanceOf(
                        UserNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationMissing() {

        // given
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(null);

        SecurityContextHolder.setContext(context);

        // then
        assertThatThrownBy(
                () -> clientService.getCurrentClient())
                .isInstanceOf(
                        AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    void shouldSaveClientSuccessfully() {

        // given
        Client client = new Client();
        client.setUsername("alex");

        when(clientRepository.save(client))
                .thenReturn(client);

        // when
        Client result = clientService.save(client);

        // then
        assertThat(result).isEqualTo(client);

        verify(clientRepository).save(client);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void shouldThrowWorkerAlreadyExistsExceptionWhenUsernameIsNotUnique() {

        // given
        Client client = new Client();
        client.setUsername("alex");

        when(clientRepository.save(client))
                .thenThrow(DataIntegrityViolationException.class);

        // when / then
        UserAlreadyExistsException exception =
                assertThrows(
                        UserAlreadyExistsException.class,
                        () -> clientService.save(client)
                );

        assertThat(exception.getMessage())
                .contains("alex");

        verify(clientRepository).save(client);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void shouldThrowExceptionWhenWorkerIsNull() {

        assertThrows(
                NullPointerException.class,
                () -> clientService.save(null)
        );

        verifyNoInteractions(clientRepository);
    }

    @Test
    void findBookableWorkers() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByUsername() {
    }
}