package android.openglsdavid.com.my3d;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sanjithdavid on 9/19/14.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer{

    private Triangle mTriangle;
    private float mAngle;
    private Square   mSquare;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    float angle=0;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        //mSquare = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {


        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
//
//        // Draw square
//        mSquare.draw(mMVPMatrix);

        // Create a rotation for the triangle

        //Use the following code to generate constant rotation.
        //Leave this code out when using TouchEvents.
        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
    angle++;
         Matrix.setRotateM(mRotationMatrix, 0, angle, 0.0f, 1.0f, 0.0f);

        Log.v("TEST",angle+"--");

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }
}