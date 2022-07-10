package games.moegirl.sinocraft.sinodivination.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinodivination.blockentity.SilkwormPlaqueEntity;
import games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static games.moegirl.sinocraft.sinodivination.menu.SilkwormPlaqueMenu.TEXTURE;

public class SilkwormPlaqueScreen extends AbstractContainerScreen<SilkwormPlaqueMenu> {

    public SilkwormPlaqueScreen(SilkwormPlaqueMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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
        TEXTURE.blitTexture(pPoseStack, "background", this);
        SilkwormPlaqueEntity entity = menu.entity();
        for (int i = 0; i < SilkwormPlaqueEntity.SILKWORM_COUNT; i++) {
            TEXTURE.blitProgress(pPoseStack, "progress" + i, this, entity.silkworm(i).progress());
        }
    }
}
