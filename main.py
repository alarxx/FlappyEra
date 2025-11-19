import os

# --- Supabase ---

from supabase import create_client, Client

SUPABASE_URL = os.environ.get("SUPABASE_URL")
SUPABASE_KEY = os.environ.get("SUPABASE_KEY")

# supabaseDB variable is of type Client
supabaseDB: Client = create_client(SUPABASE_URL, SUPABASE_KEY)

# --- FastAPI ---

from fastapi import FastAPI
from fastapi import HTTPException
from fastapi.responses import FileResponse
from fastapi.staticfiles import StaticFiles
from fastapi import Body
# Content-Type:
#   application/json
from fastapi import Request
# Content-Type:
#   application/x-www-form-urlencoded
#   multipart/form-data


app = FastAPI()


# --- GET /static ---

app.mount("/static", StaticFiles(directory="static"), name="static")


# --- GET / ---

@app.get("/", response_class=FileResponse)
async def read_index():
    return FileResponse("flappy.html")

@app.get("/score", response_class=FileResponse)
async def read_index():
    return FileResponse("score.html")

# --- ---


# --- POST /api/score ---

@app.post("/api/score")
async def create_score(request: Request):
    """
    {
        "nickname": "Name",
        "score": 123
    }
    """
    try:
        data = await get_request_data(request)

        nickname = data.get("nickname")
        score = data.get("score")

        nickname = str(nickname).strip()
        if not nickname: # empty string
            raise HTTPException(400, "Incorrect 'nickname' field")

        try:
            score = int(score)
        except Exception as e:
            raise HTTPException(status_code=400, detail=f"Incorrect 'score' field: {e}")
        if score < 0:
            raise HTTPException(status_code=400, detail="Incorrect 'score' field, must be >= 0")

        try:
            existing = (supabaseDB
                .table("score")
                .select("*")
                .eq("nickname", nickname)
                .eq("score", score)
                .execute())
        except Exception as e:
            print("Supabase error:", repr(e))
            raise HTTPException(status_code=500, detail="(POST '/api/score') Database select error")

        if existing.data:
            raise HTTPException(status_code=400, detail="Record with this nickname and score already exists")

        record = { "nickname": nickname, "score": score }

        try:
            result = supabaseDB.table("score").insert(record).execute()
        except Exception as e:
            print("Supabase error:", repr(e))
            raise HTTPException(status_code=500, detail="(POST '/api/score') Database insert error")

        if not result.data:
            raise HTTPException(status_code=500, detail="insert returned no data")

        return { "data": result.data }
    except Exception as e:
        print("POST '/api/score' Error: ", repr(e))
        raise


async def get_request_data(request: Request):
    # 1) form-data
    try:
        form = await request.form()
        form_dict = dict(form)
        if form_dict:
            return form_dict
    except Exception as e:
        pass  # try JSON

    # 2) JSON
    try:
        json_data = await request.json()
        if isinstance(json_data, dict):
            return json_data
    except Exception as e:
        pass

    # Nothing has worked
    raise HTTPException(status_code=400, detail="Invalid request body")

# --- ---


# --- GET /api/score ---

@app.get("/api/score")
async def get_scores(
    page: int = 1,
    page_size: int = 100,
):
    """
    Pagination:
      GET /api/scores?page=1&page_size=10
    """
    try:
        if page < 1:
            raise HTTPException(status_code=400, detail="'page' must be >= 1")

        if page_size < 1 or page_size > 100:
            raise HTTPException(status_code=400, detail="'page_size' must be between 1 and 100")

        start = (page - 1) * page_size # 0, 10, 20...
        end = start + page_size - 1 # 10 items: [0; 9], [10, 19]...

        try:
            result = (supabaseDB.table("score")
                .select("*", count="exact")
                .order("score", desc=True) # [1, 2_2025, 2_2024, 3]
                .order("created_at", desc=False) # [1, 2_2024, 2_2025, 3]
                .range(start, end)
                .execute())
        except Exception as e:
            print("Supabase error:", repr(e))
            raise HTTPException(status_code=500, detail="(GET '/api/score') Database select error")

        return {
            "page": page,
            "page_size": page_size,
            "total": result.count,
            "data": result.data,
        }
    except Exception as e:
        print("GET '/api/score' Error:", repr(e))
        raise

# --- ---
