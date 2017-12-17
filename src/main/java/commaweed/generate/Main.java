package commaweed.generate;

import commaweed.generate.cli.GeneratorOptions;
import commaweed.generate.service.GenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class Main implements CommandLineRunner {

   private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

   @Autowired
   private GenerateService genService;

   public static void main(String... args) {
      SpringApplication app = new SpringApplication(Main.class);
      app.setBannerMode(Banner.Mode.OFF);
      app.run(args);
   }

   @Override
   public void run(String... args) {
      LOGGER.info("Initializing random avro generator...");
      GeneratorOptions options = CommandLine.populateCommand(new GeneratorOptions(), args);

      if (options.isHelpRequested()) {
         CommandLine.usage(options, System.err);
         System.exit(0);
      }

      genService.generateRandomFiles(options);
   }
}
