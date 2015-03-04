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
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

public class HelloUniverse extends Applet {

    private SimpleUniverse u = null;
    
    public BranchGroup createSceneGraph() {
    	
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();
	
	Transform3D t;
	
    // Create a Transformgroup to scale all objects so they
    // appear in the scene.
    TransformGroup objScale = new TransformGroup();
    Transform3D t3d = new Transform3D();
    t3d.rotX(Math.PI/12);
    objScale.setTransform(t3d);
    objRoot.addChild(objScale);
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	  
	//objRoot.addChild(new ColorCube(0.4));
	  
	TransformGroup objRotate = new TransformGroup();
	objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objScale.addChild(objRotate);
    
	//  a static translation along the X-axis
	t = new Transform3D();
	Vector3d lPos1 =  new Vector3d(0.8, 0.0, 0.0);
	t.set(lPos1);
	TransformGroup l1Trans = new TransformGroup(t);
	objRotate.addChild(l1Trans);
	
	l1Trans.addChild(new ColorCube(0.2));
	
	// followed by an animated rotation around the Y axis
	Transform3D yAxis = new Transform3D();
	Alpha rotor1Alpha = new Alpha(-1, 4000);
	
	RotationInterpolator rotator1 =
	    new RotationInterpolator(rotor1Alpha, objRotate, yAxis, 0.0f, (float) Math.PI*2.0f);
	rotator1.setSchedulingBounds(bounds);
	
	objRotate.addChild(rotator1);
	

	 // Have Java 3D perform optimizations on this scene graph.
	 objRoot.compile();
	 
	 
	 /*
	  * Slow
		Alpha rotor1Alpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
	     0, 0,
	     4000, 0, 0,
	     0, 0, 0);
	  */

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
      new MainFrame(new HelloUniverse(), 500, 500);
    }
}
