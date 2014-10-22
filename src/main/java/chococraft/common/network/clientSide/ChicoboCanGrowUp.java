package chococraft.common.network.clientSide;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChicoboCanGrowUp implements IMessage {

	public int entityID;
	public int dimensionId;
	public boolean isCanGrowUp;
	public boolean growUp;

	public ChicoboCanGrowUp() {}

	public ChicoboCanGrowUp(EntityChicobo chicobo) {
		this.entityID = chicobo.getEntityId();
		this.dimensionId = chicobo.worldObj.provider.dimensionId;
		this.isCanGrowUp = chicobo.isCanGrowUp();
		this.growUp = chicobo.growUp;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		buffer.writeBoolean(this.isCanGrowUp);
		buffer.writeBoolean(this.growUp);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.isCanGrowUp = buffer.readBoolean();
		this.growUp = buffer.readBoolean();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChicoboCanGrowUp, IMessage> {

		@Override
		public IMessage onMessage(ChicoboCanGrowUp message, MessageContext ctx) {
			EntityAnimalChocobo chicobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chicobo != null && chicobo instanceof EntityChicobo)
			{
				((EntityChicobo)chicobo).setCanGrowUp(message.isCanGrowUp);
				((EntityChicobo)chicobo).growUp = message.growUp;
			}
			return null;
		}
	}
}
