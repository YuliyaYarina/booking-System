### State Machine Architecture

Implemented a finite-state machine (FSM) for slot lifecycle orchestration using Spring State Machine.

The system manages:
- state transitions
- lifecycle validation
- business rules
- side effects execution

Transition flow example:

AVAILABLE + BOOK → BOOKED

Key concepts:
- event-driven architecture
- Guards for transition validation
- Actions for post-transition processing
- persistent entity state in database
