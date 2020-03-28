rm -rf build/libs
rm -rf release
./gradlew jar
mkdir -p release
cp build/libs/*.jar release/
