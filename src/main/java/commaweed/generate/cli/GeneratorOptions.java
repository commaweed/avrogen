package commaweed.generate.cli;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static picocli.CommandLine.Option;

public class GeneratorOptions {

   @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
   private boolean helpRequested = false;

   @Option(names = { "-d", "--destination" }, paramLabel = "DEST_DIRECTORY", description = "the destination directory")
   private String destination;

   public boolean isHelpRequested() {
      return helpRequested;
   }

   public String getDestination() {
      return destination;
   }

   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
   }
}
