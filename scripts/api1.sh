#!/bin/bash

api1=$(find /home/apphumano -name "api1" 2>/dev/null | head -1)

if lsof -i:8001 > /dev/null 2>&1; then
    echo "Tirando abajo el servicio que ocupa el puerto 8001"
    sleep 0.5
    PID=$(lsof -i:8001 | awk '{print $2}' FS=' ' | tail -1)
    kill -9 ${PID}
    echo "Puerto liberado"
    sleep 0.5
    echo "Ejecutando API1"
    sleep 0.5
    cd ${api1}/rest-demo-spring-web; mvn spring-boot:run
else
   echo "Ejecutando API1"
   sleep 0.5
   cd ${api1}/rest-demo-spring-web; mvn spring-boot:run
fi
