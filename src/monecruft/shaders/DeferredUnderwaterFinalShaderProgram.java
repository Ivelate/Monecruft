package monecruft.shaders;

import monecruft.MonecruftGame;

public class DeferredUnderwaterFinalShaderProgram extends DeferredShaderProgram{

	public DeferredUnderwaterFinalShaderProgram(boolean verbose)
	{
		super("/shaders/finalDrawShader.vshader","/shaders/finalDrawShaderUnderwaterFinal.fshader",true);
	}
	@Override
	protected void setupCustomAttributes() {
		
	}
	@Override
	public boolean supportShadows() {
		return false;
	}
	@Override
	public int colorTexLocation() {
		return MonecruftGame.DEFERREDFBO_COLOR_TEXTURE_LOCATION;
	}
	@Override
	public int miscTexLocation() {
		return MonecruftGame.WATER_NORMAL_TEXTURE_LOCATION;
	}
	@Override
	public boolean supportSkyParameters() {
		return false;
	}
	@Override
	public boolean supportWorldPosition() {
		return true;
	}
	
	@Override
	public int miscTex2Location() {
		return MonecruftGame.CURRENT_LIQUID_NORMAL_TEXTURE_LOCATION;
	}
	@Override
	public boolean supportPlayerLighting() {
		return true;
	}

}