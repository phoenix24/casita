### a toy actor system

todo:
- fix inbox interface (+ add more inboxes)
- fix message interface
- fix execution context interface
- add support for remote actors (+message serialization)
- fix actor address + topology
- fix actor supervision

- message inbox; durability (fifo ordering, finite size); how to handle full inboxes -> inmemory queue?
- system bootstrap -> create actors are fixed
- fair scheduling -> actor scheduling; time-sharing ??

- failure scenarios for actors? supervision -> to be done later?
- actor identifier + actor address

- actor lifecycle??
- can be shutdown -> complete processing the currency message or crash; resumption?

- interface shutdown
- interface receive-message

- actor state - thereby change in state?
- actor communication via message/ address on the same jvm -> YES?

- actor message
- interface
    \- user message
    \- system message
        \- actor-lifecycle message (not in scope)
        \- actor-tasks (actor reference + message reference)
- immutable (not sure| not immutable at the moment)?
- tests
\- test: ability to send to an address  
\- test: ability to send to self-address 

actor
- id
- inbox (one actor, one inbox constraint) 
- execution context
- method: isshutdown or open?
- method: receive
- method: shutdown

- actor execution context
\- thread-pools
\- todo: think more???
\- todo: time-sharing think more??

- actor-task
\- actor
\- message
\- sent a queue

- actor message routing
\- no router; direct communication via address

- actor message flow
\- send message to actor from outside actor-system
\- actor system 1receives the message
\- actor system - actor locate (throw if invalid actor)
\- actor system - actor check if accepting (throw if inbox closed or full or in shutdown)
\- actor system - create actor-task message and enqueue into the execution context
\- send message to actor from an actor within actor-system  
