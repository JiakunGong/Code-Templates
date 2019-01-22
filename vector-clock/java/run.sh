#!/bin/bash

ID1="node1"
PORT1=12345
ID2="node2"
PORT2=54321
SEND_CNT=3

echo "Starting the nodes"
java Node $ID1 $PORT1 $PORT2 $SEND_CNT &
NODE1=$!
java Node $ID2 $PORT2 $PORT1 $SEND_CNT &
NODE2=$!

wait $NODE1
wait $NODE2
