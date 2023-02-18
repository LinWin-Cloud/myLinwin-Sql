#! /bin/bash

cd src
find . | grep java > source.txt
javac -d ../out/ @source.txt
cd ../out/
jar -cvfm MyLinwinSql.jar ../release/MANIFEST.MF *
mv MyLinwinSql.jar ../release
cd ../release
java -jar MyLinwinSql.jar
