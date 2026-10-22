.PHONY: install run-dist build

install:
	cd app && gradlew.bat installDist

run-dist:
	app\build\install\app\bin\app.bat $(ARGS)

build:
	cd app && gradlew.bat build