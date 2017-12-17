package commaweed.generate.service;

import commaweed.generate.cli.GeneratorOptions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateService {

   private static final Logger LOGGER = LoggerFactory.getLogger(GenerateService.class);

   @Value("${defaults.destination:not_found}")
   private String destination;

   private static String getValue(String value, String property) {
      return (StringUtils.trimToNull(value) == null) ? property : value;
   }

   public void generateRandomFiles(GeneratorOptions options) {
      LOGGER.info("Generating random files for commannd-line options [{}]...", options);

      LOGGER.debug("will be using destination value: {}", getValue(options.getDestination(), this.destination));
   }

}
