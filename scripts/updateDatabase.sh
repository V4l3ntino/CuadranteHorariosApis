#!/bin/bash
updateScript=$(find /home/apphumano -name "updateDatebase.py" 2>/dev/null | head -1 )

python ${updateScript} > logsUpdateDatabase
