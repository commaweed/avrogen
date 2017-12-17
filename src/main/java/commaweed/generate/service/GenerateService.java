package commaweed.generate.service;

import commaweed.avro.CommaweedChunkContent;
import commaweed.avro.CommaweedFile;
import commaweed.avro.CommaweedFileChunk;
import commaweed.avro.CommaweedSystem;
import commaweed.generate.cli.GeneratorOptions;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GenerateService {

   private static final Logger LOGGER = LoggerFactory.getLogger(GenerateService.class);

   @Autowired
   private PayloadGeneratorService payloadService;

   @Value("${defaults.destination:not_found}")
   private String destination;

   private static String getValue(String value, String property) {
      return (StringUtils.trimToNull(value) == null) ? property : value;
   }

   private CommaweedFileChunk generateRandomChunk() {
      CommaweedFileChunk chunk = new CommaweedFileChunk();

      chunk.setChunkType("primary");
      chunk.setPayload(Collections.singletonList(
         new CommaweedChunkContent(
            payloadService.convertToByteBuffer(
               this.payloadService.createRandomDocument(100, 100, 100)
            )
         )
      ));

      return chunk;
   }

   private static enum FileParameter {
      checksum,
      fileType,
      someOther
   }

   private static enum FileType {
      text,
      html,
      garbage;

      private static Random randomizer = new Random();

      private static FileType getRandom() {
         int random = randomizer.nextInt(3);

         FileType result;
         switch (random) {
            case 0:
               result = text;
               break;
            case 1:
               result = html;
               break;
            case 2:
               result = garbage;
               break;
            default:
               throw new IllegalArgumentException("Invalid random [" + random + "]; it cannot exceed [" + FileType.values().length + "]");

         }

         return result;
      }
   }

   private CommaweedFile generateRandomFile() {
      CommaweedFile file = new CommaweedFile();

      Map<CharSequence,CharSequence> parameters = new HashMap<>();
      for (FileParameter parameter : FileParameter.values()) {
         if (parameter == FileParameter.fileType) {
            parameters.put(parameter.toString(), FileType.getRandom().toString());
         } else {
            parameters.put(parameter.toString(), this.payloadService.createRandomWord(20));
         }
      }

      file.setFileId(this.payloadService.createRandomWord(20));
      file.setChunks(Collections.singletonList(generateRandomChunk()));
      file.setParameters(parameters);

      return file;
   }

   private CommaweedSystem generateRandomSystem() {
      CommaweedSystem system = new CommaweedSystem();

      system.setCommaweedId(this.payloadService.createRandomWord(20));
      system.setFiles(Collections.singletonList(generateRandomFile()));
      system.setCommaweedHeader(this.payloadService.createRandomParagraph(20, 20));

      return system;
   }

   private Path getDestinationDirectory(GeneratorOptions options) {
      String destinationPath = getValue(options.getDestination(), this.destination);

      Path path = Paths.get(destinationPath);

      // create the directory if it doesn't exit
      try {
         Files.createDirectories(path);
      } catch (IOException e) {
         LOGGER.error("Unable to create destination directory for path [{}]!", destinationPath, e);
      }

      return path;
   }

   public void generateRandomFiles(GeneratorOptions options) {
      LOGGER.info("Generating random files for commannd-line options [{}]...", options);

      DatumWriter<CommaweedSystem> writer = new SpecificDatumWriter<CommaweedSystem>(CommaweedSystem.class);

      try (DataFileWriter<CommaweedSystem> dataFileWriter = new DataFileWriter<CommaweedSystem>(writer)) {
         Path path = getDestinationDirectory(options).resolve("test.avro");
         LOGGER.info("Writing test file to [{}]...", path.getFileName());

         CommaweedSystem testSystem = generateRandomSystem();
         dataFileWriter.create(testSystem.getSchema(), path.toFile());
         dataFileWriter.append(testSystem);
         dataFileWriter.append(generateRandomSystem());
      } catch (Exception e) {
         LOGGER.error("Unable to write random avro file", e);
      }

   }

}
