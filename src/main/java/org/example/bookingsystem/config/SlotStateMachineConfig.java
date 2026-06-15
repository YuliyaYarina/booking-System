package org.example.bookingsystem.config;

import org.example.bookingsystem.model.SlotEvent;
import org.example.bookingsystem.model.SlotState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class SlotStateMachineConfig extends StateMachineConfigurerAdapter<SlotState, SlotEvent> {

    @Override
    public void configure(
            StateMachineStateConfigurer<SlotState, SlotEvent> states
    ) throws Exception {

        states
                .withStates()
                .initial(SlotState.AVAILABLE)
                .state(SlotState.BOOKED)
                .state(SlotState.CANCELLED)
                .end(SlotState.CLOSED);
    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<SlotState, SlotEvent> transitions
    ) throws Exception {

        transitions

                .withExternal()
                .source(SlotState.AVAILABLE)
                .target(SlotState.BOOKED)
                .event(SlotEvent.BOOK)
                .action(printNewBooked())

                .and()

                .withExternal()
                .source(SlotState.BOOKED)
                .target(SlotState.CANCELLED)
                .event(SlotEvent.CANCEL)

                .and()

                .withExternal()
                .source(SlotState.BOOKED)
                .target(SlotState.CLOSED)
                .event(SlotEvent.CLOSE);
    }

    private Action<SlotState, SlotEvent> printNewBooked() {
        System.out.println("новое бронирование");
        return null;
    }
}
