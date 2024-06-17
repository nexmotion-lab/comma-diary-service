FROM ubuntu:latest
LABEL authors="sunwo"

ENTRYPOINT ["top", "-b"]