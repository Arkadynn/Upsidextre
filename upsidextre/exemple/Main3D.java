package upsidextre.exemple;


import java.io.File;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class Main3D implements ApplicationListener {
	public Environment environment;
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	public ArrayList<ModelInstance> instances= new ArrayList();
	public CameraInputController camController;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
        modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		cam = new PerspectiveCamera(65, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 0.40f, 0.15f);
		//cam.position.set(0f,4f,-1.5f);
		cam.lookAt(0,0.1f,0.35f);
		cam.near = 0.25f;
		cam.far = 30f;
		cam.update();
        UBJsonReader jsonReader = new UBJsonReader();
        // Create a model loader passing in our json reader
        G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
        // Now load the model by name
        // Note, the model (g3db file ) and textures need to be added to the assets folder of the Android proj
        
        model = modelLoader.loadModel(Gdx.files.getFileHandle("data/robot.g3db", FileType.Internal));
        instance = new ModelInstance(model);
        instance.transform.translate(0, -1, 0);
        instances.add(instance);
        instances.add(new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("data/table.g3db", FileType.Internal))));
        instances.get(1).transform.translate(0,-1.05f,0.6f);
        Texture texTile1 = new Texture(Gdx.files.internal("data/ground.png"));
        Texture texTile2 = new Texture(Gdx.files.internal("data/cube.png"));
        Material mat1 = new Material(TextureAttribute.createDiffuse(texTile1));
        Material mat2 = new Material(TextureAttribute.createDiffuse(texTile2));
        ModelInstance ground = new ModelInstance( new ModelBuilder().createBox(6,0.001f,6, mat1,Usage.Position | Usage.Normal| Usage.TextureCoordinates));
        ModelInstance chair = new ModelInstance( new ModelBuilder().createBox(0.5f,0.5f,0.5f, mat2,Usage.Position | Usage.Normal| Usage.TextureCoordinates));
        ground.transform.translate(0, -1f, 1.5f);
        chair.transform.translate(0f, -0.75f, 0.1f);
        instances.add(chair);
        instances.add(ground);
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		camController.update();
		instance.getNode("Left_Forearm_Joint_01").rotation.setEulerAngles(instance.getNode("Left_Forearm_Joint_01").rotation.getYaw()+1, 0, 0);
		instance.calculateTransforms();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
	}
	public boolean needsGL20 () {
		return true;
	}
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
