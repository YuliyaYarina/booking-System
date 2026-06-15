package org.example.bookingsystem.service.impl;

import org.example.bookingsystem.exceptions.UserAlreadyExistsException;
import org.example.bookingsystem.exceptions.UserNotFoundException;
import org.example.bookingsystem.model.User;
import org.example.bookingsystem.model.Worker;
import org.example.bookingsystem.repository.WorkerRepository;
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

@ExtendWith(MockitoExtension.class)
class WorkerServiceImplTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerServiceImpl workerService;

    @Test
    void shouldReturnCurrentWorker() {

        // given
        Worker worker = new Worker();
        worker.setUsername("alex");

        Authentication authentication =
                mock(Authentication.class);

        when(authentication.getName())
                .thenReturn("alex");

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        // when
        when(workerRepository.findByUsername("alex"))
                .thenReturn(Optional.of(worker));

        User result =
                workerService.getCurrentWorker();

        // then
        assertThat(result).isEqualTo(worker);
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
        when(workerRepository.findByUsername("alex"))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(
                () -> workerService.getCurrentWorker())
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
                () -> workerService.getCurrentWorker())
                .isInstanceOf(
                        AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    void shouldSaveWorkerSuccessfully() {

        // given
        Worker worker = new Worker();
        worker.setUsername("alex");

        when(workerRepository.save(worker))
                .thenReturn(worker);

        // when
        User result = workerService.save(worker);

        // then
        assertThat(result).isEqualTo(worker);

        verify(workerRepository).save(worker);
        verifyNoMoreInteractions(workerRepository);
    }

    @Test
    void shouldThrowWorkerAlreadyExistsExceptionWhenUsernameIsNotUnique() {

        // given
        Worker worker = new Worker();
        worker.setUsername("alex");

        when(workerRepository.save(worker))
                .thenThrow(DataIntegrityViolationException.class);

        // when / then
        UserAlreadyExistsException exception =
                assertThrows(
                        UserAlreadyExistsException.class,
                        () -> workerService.save(worker)
                );

        assertThat(exception.getMessage())
                .contains("alex");

        verify(workerRepository).save(worker);
        verifyNoMoreInteractions(workerRepository);
    }

    @Test
    void shouldThrowExceptionWhenWorkerIsNull() {

        assertThrows(
                NullPointerException.class,
                () -> workerService.save(null)
        );

        verifyNoInteractions(workerRepository);
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