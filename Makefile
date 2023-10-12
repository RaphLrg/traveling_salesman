all: run

run: ./classes/Main.class
	java -classpath ./classes Main

./classes/Main.class:
	javac -classpath ./classes -sourcepath ./src -d ./classes src/Main.java

compile:
	javac -classpath ./classes -sourcepath ./src -d ./classes src/Main.java

clean:
	rm -rf ./classes/*