# Minesweeper

## General info
A simple minesweeper game written in Java + Spring (backend) and Typescript + Angular (frontend).

![gif](readme_files/screen.gif)

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## Technologies
Project is created with:
* Java 11
* Spring Boot
* Typescript
* Angular
* PostgreSQL

## Setup
In order to run project locally you need to clone this repository and build project with Docker Compose:

```
$ git clone https://github.com/xpakx/minesweeper.git
$ cd minesweeper
$ docker-compose up --build -d
```

To stop:
```
$ docker-compose stop
```

## Features
- [x] Game logic
	- [x] Generating board
	- [x] Move logic
	- [x] Flagging fields
- [x] Ranking

