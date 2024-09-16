#!/bin/bash

api2=$(find /home/apphumano -name "api2" 2>/dev/null | head -1)

if lsof -i:8002 > /dev/null 2>&1; then
    echo "Tirando abajo el servicio que ocupa el puerto 8002"
    sleep 0.5
    PID=$(lsof -i:8002 | awk '{print $2}' FS=' ' | tail -1)
    kill -9 ${PID}
    echo "Puerto liberado"
    sleep 0.5
    echo "Ejecutando API2"
    sleep 0.5
    cd ${api2}/rest-demo-spring-web; mvn spring-boot:run
else
   echo "Ejecutando API2"
   sleep 0.5
   cd ${api2}/rest-demo-spring-web; mvn spring-boot:run
fi
