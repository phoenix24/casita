### a toy actor system

![casita](https://github.com/phoenix24/casita/workflows/build/badge.svg)

todo:
- fix inbox interface (+ add more inboxes)
- fix message interface
- fix execution context interface
- add support for remote actors (+message serialization +referentially-transparent)
- fix actor address + topology
- fix actor supervision (+restart +recovery)
- fix actor lifecycle??

- actor message (immutable)
    \- user message
    \- system message
        \- actor-lifecycle message (not in scope)
        \- actor-tasks (actor reference + message reference)
- tests
\- test: ability to send to an address  
\- test: ability to send to self-address 

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
