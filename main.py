
# --- API ---
from fastapi import FastAPI, HTTPException
from fastapi.responses import FileResponse

app = FastAPI()

@app.get("/", response_class=FileResponse)
async def read_index():
    return FileResponse("click.html")

# --- PING ---

import time
import urllib.request
import asyncio

def do_request():
    url = "https://flappy.scalar.kz/click"
    with urllib.request.urlopen(url) as response:
        status = response.status
        data = response.read().decode("utf-8")
        print(f"[OK] {status}: {data[:60]}...")

async def ping_api_every_minute():
    while True:
        try:
            await asyncio.to_thread(do_request)
        except Exception as e:
            print(f"[ERROR] {e}")
        await asyncio.sleep(2 * 60)

# Startup event
@app.on_event("startup")
async def start_background_tasks():
    asyncio.create_task(ping_api_every_minute())




