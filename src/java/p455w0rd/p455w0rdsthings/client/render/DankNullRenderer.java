package p455w0rd.p455w0rdsthings.client.render;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import codechicken.lib.render.IItemRenderer;
import codechicken.lib.render.TransformUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import p455w0rd.p455w0rdsthings.ModItems;
import p455w0rd.p455w0rdsthings.items.ItemDankNull;
import p455w0rd.p455w0rdsthings.util.ItemUtils;

@SideOnly(Side.CLIENT)
public class DankNullRenderer implements IItemRenderer {
	private float timer = 0.0F;

	@Override
	public void renderItem(ItemStack item) {
		if (!(item.getItem() instanceof ItemDankNull)) {
			return;
		}
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		if (rm == null) {
			return;
		}
		GameSettings options = rm.options;
		if (options == null) {
			return;
		}
		int view = options.thirdPersonView;
		int index = ItemUtils.getSelectedStackIndex(item);
		ItemStack containedStack = ItemUtils.getItemByIndex(item, index);
		IBakedModel holderModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(ModItems.dankNullHolder, 1, item.getItemDamage()));

		TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);

		if (containedStack != null) {
			IBakedModel containedItemModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(containedStack);
			/*
			 * GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			 * GlStateManager.enableRescaleNormal();
			 * GlStateManager.alphaFunc(516, 0.1F);
			 * GlStateManager.enableLighting(); GlStateManager.enableBlend();
			 * GlStateManager.enableAlpha();
			 * GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.
			 * SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
			 * GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			 */
			GlStateManager.pushMatrix();

			if (containedStack.getItem() instanceof ItemBlock && !(Block.getBlockFromItem(containedStack.getItem()) instanceof BlockTorch)) {
				GlStateManager.scale(0.4D, 0.4D, 0.4D);
				if (containedItemModel.isBuiltInRenderer()) {
					if (view > 0 || !isStackInHand(item)) {
						GlStateManager.scale(1.1D, 1.1D, 1.1D);
						GlStateManager.translate(1.25D, 1.4D, 1.25D);
					}
					else {
						GlStateManager.translate(1.25D, 2.0D, 1.25D);
					}
				}
				else if (view > 0 || !isStackInHand(item)) {
					GlStateManager.translate(0.75D, 0.9D, 0.75D);
				}
				else {
					GlStateManager.translate(0.75D, 1.5D, 0.75D);
				}
			}
			else {
				GlStateManager.scale(0.5D, 0.5D, 0.5D);
				if (containedItemModel.isBuiltInRenderer()) {
					if (view > 0 || !isStackInHand(item)) {
						if (containedStack.getItem() instanceof ItemSkull) {
							if (containedStack.getItemDamage() == 5) {
								GlStateManager.scale(0.65D, 0.65D, 0.65D);
								GlStateManager.translate(1.5D, 3D, 1.5D);
							}
							else {
								GlStateManager.translate(0.75D, 2.25D, 1.1D);
							}
						}
						else {
							GlStateManager.scale(1.1D, 1.1D, 1.1D);
							GlStateManager.translate(1.25D, 1.4D, 1.25D);
						}
					}
					else {
						if (containedStack.getItem() instanceof ItemSkull) {
							if (containedStack.getItemDamage() == 5) {
								GlStateManager.scale(0.65D, 0.65D, 0.65D);
								GlStateManager.translate(1.5D, 3D, 1.5D);
							}
							else {
								GlStateManager.translate(0.75D, 2.25D, 1.1D);
							}
						}
						else {
							GlStateManager.translate(0.75D, 2.0D, 1.0D);
						}
					}
				}
				else if (view > 0 || !isStackInHand(item)) {
					GlStateManager.translate(0.5D, 0.9D, 0.5D);
				}
				else {
					GlStateManager.translate(0.5D, 1.5D, 0.5D);
				}
			}

			if (timer >= 360.1F) {
				timer = 0.0F;
			}
			timer += 0.25F;
			if (item.isOnItemFrame()) {
				GlStateManager.scale(1.25D, 1.25D, 1.25D);
				GlStateManager.translate(-0.2D, -0.2D, -0.5D);
			}
			if (containedItemModel.isBuiltInRenderer()) {
				GlStateManager.translate(-0.0D, 0.0D, -0.0D);

				GlStateManager.rotate(timer, 1.0F, timer, 1.0F);
				GlStateManager.translate(0.0D, 0.0D, 0.0D);
			}
			else {
				GlStateManager.rotate(timer, 1.0F, 1.0F, 1.0F);
			}
			
			containedItemModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(containedItemModel, ItemCameraTransforms.TransformType.NONE, false);
			renderItem(containedStack, containedItemModel);
			/*
			 * GlStateManager.translate(1.0D, 1.0D, 1.0D);
			 * GlStateManager.scale(1.0D, 1.0D, 1.0D);
			 * GlStateManager.cullFace(GlStateManager.CullFace.BACK);
			 * GlStateManager.popMatrix();
			 * GlStateManager.disableRescaleNormal();
			 * GlStateManager.disableBlend(); GlStateManager.disableAlpha();
			 * GlStateManager.disableLighting();
			 */
			GlStateManager.popMatrix();
		}
		/*
		 * GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		 * GlStateManager.enableRescaleNormal();
		 * GlStateManager.enableLighting(); GlStateManager.alphaFunc(516, 0.1F);
		 * GlStateManager.enableAlpha(); GlStateManager.enableBlend();
		 * GlStateManager.disableCull();
		 * GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.
		 * SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
		 * GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		 */
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.enableBlend();
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		if (item.isOnItemFrame()) {
			GlStateManager.scale(1.25D, 1.25D, 1.25D);
			GlStateManager.translate(-0.1D, -0.1D, -0.25D);
		}
		holderModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(holderModel, ItemCameraTransforms.TransformType.NONE, false);
		renderItem(item, holderModel);

		GlStateManager.popMatrix();
		//GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableLighting();
		/*
		 * GlStateManager.disableLighting();
		 * GlStateManager.disableRescaleNormal(); GlStateManager.disableBlend();
		 * GlStateManager.disableAlpha();
		 */

		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}

	private boolean isStackInHand(ItemStack itemStackIn) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player.getHeldItemMainhand() == itemStackIn || player.getHeldItemOffhand() == itemStackIn) {
			return true;
		}
		return false;
	}

	private void renderModel(IBakedModel model, ItemStack stack) {
		this.renderModel(model, -1, stack);
	}

	private void renderModel(IBakedModel model, int color) {
		this.renderModel(model, color, (ItemStack) null);
	}

	private void renderModel(IBakedModel model, int color, ItemStack stack) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.ITEM);

		for (EnumFacing enumfacing : EnumFacing.values()) {
			this.renderQuads(vertexbuffer, model.getQuads((IBlockState) null, enumfacing, 0L), color, stack);
		}

		this.renderQuads(vertexbuffer, model.getQuads((IBlockState) null, (EnumFacing) null, 0L), color, stack);
		tessellator.draw();
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return new ArrayList<BakedQuad>();
	}

	private void renderQuads(VertexBuffer renderer, List<BakedQuad> quads, int color, ItemStack stack) {
		boolean flag = color == -1 && stack != null;
		int i = 0;

		for (int j = quads.size(); i < j; ++i) {
			BakedQuad bakedquad = (BakedQuad) quads.get(i);
			int k = color;

			if (flag && bakedquad.hasTintIndex()) {
				ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
				k = itemColors.getColorFromItemstack(stack, bakedquad.getTintIndex());
				//k=0x00FF00;

				if (EntityRenderer.anaglyphEnable) {
					k = TextureUtil.anaglyphColor(k);
				}
				k = k | -16777216;
				//k = k | 0x00FF00;
			}
			net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
		}
	}

	public void renderItem(ItemStack stack, IBakedModel model) {
		if (stack != null) {
			GlStateManager.pushMatrix();
			//GlStateManager.translate(-0.5F, -0.5F, -0.5F);

			if (model.isBuiltInRenderer()) {
				//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				//GlStateManager.enableRescaleNormal();
				//TileEntityItemStackRenderer.instance.renderByItem(stack);
				Minecraft.getMinecraft().getItemRenderer().renderItem(Minecraft.getMinecraft().thePlayer, stack, TransformType.NONE);
			}
			else {

				this.renderModel(model, stack);

				if (stack.hasEffect()) {
					if (stack.getItem() instanceof ItemDankNull) {
						this.renderEffect2(model);
					}
					else {
						this.renderEffect(model);
					}
				}
			}

			GlStateManager.popMatrix();
		}
	}

	private void renderEffect(IBakedModel model) {
		GlStateManager.depthMask(false);
		GlStateManager.depthFunc(514);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/misc/enchanted_item_glint.png"));
		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
		GlStateManager.translate(f, 0.0F, 0.0F);
		GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
		this.renderModel(model, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f1 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
		GlStateManager.translate(-f1, 0.0F, 0.0F);
		GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
		this.renderModel(model, -8372020);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableLighting();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}
	
	private void renderEffect2(IBakedModel model) {
		GlStateManager.depthMask(false);
		GlStateManager.depthFunc(514);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/misc/enchanted_item_glint.png"));
		GlStateManager.matrixMode(5890);
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
		GlStateManager.translate(f, 0.0F, 0.0F);
		GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
		//this.renderModel(model, -8372020);
		this.renderModel(model, 0xFFFFFF00);
		GlStateManager.popMatrix();
		/*
		GlStateManager.pushMatrix();
		GlStateManager.scale(8.0F, 8.0F, 8.0F);
		float f1 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
		GlStateManager.translate(-f1, 0.0F, 0.0F);
		GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
		//this.renderModel(model, -8372020);
		this.renderModel(model, 0xFFFFFF00);
		GlStateManager.popMatrix();
		*/
		GlStateManager.matrixMode(5888);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableLighting();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, TransformUtils.DEFAULT_BLOCK.getTransforms(), cameraTransformType);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

}
