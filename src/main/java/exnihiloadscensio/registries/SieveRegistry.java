package exnihiloadscensio.registries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import exnihiloadscensio.ExNihiloAdscensio;
import exnihiloadscensio.blocks.BlockSieve.MeshType;
import exnihiloadscensio.blocks.ENBlocks;
import exnihiloadscensio.items.ENItems;
import exnihiloadscensio.items.ItemResource;
import exnihiloadscensio.json.CustomBlockInfoJson;
import exnihiloadscensio.json.CustomItemInfoJson;
import exnihiloadscensio.registries.types.Compostable;
import exnihiloadscensio.registries.types.Siftable;
import exnihiloadscensio.util.BlockInfo;
import exnihiloadscensio.util.ItemInfo;
import lombok.Getter;

public class SieveRegistry {

	@Getter
	private static HashMap<BlockInfo, ArrayList<Siftable>> registry = new HashMap<BlockInfo, ArrayList<Siftable>>();
	
	public static void register(BlockInfo block, ItemInfo drop, float chance, int meshLevel) {
		Siftable siftable = new Siftable(drop, chance, meshLevel);
		
		register(block, siftable);
	}
	
	public static void register(IBlockState state, ItemInfo drop, float chance, int meshLevel) {
		register(new BlockInfo(state), new Siftable(drop, chance, meshLevel));
	}
	
	public static void register(IBlockState state, ItemStack drop, float chance, int meshLevel) {
		register(new BlockInfo(state), new Siftable(new ItemInfo(drop), chance, meshLevel));
	}
	
	public static void register(BlockInfo block, Siftable drop) {
		if (block == null)
			return;
		ArrayList<Siftable> currentDrops;
		if (registry.containsKey(block)) {
			currentDrops = registry.get(block);
		} else {
			currentDrops = new ArrayList<Siftable>();
		}
		
		currentDrops.add(drop);
		registry.put(block, currentDrops);
	}
	
	/**
	 * Gets *all* possible drops from the sieve. It is up to the dropper to
	 * check whether or not the drops should be dropped!
	 * @param block
	 * @return ArrayList of {@linkplain exnihiloadscensio.registries.types.Siftable}
	 * that could *potentially* be dropped.
	 */
	public static ArrayList<Siftable> getDrops(BlockInfo block) {
		if (!registry.containsKey(block))
			return null;
		
		return registry.get(block);
	}
	
	/**
	 * Gets *all* possible drops from the sieve. It is up to the dropper to
	 * check whether or not the drops should be dropped!
	 * @param block
	 * @return ArrayList of {@linkplain exnihiloadscensio.registries.types.Siftable}
	 * that could *potentially* be dropped.
	 */
	public static ArrayList<Siftable> getDrops(ItemStack block) {
		return getDrops(new BlockInfo(block));
	}
	
	public static boolean canBeSifted(ItemStack stack) {
		if (stack == null)
			return false;
		return registry.containsKey(new BlockInfo(stack));
	}
	
	public static void registerDefaults() {
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 1f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 1f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 0.5f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 0.5f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 0.1f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), ItemResource.getResourceStack("stones"), 0.1f, MeshType.STRING.getID());
		
		register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.WHEAT_SEEDS, 0), 0.7f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.MELON_SEEDS, 0), 0.35f, MeshType.STRING.getID());
		register(Blocks.DIRT.getDefaultState(), new ItemInfo(Items.PUMPKIN_SEEDS, 0), 0.35f, MeshType.STRING.getID());
		
		register(Blocks.SAND.getDefaultState(), new ItemInfo(Items.DYE, 3), 0.03f, MeshType.STRING.getID());
		
		
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.FLINT, 0), 0.25f, MeshType.FLINT.getID());
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.COAL, 0), 0.125f, MeshType.FLINT.getID());
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DYE, 4), 0.05f, MeshType.FLINT.getID());
		
		
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DIAMOND, 0), 0.008f, MeshType.IRON.getID());
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.EMERALD, 0), 0.008f, MeshType.IRON.getID());
		
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.DIAMOND, 0), 0.016f, MeshType.DIAMOND.getID());
		register(Blocks.GRAVEL.getDefaultState(), new ItemInfo(Items.EMERALD, 0), 0.016f, MeshType.DIAMOND.getID());
		
		
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.QUARTZ, 0), 1f, MeshType.FLINT.getID());
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.QUARTZ, 0), 0.33f, MeshType.FLINT.getID());
		
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.NETHER_WART, 0), 0.1f, MeshType.STRING.getID());
		
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.GHAST_TEAR, 0), 0.02f, MeshType.DIAMOND.getID());
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.QUARTZ, 0), 1f, MeshType.DIAMOND.getID());
		register(Blocks.SOUL_SAND.getDefaultState(), new ItemInfo(Items.QUARTZ, 0), 0.8f, MeshType.DIAMOND.getID());
		
		
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.DYE, 15), 0.2f, MeshType.STRING.getID());
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.GUNPOWDER, 0), 0.07f, MeshType.STRING.getID());
		
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.REDSTONE, 0), 0.125f, MeshType.IRON.getID());
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.REDSTONE, 0), 0.25f, MeshType.DIAMOND.getID());
		
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.GLOWSTONE_DUST, 0), 0.0625f, MeshType.IRON.getID());
		register(ENBlocks.dust.getDefaultState(), new ItemInfo(Items.BLAZE_POWDER, 0), 0.05f, MeshType.IRON.getID());
		
		
		//Ores
		/*register(Blocks.GRAVEL.getDefaultState(), ExNihiloAdscensio.proxy.getFoodRegistryWrapper().getStack("iron"), 0.2f, MeshType.FLINT.getID());
		register(Blocks.GRAVEL.getDefaultState(), ExNihiloAdscensio.proxy.getFoodRegistryWrapper().getStack("iron"), 0.2f, MeshType.IRON.getID());
		register(Blocks.GRAVEL.getDefaultState(), ExNihiloAdscensio.proxy.getFoodRegistryWrapper().getStack("iron"), 0.1f, MeshType.DIAMOND.getID());
		
		register(Blocks.GRAVEL.getDefaultState(), ExNihiloAdscensio.proxy.getFoodRegistryWrapper().getStack("gold"), 0.05f, MeshType.IRON.getID());
		register(Blocks.GRAVEL.getDefaultState(), ExNihiloAdscensio.proxy.getFoodRegistryWrapper().getStack("gold"), 0.1f, MeshType.DIAMOND.getID());
		*/
	}
	
	private static Gson gson;

	public static void loadJson(File file)
	{
		gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(BlockInfo.class, new CustomBlockInfoJson())
				.registerTypeAdapter(ItemInfo.class, new CustomItemInfoJson()).create();
		if (file.exists())
		{
			try 
			{
				FileReader fr = new FileReader(file);
				HashMap<String, ArrayList<Siftable>> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, ArrayList<Siftable>>>(){}.getType());
				
				Iterator<String> it = gsonInput.keySet().iterator();
				
				while (it.hasNext())
				{
					String s = (String) it.next();
					BlockInfo stack = new BlockInfo(s);
					registry.put(stack, gsonInput.get(s));
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			registerDefaults();
			saveJson(file);
		}
	}
	
	public static void saveJson(File file)
	{
		try
		{
			FileWriter fw = new FileWriter(file);
			gson.toJson(registry, fw);
			
			fw.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
