set MEMORY=-Xms128m -Xmx128m

set GC=-XX:+UseSerialGC
java %MEMORY% %GC% -jar target/KVAHW004.jar

set GC=-XX:+UseParallelGC
java %MEMORY% %GC% -jar target/KVAHW004.jar

set GC=-XX:+UseConcMarkSweepGC
java %MEMORY% %GC% -jar target/KVAHW004.jar

set GC=-XX:+UseG1GC
java %MEMORY% %GC% -jar target/KVAHW004.jar
