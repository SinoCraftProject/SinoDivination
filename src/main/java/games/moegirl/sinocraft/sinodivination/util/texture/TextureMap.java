package games.moegirl.sinocraft.sinodivination.util.texture;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public record TextureMap(
        ResourceLocation texture,
        int width,
        int height,
        Map<String, PointEntry> pointMap,
        Map<String, TextureEntry> textureMap,
        Map<String, SlotEntry> slotMap,
        Map<String, SlotsEntry> slotsMap,
        Map<String, ProgressEntry> progressMap,
        List<PointEntry> points,
        List<TextureEntry> textures,
        List<SlotEntry> slot,
        List<SlotsEntry> slots,
        List<ProgressEntry> progress
) {

    public static TextureMap of(ResourceLocation texture) {
        return TextureParser.parse(texture);
    }

    public static TextureMap of(String modid, String path, String name) {
        return TextureParser.parse(new ResourceLocation(modid, "textures/" + path + "/" + name + ".png"));
    }

    public Optional<PointEntry> getPoint(String name) {
        return Optional.ofNullable(pointMap.get(name));
    }

    public Optional<TextureEntry> getTexture(String name) {
        return Optional.ofNullable(textureMap.get(name));
    }

    public Optional<SlotEntry> getSlot(String name) {
        return Optional.ofNullable(slotMap.get(name));
    }

    public Optional<SlotsEntry> getSlots(String name) {
        return Optional.ofNullable(slotsMap.get(name));
    }

    public Optional<ProgressEntry> getProgress(String name) {
        return Optional.ofNullable(progressMap.get(name));
    }

    public void placeSlot(Container container, String name, int index, Function<Slot, Slot> addSlot, SlotStrategy slot) {
        getSlot(name).ifPresent(entry -> placeSlot(container, entry, index, addSlot, slot));
    }

    public void placeSlots(Container container, String name, int beginIndex, Function<Slot, Slot> addSlot, SlotStrategy slot) {
        getSlots(name).ifPresent(slotsEntry -> {
            int i = beginIndex;
            for (SlotEntry entry : slotsEntry.entries()) {
                placeSlot(container, entry, i++, addSlot, slot);
            }
        });
    }

    private void placeSlot(Container container, SlotEntry entry, int index, Function<Slot, Slot> addSlot, SlotStrategy slot) {
        addSlot.apply(slot.createSlot(container, index, entry.x(), entry.y()));
    }

    @OnlyIn(Dist.CLIENT)
    public void bindTexture() {
        com.mojang.blaze3d.systems.RenderSystem.setShaderTexture(0, texture);
    }

    @OnlyIn(Dist.CLIENT)
    public void blitTexture(com.mojang.blaze3d.vertex.PoseStack stack, String name,
                            net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<?> gui,
                            games.moegirl.sinocraft.sinocore.api.utility.GLSwitcher... configurations) {
        if (textureMap.containsKey(name)) {
            bindTexture();
            TextureEntry entry = textureMap.get(name);
            blitTexture(stack, entry, (int) (gui.getGuiLeft() + entry.u()), (int) (gui.getGuiTop() + entry.v()), entry.w(), entry.h());
        }
        resumeGL(configurations);
    }

    @OnlyIn(Dist.CLIENT)
    public void blitProgress(com.mojang.blaze3d.vertex.PoseStack stack, String name,
                             net.minecraft.client.gui.screens.inventory.AbstractContainerScreen<?> gui,
                             float progress, games.moegirl.sinocraft.sinocore.api.utility.GLSwitcher... configurations) {
        if (progressMap.containsKey(name)) {
            bindTexture();
            ProgressEntry entry = progressMap.get(name);
            int x = gui.getGuiLeft() + entry.x();
            int y = gui.getGuiTop() + entry.y();
            String begin = entry.begin();
            if (begin != null && textureMap.containsKey(begin)) {
                TextureEntry bg = textureMap.get(begin);
                blitTexture(stack, bg, x, y, bg.w(), bg.h());
            }
            float tu, tv;
            int tw, th;
            if (progress > 0) {
                TextureEntry p = textureMap.get(entry.finished());
                if (entry.isVertical()) {
                    if (entry.isOpposite()) {
                        float dv = (1 - progress) * p.h();
                        tv = p.v() + dv;
                        y += (int) dv;
                    } else {
                        tv = p.v();
                    }
                    tu = p.u();
                    tw = p.w();
                    th = (int) (p.h() * progress);
                } else {
                    if (entry.isOpposite()) {
                        float du = (1 - progress) * p.w();
                        tu = p.u() + du;
                        x += (int) du;
                    } else {
                        tu = p.u();
                    }
                    tv = p.v();
                    tw = (int) (p.w() * progress);
                    th = p.h();
                }
                blitTexture(stack, tu, tv, tw, th, x, y, tw, th);
            }
        }
        resumeGL(configurations);
    }

    @OnlyIn(Dist.CLIENT)
    private void blitTexture(com.mojang.blaze3d.vertex.PoseStack stack, TextureEntry entry, int x, int y, int w, int h) {
        blitTexture(stack, entry.u(), entry.v(), entry.w(), entry.h(), x, y, w, h);
    }

    @OnlyIn(Dist.CLIENT)
    private void blitTexture(com.mojang.blaze3d.vertex.PoseStack stack, float tu, float tv, int tw, int th, int x, int y, int w, int h) {
        if (w > 0 && h > 0) {
            net.minecraft.client.gui.GuiComponent.blit(stack, x, y, w, h, tu, tv, tw, th, width, height);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void resumeGL(games.moegirl.sinocraft.sinocore.api.utility.GLSwitcher... configurations) {
        for (var switcher : configurations) {
            switcher.resume();
        }
    }
}
