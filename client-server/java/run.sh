#!/bin/bash

PORT=12345

echo "Starting the server"
java TCPServer $PORT &
SERVER_PID=$!
sleep 1

echo "Starting the client"
java TCPClient 127.0.0.1 $PORT 5
sleep 1
kill $SERVER_PID
