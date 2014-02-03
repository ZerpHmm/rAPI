package rafoudiablol.api.item;

import java.lang.reflect.InvocationTargetException;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rafoudiablol.cp.Identifier;
import rafoudiablol.cp.ModCore;
import rafoudiablol.cp.common.EntityCustomPainting;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

/**
 *	fix ItemHangingEntity.createHangingEntity
 *	@see ItemHangingEntity
**/

public class ItemHangingCustomEntity extends Item
{
    private final Class hangingEntityClass;

    public ItemHangingCustomEntity(int i, Class clazz)
    {
        super(i);
        this.hangingEntityClass = clazz;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f1, float f2, float f3)
    {
        if (l == 0)
        {
            return false;
        }
        else if (l == 1)
        {
            return false;
        }
        else
        {
            int i1 = Direction.facingToDirection[l];
            EntityHanging entityhanging = this.createHangingEntity(world, i, j, k, i1);
            
            if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack))
            {
                return false;
            }
            else
            {
                if (entityhanging != null && entityhanging.onValidSurface())
                {
                	this.preSpawn(world, entityplayer, i, j, k, entityhanging);
                	
                    if (!world.isRemote)
                    {
                		world.spawnEntityInWorld(entityhanging);
                		
                    }
                    
                    this.postSpawn(world, entityplayer, i, j, k, entityhanging);

                    --itemstack.stackSize;
                }

                return true;
            }
        }
    }
    
    protected void preSpawn(World world, EntityPlayer entityplayer, int i, int j, int k, EntityHanging entity)  {}
    protected void postSpawn(World world, EntityPlayer entityplayer, int i, int j, int k, EntityHanging entity)  {}
    
    /**
     * Create the hanging entity associated to this item.
     */
    private EntityHanging createHangingEntity(World world, int i, int j, int k, int l)
    {
    	try
		{
			return (EntityHanging)hangingEntityClass
				.getConstructor(World.class, int.class, int.class, int.class, int.class)
					.newInstance(world, i, j, k, l);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
    	
		return null;
	}
}
