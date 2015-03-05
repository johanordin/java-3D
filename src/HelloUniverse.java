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

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.image.TextureLoader;

public class HelloUniverse extends Applet {

    private SimpleUniverse u = null;
    
    public BranchGroup createSceneGraph() {
    	
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();	

    // a small static rotation around the X axis at the root of the scene graph
    Transform3D t3d = new Transform3D();
    t3d.rotX(Math.PI/12);
	TransformGroup objViewTrans = new TransformGroup(t3d);


	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	

	//----------------------------------
	// SUN ?

	
	// Create and load a texture object, and associate it with an appearance
	Appearance app = new Appearance();
	Texture tex = new TextureLoader("sun.png", this).getTexture();
	app.setTexture(tex);
	// The three lines below are really only needed if lighting is applied
	TextureAttributes texAttr = new TextureAttributes();
	texAttr.setTextureMode(TextureAttributes.MODULATE);
	app.setTextureAttributes(texAttr);
	

	TransformGroup objRotSun = new TransformGroup();
	objRotSun.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	// Planet orbit - followed by an animated rotation around the Y axis
	Alpha alphaSun = new Alpha(-1, 3000);	
	RotationInterpolator rotatorSun =
	    new RotationInterpolator(alphaSun, objRotSun);
	rotatorSun.setSchedulingBounds(bounds);
	

	
	//----------------------------------
	// EARTH
	
	// Create and load a texture object, and associate it with an appearance
	Appearance appEarth = new Appearance();
	Texture textEarth = new TextureLoader("earth.png", this).getTexture();
	appEarth.setTexture(textEarth);
	TextureAttributes texAttrEarth = new TextureAttributes();
	texAttrEarth.setTextureMode(TextureAttributes.MODULATE);
	appEarth.setTextureAttributes(texAttrEarth);
	
	
	TransformGroup objOrbitEarth = new TransformGroup();
	objOrbitEarth.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	//  Planet translation - a static translation along the X-axis
	Transform3D t = new Transform3D();
	Vector3d positionEarth =  new Vector3d(0.8, 0.0, 0.0);
	t.set(positionEarth);
	TransformGroup objTransEarth = new TransformGroup(t);

	// Planet orbit - followed by an animated rotation around the Y axis

	Alpha alphaOrbitEarth = new Alpha(-1, 10000);	
	RotationInterpolator rotatorOrbitEarth =
	    new RotationInterpolator(alphaOrbitEarth, objOrbitEarth);
	rotatorOrbitEarth.setSchedulingBounds(bounds);
	
	// Spin Earth -
	TransformGroup spinEarth = new TransformGroup();
	spinEarth.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	Transform3D yAxis = new Transform3D();
	Alpha alphaSpinEarth = new Alpha(-1, 1500);	
	RotationInterpolator rotatorSpinEarth =
	    new RotationInterpolator(alphaSpinEarth, spinEarth, yAxis, 0.0f, -(float) Math.PI*2.0f);
	rotatorSpinEarth.setSchedulingBounds(bounds);
	

	// end of node
	//----------------------------------
	
	
	//----------------------------------
	// MOON
	// Create and load a texture object, and associate it with an appearance
	Appearance appMoon = new Appearance();
	Texture textMoon = new TextureLoader("moon.png", this).getTexture();
	appMoon.setTexture(textMoon);
	TextureAttributes texAttrMoon = new TextureAttributes();
	texAttrMoon.setTextureMode(TextureAttributes.MODULATE);
	appMoon.setTextureAttributes(texAttrMoon);
	
	
	TransformGroup objOrbitMoon = new TransformGroup();
	objOrbitMoon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	//  Moon translation - a static translation along the X-axis
	Transform3D t1 = new Transform3D();
	Vector3d positionMoon =  new Vector3d(0.2, 0.0, 0.0);
	t1.set(positionMoon);
	TransformGroup objTransMoon = new TransformGroup(t1);

	
	// Moon orbit - followed by an animated rotation around the Y axis
	Alpha alphaOrbitMoon = new Alpha(-1, 2000);	
	RotationInterpolator rotatorOrbitMoon =
	    new RotationInterpolator(alphaOrbitMoon, objOrbitMoon);
	rotatorOrbitMoon.setSchedulingBounds(bounds);
	
	
	// Spin Moon -
	TransformGroup spinMoon = new TransformGroup();
	spinMoon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	
	Alpha alphaSpinMoon = new Alpha(-1, 500);	
	RotationInterpolator rotatorSpinMoon =
	    new RotationInterpolator(alphaSpinMoon, spinMoon, yAxis, 0.0f, -(float) Math.PI*2.0f);
	rotatorSpinMoon.setSchedulingBounds(bounds);
	

	// end of node
	

	
	//----------------------------------
    objRoot.addChild(objViewTrans);
    
	objViewTrans.addChild(objRotSun);
	//objRotSun.addChild(new ColorCube(0.1));
	objRotSun.addChild(new Sphere(0.25f, Sphere.GENERATE_TEXTURE_COORDS, app));
	
	objViewTrans.addChild(objOrbitEarth);
	objOrbitEarth.addChild(objTransEarth);

	objTransEarth.addChild(spinEarth);
	//spinEarth.addChild(new ColorCube(0.1));
	spinEarth.addChild(new Sphere(0.1f, Sphere.GENERATE_TEXTURE_COORDS, appEarth));
	objTransEarth.addChild(objOrbitMoon);
	
	objOrbitMoon.addChild(objTransMoon);
	objTransMoon.addChild(spinMoon);
	//spinMoon.addChild(new ColorCube(0.05));
	spinMoon.addChild(new Sphere(0.04f, Sphere.GENERATE_TEXTURE_COORDS, appMoon));
	
	
	objRoot.addChild(rotatorOrbitEarth);
	objRoot.addChild(rotatorSpinEarth);
	objRoot.addChild(rotatorOrbitMoon);
	objRoot.addChild(rotatorSpinMoon);
	objRoot.addChild(rotatorSun);
	
	
	
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
      new MainFrame(new HelloUniverse(), 700, 700);
    }
}
