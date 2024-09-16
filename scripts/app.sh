#!/bin/bash

manage=$(find / -name "manage.py" 2>/dev/null | head -1)

if lsof -i:9090 > /dev/null 2>&1; then
    echo "Tirando abajo el servicio que ocula el puerto 9090"
    sleep 0.5
    PID=$(lsof -i:9090 | awk '{print $2}' FS=' ' | tail -1)
    kill -9 ${PID}
    echo "Puerto liberado"
    sleep 0.5
    echo "Ejecutando Makitime"
    sleep 0.5
    python ${manage} runserver 0.0.0.0:9090
else
    echo "El puerto 9090 no está en uso. Ejecuntando Makitime desde ese puerto"
    python ${manage} runserver 0.0.0.0:9090
fi
