
# Avro Random File Generator Sample

### to download avro-tools jar to local repo

mvn dependency:get -Dartifact=org.apache.avro:avro-tools:1.8.1

### to use avro-tools to convert avro file to json

java -jar avro-tools-1.8.1.jar tojson local/test.avro > test.json
 