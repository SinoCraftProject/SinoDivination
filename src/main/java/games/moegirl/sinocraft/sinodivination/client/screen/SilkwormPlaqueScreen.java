package games.moegirl.sinocraft.sinodivination.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.api.client.screen.TextureMapClient;
import games.moegirl.sinocraft.sinocore.api.utility.texture.TextureEntry;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu.TEXTURE;

public class SilkwormPlaqueScreen extends AbstractContainerScreen<SilkwormPlaqueMenu> {

    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    public SilkwormPlaqueScreen(SilkwormPlaqueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        TextureEntry entry = TEXTURE.textures().ensureGet("background");
        width = entry.w();
        height = entry.h();
    }

    @Override
    protected void init() {
        super.init();
        TEXTURE.points().get("title").ifPresent(p -> {
            titleLabelX = p.x();
            titleLabelY = p.y();
        });
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        CLIENT.blitTexture(pPoseStack, "background", this);
        SilkwormPlaqueEntity entity = menu.entity();
        for (int i = 0; i < SilkwormPlaqueEntity.SILKWORM_COUNT; i++) {
            CLIENT.blitProgress(pPoseStack, "progress" + i, this, entity.silkworm(i).progress());
        }
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 0x404040);
    }
}
