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

	
    // a small static rotation around the X axis at the root of the scene graph
    Transform3D t3d = new Transform3D();
    t3d.rotX(Math.PI/12);
	TransformGroup objViewTrans = new TransformGroup(t3d);
    //objRotCamX.setTransform(t3d);
    objRoot.addChild(objViewTrans);
	
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	
	TransformGroup objTransRot = new TransformGroup();
	objTransRot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objViewTrans.addChild(objTransRot);
   
	//  a static translation along the X-axis
	Transform3D t = new Transform3D();
	Vector3d newPos =  new Vector3d(0.6, 0.0, 0.0);
	t.set(newPos);
	TransformGroup trans1 = new TransformGroup(t);
	objTransRot.addChild(trans1);
	
	// followed by an animated rotation around the Y axis
	Transform3D yAxis = new Transform3D();
	Alpha rotor1Alpha = new Alpha(-1, 10000);
	
	RotationInterpolator rotator1 =
	    new RotationInterpolator(rotor1Alpha, objTransRot, yAxis, 0.0f, (float) Math.PI*2.0f);
	rotator1.setSchedulingBounds(bounds);
	
	objTransRot.addChild(rotator1);
	
	// --
	TransformGroup objRot = new TransformGroup();
	objRot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	Transform3D yAxis2 = new Transform3D();
	Alpha rotor2Alpha = new Alpha(-1, 1000);
	
	RotationInterpolator rotator2 =
	    new RotationInterpolator(rotor2Alpha, objRot, yAxis2, 0.0f, (float) Math.PI*2.0f);
	rotator2.setSchedulingBounds(bounds);
	
	trans1.addChild(objRot);
	trans1.addChild(rotator2);
	
	objRot.addChild(new ColorCube(0.2));
	// end of node
	//----------------------------------
	
	
	
	
	
	


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
