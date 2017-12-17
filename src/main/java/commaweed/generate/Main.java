package commaweed.generate;

import commaweed.generate.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Main implements CommandLineRunner {

   @Autowired
   private GenerateService genService;

   public static void main(String... args) {
      SpringApplication app = new SpringApplication(Main.class);
      app.setBannerMode(Banner.Mode.OFF);
      app.run(args);
   }

   @Override
   public void run(String... args) {
      System.out.println("Start generating... " + Arrays.toString(args));

      String one = null;
      String two = null;
      String three = null;

      if (args.length > 0) one = args[0];
      if (args.length > 1) two = args[1];
      if (args.length > 2) three = args[2];


      System.out.println("1: " + genService.getOne(one));
      System.out.println("1: " + genService.getTwo(two));
      System.out.println("1: " + genService.getThree(three));

      System.exit(0);
   }
}
