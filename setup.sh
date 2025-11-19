#! /bin/bash

# Don't run it from root, if you gonna use it from user!


# Made this file executable:
# chmod +x setup.sh


# Break when error occurs
set -e


# APT update
update="apt update"
echo "$update"
su -c "$update"
echo "Package list updated"

# APT install
install="apt install -y python3 python3-pip python3-venv"
echo "$install"
su -c "$install"
echo "Python, pip and venv installed"
echo "---
Now, after those you probably would like to run:
    apt upgrade -y
    apt autoremove -y
---"


# Python VENV
python3 -m venv .venv
echo "Python .venv created"
# "." command is equivalent to "source" command
. .venv/bin/activate
echo "Python .venv activated"

# Python VENV requirements.txt
if [ ! -f requirements.txt ]; then
    # All libraries should be listed in command, but file requirements.txt will be provided source of truth
    pip install fastapi "fastapi[standard]"
    echo "FastAPI installed"
    pip freeze > requirements.txt
    echo "Python freeze libraries"
else
    pip install -r requirements.txt
    echo "Python pip installed from requirements.txt"
fi


# ENV
if [ ! -f .env ]; then
    touch .env
    # about "quotations", we can use lines and tabs with them
    TEXT="File .env created"
    echo "$TEXT"
else
    echo "File .env already exists"
fi
