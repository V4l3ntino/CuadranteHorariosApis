#!/bin/bash

api1=$(find /home/apphumano -name "api1.sh" 2>/dev/null | head -1)
api2=$(find /home/apphumano -name "api2.sh" 2>/dev/null | head -1)

bash ${api1} > logApi1 & disown

sleep 20

bash ${api2} > logApi2 & disown
