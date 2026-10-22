install:
	cd app && gradlew.bat installDist

run-dist:
	app\build\install\app\bin\app.bat $(ARGS)