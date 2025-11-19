#! /bin/bash

# Made this file executable:
# chmod +x run.sh

. .venv/bin/activate
# fastapi run main.py
export $(grep -v '^#' .env | xargs)
fastapi run main.py --host "$HOST" --port "$PORT"
