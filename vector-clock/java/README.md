# Vector Clock in Java

A simple vector clock implemented in Java.
The code is implemented as a standalone node.
The node starts a server thread and several client threads.
The server thread reads incoming requests,
deserializes vector clocks from them,
and merges the received vector clock with the local one.
The client thread serializes the local vector clock
and sends it out to another node.
The local vector clock is incremented before being serialized for sending,
and before deserializing the received data.

The code can be compiled by running the following command:

```bash
make
```

The compiled files can be cleaned by running the following command:

```bash
make clean
```

The code can be with the following command:

```bash
./run.sh
```

This command will start two nodes.
Each node sends three messages (serialized vector clock values) to the other.