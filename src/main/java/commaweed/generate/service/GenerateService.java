package commaweed.generate.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateService {

   private static final Logger LOGGER = LoggerFactory.getLogger(GenerateService.class);

   @Value("${defaults.one:not_found}")
   private String one;

   @Value("${defaults.two:not_found}")
   private String two;

   @Value("${defaults.three:not_found}")
   private String three;

   private static String getValue(String value, String property) {
      return (StringUtils.trimToNull(value) == null) ? property : value;
   }

   public String getOne(String value) {
      return "one=" + getValue(value, this.one);
   }

   public String getTwo(String value) {
      return "two=" + getValue(value, this.two);
   }

   public String getThree(String value) {
      return "three=" + getValue(value, this.three);
   }

}
