package p455w0rd.p455w0rdsthings.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import p455w0rd.p455w0rdsthings.Globals;
import p455w0rd.p455w0rdsthings.blocks.tileentities.TileEntityCompressor;
import p455w0rd.p455w0rdsthings.container.ContainerCompressor;

public class GuiCompressor extends GuiContainer {
	
	private ResourceLocation GUITexture = new ResourceLocation(Globals.MODID, "textures/gui/compressor.png");

	private TileEntityCompressor compressorTE;
	
	public GuiCompressor(InventoryPlayer inventoryPlayer, TileEntityCompressor te) {
		super(new ContainerCompressor(inventoryPlayer, te));
		compressorTE = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		this.mc.fontRendererObj.drawString("Compressor", 8, 6, 4210752);
		this.mc.fontRendererObj.drawString(compressorTE.getEnergyStored(EnumFacing.DOWN) + "/" + compressorTE.getMaxEnergyStored(EnumFacing.DOWN) + " RF", 8, 40, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.renderEngine.bindTexture(GUITexture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	public void updateScreen() {
	}
	
}