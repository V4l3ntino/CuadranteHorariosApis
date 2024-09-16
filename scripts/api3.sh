#!/bin/bash

api3=$(find /home/apphumano -name "api3" 2>/dev/null | head -1)

if lsof -i:8003 > /dev/null 2>&1; then
    echo "Tirando abajo el servicio que ocupa el puerto 8003"
    sleep 0.5
    PID=$(lsof -i:8003 | awk '{print $2}' FS=' ' | tail -1)
    kill -9 ${PID}
    echo "Puerto liberado"
    sleep 0.5
    echo "Ejecutando API3"
    sleep 0.5
    cd ${api3}/rest-demo-spring-web; mvn spring-boot:run
else
   echo "Ejecutando API3"
   sleep 0.5
   cd ${api3}/rest-demo-spring-web; mvn spring-boot:run
fi
