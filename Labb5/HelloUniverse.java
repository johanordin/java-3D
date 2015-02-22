/*
 *	@(#)HelloUniverse.java 1.52 01/09/28 17:30:21
 *
 * Copyright (c) 1996-2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Minor modifications by Stefan Gustavson, ITN-LiTH (stegu@itn.liu.se)
 *
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class HelloUniverse extends Applet {

    private SimpleUniverse u = null;
    
    public BranchGroup createSceneGraph() {
      // Create the root of the branch graph
      BranchGroup objRoot = new BranchGroup();

      // Create the TransformGroup node and initialize it to the
      // identity. Enable the TRANSFORM_WRITE capability so that
      // our behavior code can modify it at run time. Add it to
      // the root of the subgraph.
      TransformGroup objRotate = new TransformGroup();
      objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      objRoot.addChild(objRotate);

      // Create a simple Shape3D node; add it to the scene graph.
      objRotate.addChild(new ColorCube(0.4));

      // Create a new Behavior object that will perform the
      // desired operation on the specified transform and add
      // it into the scene graph.
      Alpha rotationAlpha = new Alpha(-1, 4000);
      RotationInterpolator rotator =
        new RotationInterpolator(rotationAlpha, objRotate);
      BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
      rotator.setSchedulingBounds(bounds);
      objRoot.addChild(rotator);

      // Have Java 3D perform optimizations on this scene graph.
      objRoot.compile();

      return objRoot;
    }

    public HelloUniverse() {
    }

    public void init() {
      setLayout(new BorderLayout());
      GraphicsConfiguration config =
        SimpleUniverse.getPreferredConfiguration();

      Canvas3D c = new Canvas3D(config);
      add("Center", c);

      // Create a simple scene and attach it to the virtual universe
      BranchGroup scene = createSceneGraph();
      u = new SimpleUniverse(c);

      // This will move the ViewPlatform back a bit so the
      // objects in the scene can be viewed.
      u.getViewingPlatform().setNominalViewingTransform();

      u.addBranchGraph(scene);
    }

    public void destroy() {
      u.removeAllLocales();
    }

    //
    // The following allows HelloUniverse to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
      new MainFrame(new HelloUniverse(), 256, 256);
    }
}
