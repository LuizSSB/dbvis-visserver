export JAVA_HOME=/usr/lib/jvm/java-7-oracle/
rm -rf dist/*
ant -buildfile build-jar.xml jar
mkdir dist/lib
cp lib/* dist/lib
cp lib/jung/* dist/lib
cp lib/resteasy/* dist/lib
echo "java -cp dbvis.jar:lib/* br.unesp.amoraes.dbvis.App" > dist/run.sh
echo "java -cp dbvis.jar:lib/* br.unesp.amoraes.dbvis.App" > dist/run.bat
echo "java -cp dbvis.jar:lib/* br.unesp.amoraes.dbvis.App install" > dist/installAndRun.sh
echo "java -cp dbvis.jar:lib/* br.unesp.amoraes.dbvis.App install" > dist/installAndRun.bat
