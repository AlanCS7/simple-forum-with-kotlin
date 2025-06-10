.PHONY: start-docker stop-docker

start-docker:
	docker compose up -d postgres redis mail-dev

stop-docker:
	docker compose stop postgres redis mail-dev