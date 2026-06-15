package org.example.bookingsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.model.Slot;
import org.example.bookingsystem.model.SlotEvent;
import org.example.bookingsystem.model.SlotState;
import org.example.bookingsystem.repository.SlotRepository;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository repository;

    private final StateMachine<SlotState, SlotEvent> stateMachine;

    @Transactional
    public void book(Long slotId) {

        Slot slot = repository.findById(slotId)
                .orElseThrow();

        stateMachine.start();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> {

                    access.resetStateMachine(
                            new DefaultStateMachineContext<>(
                                    slot.getState(),
                                    null,
                                    null,
                                    null
                            )
                    );
                });

        boolean accepted =
                stateMachine.sendEvent(SlotEvent.BOOK);

        if (!accepted) {
            throw new IllegalStateException("Transition denied");
        }

        slot.setState(stateMachine.getState().getId());

        repository.save(slot);
    }
}
