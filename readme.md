# casita actor system


# focus on a completion, demo-able code.
# rahul agarwal : 9945429440


build an actor system to support message passing concurrency.

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

# thread-safety

example -
- log actor(s)          (fire-forget)
- notify actor(s)       ()
- sleepy actor(s)       (to test queue)
- ping pong actor(s)    (communiation)


time check:
- 10:34 am
\- 11:15 am design check 1 (45 mins)
\- 11:35 am design check 2 (55 mins)
\- 11:45 am design check 3 - freeze (70 mins)
\- 12:30 pm complete implementation (45 mins)??
- 12:34 pm test



actor-system
- actor map 
- actor execution contexts
- actor addresses - name:host:port (topology)
- interface
\- create actor 
\- send message to actor
\-  
- not-in-scope: actor supervision 
- not-in-scope: distributed actors 
- not-in-scope: message serialization 
- tests
\- test: creation


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


- actor inbox 
-interface 
    \- inmemory (queue backed size-limit)
    \- db backed (not required)
    \- disk backed (not required)
- tests
\- test: add a message to inbox
\- test: retrieve a message from inbox

actor
- id
- inbox (one actor, one inbox constraint) 
- execution context
- method: isshutdown or open?
- method: receive
- method: shutdown
- tests
\- test: log actor(s): prints to std-out (test side-effect)
\- test: sleepy actor(s): sleep, to allow queue buildup (test queue)
\- test: notify actor(s): make an api call?
\- test: ping pong actor(s): send messages back/ forth (test communication)
\- test: ping pong actor(s): send messages back/ forth, shutdown after 10-times (test communication + shutdown + state)

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
