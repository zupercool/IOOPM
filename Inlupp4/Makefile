BIN_PATH=./bin/
SRC_PATH=.

FLAGS_PROD=-sourcepath $(SRC_PATH) -d $(BIN_PATH)
FLAGS_DEBUG=$(FLAGS_PROD) -g

#normal
# INTENSITY=50
# MAX_GROC=10
# REG_TRESH=5
# NO_REGS=10

# extreme
# INTENSITY=100
# MAX_GROC=25
# REG_TRESH=1
# NO_REGS=5

all: kassako
	@cd $(BIN_PATH); \
	java Simulator

kassako:
	javac $(FLAGS_PROD) *.java

doc: doc/ .PHONY
	@echo "[MAKE] Documenting kassako..."
	@javadoc -d doc/ *.java

test: CustomerTest.java
	javac $(FLAGS_DEBUG) $^
	@cd $(BIN_PATH); \
	java -ea CustomerTest

clean:
	rm -rf *.class
	rm -rf *.java~
	rm -rf .DS_Store
	rm -rf ./bin/*.class
	rm -rf ./bin/.DS_Store

.PHONY:
