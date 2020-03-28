rm -rf build/libs
rm -rf release
./gradlew shadowJar
mkdir -p release
cp build/libs/*.jar release/
