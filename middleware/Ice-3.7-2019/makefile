all:
	slice2java --output-dir generated slice/bank.ice
	protoc -I=. --java_out=generated --plugin=protoc-gen-grpc-java=/home/marina/protoc-gen-grpc-java-1.2.0-linux-x86_64.exe --grpc-java_out=generated currency.proto

