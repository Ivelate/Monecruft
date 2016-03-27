package monecruft.gui;

import monecruft.blocks.BlockLibrary;
import monecruft.gui.Chunk.Direction;
import monecruft.gui.MapGenerator.ChunkGenerationResult;
import monecruft.storage.ByteArrayPool;
import monecruft.storage.CubeStorage;
import monecruft.storage.FileManager;
import monecruft.storage.FileManager.ChunkLoadResult;

public class MapHandler 
{	
	public class ChunkData
	{
		public final ChunkGenerationResult chunkGenerationResult;
		public final boolean initcializedFlag;
		public ChunkData(ChunkGenerationResult chunkGenerationResult,boolean initcializedFlag)
		{
			this.chunkGenerationResult=chunkGenerationResult;
			this.initcializedFlag=initcializedFlag;
		}
	}
	private MapGenerator mg;
	private FileManager fm;
	private WorldFacade wf;
	public MapHandler(int bbegin,int bend,int mapcode,long seed,WorldFacade wf,FileManager fm)
	{
		this.mg=new MapGenerator(bbegin,bend,mapcode,seed);
		this.fm=fm;
		this.wf=wf;
	}
	
	public ChunkData getChunk(int x,int y,int z,byte[][][] c)
	{
		ChunkLoadResult res=this.fm.loadChunk(c, x, y, z);
		switch(res)
		{
		case CHUNK_NORMAL_INITCIALIZED:
			return new ChunkData(ChunkGenerationResult.CHUNK_NORMAL,true);
		case CHUNK_NORMAL_NOT_INITCIALIZED:
			return new ChunkData(ChunkGenerationResult.CHUNK_NORMAL,false);
		case CHUNK_FULL:
			return new ChunkData(ChunkGenerationResult.CHUNK_NORMAL,true);
		case CHUNK_NOT_FOUND:
			return new ChunkData(this.mg.generateChunk(x, y, z,c),false);
		case CHUNK_EMPTY:
			return new ChunkData(ChunkGenerationResult.CHUNK_EMPTY,true);
		}
		
		return new ChunkData(ChunkGenerationResult.CHUNK_EMPTY,true); //Never reached
	}
	public void storeChunk(int x,int y,int z,CubeStorage c,boolean initcializedFlag)
	{
		if(c.isTrueStorage()){
			this.fm.storeChunk(c.getArray(), x, y, z,initcializedFlag);
		}
		else this.fm.storeChunk(c.get(0,0,0), x, y, z,initcializedFlag);
	}
	public void generateChunkObjects(Chunk c)
	{
		this.mg.generateChunkObjects(c);
	}
	
	public boolean shouldDraw(byte blockCode,byte neighbourBlockCode,boolean liquidTag,Direction d){
		if(liquidTag)
			if(BlockLibrary.isLiquid(blockCode)||BlockLibrary.isSolid(blockCode)){
				/*if(BlockLibrary.isSameBlock(blockCode, neighbourBlockCode)){
					int maxh=BlockLibrary.getLiquidMaxLevel(blockCode);
					if(d==Direction.YP){
						if(BlockLibrary.getLiquidLevel(neighbourBlockCode)<maxh) return true;
						else return false;
					}
					else if(d==Direction.YM){
						if(BlockLibrary.getLiquidLevel(blockCode)<maxh) return true;
						else return false;
					}
				}*/
				return false;
			}
			else return true;
		else
			return (BlockLibrary.canSeeTrough(blockCode)) && !(blockCode==neighbourBlockCode&&BlockLibrary.isPartnerGrouped(blockCode));
	}
}
