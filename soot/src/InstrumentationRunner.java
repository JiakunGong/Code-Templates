import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import soot.PackManager;
import soot.Transform;

public class InstrumentationRunner {
  private static String[] cmdLineArgs = {
          // This option specifies the java files to be analyzed.
          "-process-dir", "<directory-containing-files-you-want-to-analyze>",

          // This option enables the -wxxx phases, e.g., wjtp.
          "-W",

          // This option keeps the variable name unchanged w/ best effort.
          "-p", "jb", "use-original-names:true",

          // The following options disable unused phases to boost performance.
          //"-p", "cg", "enabled:false",
          //"-p", "tag", "enabled:false",
          //"-p", "bb", "enabled:false",
          //"-p", "jap", "enabled:false",
          "-p", "jop", "enabled:false",
          //"-p", "jtp", "enabled:false",
          //"-p", "wjap", "enabled:false",
          "-p", "wjop", "enabled:false",

          //"-pp",

          // This option outputs jimple for debugging.
          "-f", "j",
          //"-f", "c",

          // Sets the output directory
          "-d", "./sootOutput",

          // This option prints more debugging information.
          "-validate",

          // This option handles missing files.
          "-allow-phantom-refs"};

  /**
   * Checks that InstrumentationRunner is invoked correctly.
   * @param argsNr - Number of arguments.
   */
  private static void sanityCheck(int argsNr) {
    if (argsNr != 1) {
      System.err.println("usage: java InstrumentationRunner <dir>");
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    sanityCheck(args.length);

    // Initialize the command line arguments for soot.
    // cmdLineArgs[1] is the directory containing the Java files to be analyzed.
    cmdLineArgs[1] = args[0];
    List<String> argsList = new ArrayList<String>();
    argsList.addAll(Arrays.asList(cmdLineArgs));

    // Inject a transformation into "Whole Jimple Transformation Pack" to 
    // identify the classes and variables that we need to instrument.
    PackManager.v().getPack("wjtp")
      .add(new Transform("wjtp.parisTransform", new PTransformer()));

    // Run soot.
    args = argsList.toArray(new String[0]);
    soot.Main.main(args);
  }
}
