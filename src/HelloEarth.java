/*
 *	@(#)HelloEarth.java 2001-09-21
 *
 *    Stefan Gustavson, ITN-LiTH, 2001.
 *
 *    Draw a rotating, textured sphere.
 *    Based on the example files HelloUniverse.java
 *    and TexureImage.java from Sun Microsystems.
 */

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;

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

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class HelloEarth extends Applet {

    private SimpleUniverse u = null;
    
    public BranchGroup createSceneGraph() {
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

	Transform3D viewtransform = new Transform3D(); // Global view transformation
	viewtransform.rotX((float)Math.PI/8.0f);
	TransformGroup viewRotate = new TransformGroup(viewtransform);
	TransformGroup objRotate = new TransformGroup();
	objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(viewRotate); // View rotation
	viewRotate.addChild(objRotate); // Planet axis rotation

	// Create and load a texture object, and associate it with an appearance
	Appearance app = new Appearance();
	Texture tex = new TextureLoader("earth.png", this).getTexture();
	app.setTexture(tex);
	// The three lines below are really only needed if lighting is applied
	TextureAttributes texAttr = new TextureAttributes();
	texAttr.setTextureMode(TextureAttributes.MODULATE);
	app.setTextureAttributes(texAttr);

	// Create a simple Shape3D node, attach the texture to it
      // and add the object to the scene graph.
	objRotate.addChild(new Sphere(0.5f, Sphere.GENERATE_TEXTURE_COORDS, app));

	// Create a Behavior object that will perform the animation
	Transform3D rotateY = new Transform3D();
	Alpha rotAlpha = new Alpha(-1, 10000); // infinite loop, 10000 ms cycle
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	RotationInterpolator rotator =
	    new RotationInterpolator(rotAlpha, objRotate, rotateY,
				     0.0f, (float)Math.PI * 2.0f);
	rotator.setSchedulingBounds(bounds);
	objRoot.addChild(rotator);

      // Have Java 3D perform optimizations on this scene graph.
      objRoot.compile();

	return objRoot;
    }

    public HelloEarth() {
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
    // The following allows HelloEarth to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
	new MainFrame(new HelloEarth(), 500, 500);
    }
}
