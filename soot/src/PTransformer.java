import java.util.*;

import soot.ArrayType;
import soot.Body;
import soot.IntType;
import soot.jimple.*;
import soot.jimple.internal.JAssignStmt;
import soot.Local;
import soot.RefType;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.*;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.ValueBox;

/**
 * This is the main class of your instrumentation logic. If you use wjtp, this
 * class should extend {@link soot.SceneTransformer}.
 */
public class PTransformer extends SceneTransformer {
  @Override
  protected void internalTransform(String phaseName, Map options) {
    System.out.println("[INFO] Running PTransformer.");

    // You can get all the classes from Scene.
    // For example, the following code iterates over all the classes you passed
    // in (via the -process-dir argument in InstrumentationRunner.java) and
    // prints their names.
    for (SootClass cls : Scene.v().getApplicationClasses()) {
      System.out.println("[INFO] Found class: " + cls.toString());
    }

    // You can also get a specific class from Scene using the full name of the
    // class, i.e., package.ClassName.
    SootClass cls = Scene.v().getSootClass("foo.Test");
    // You can iterates over all the methods of a class using SootClass' API.
    for (SootMethod method : cls.getMethods()) {
      System.out.println("[INFO] foo.Test has method: " + method.toString());
    }

    // Take a look at the javadoc of Scene, SootClass, SootMethod, SootField to
    // see which methods you can use. An example website is as follows:
    // https://www.sable.mcgill.ca/soot/doc/soot/SootClass.html
  }
}
