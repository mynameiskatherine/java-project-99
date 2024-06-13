setup:
	make -C app setup

frontend:
	make -C app frontend

backend:
	make -C app backend

clean:
	make -C app clean

build:
	make -C app build

dev:
	make -C app dev

reload-classes:
	make -C app reload-classes

start-prod:
	make -C app start-prod

install:
	make -C app install

lint:
	make -C app lint

test:
	make -C app test

update-js-deps:
	make -C app update-js-deps

check-java-deps:
	make -C app check-java-deps

.PHONY: build frontend

