package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.util.RecipeProcessor;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class KettlePotEntity extends BlockEntity implements BlockEntityTicker<KettlePotEntity> {

    private final InputOnlyContainer input = new InputOnlyContainer(3).bindEntityChange(this);
    private final FluidTank fluid = new FluidTank(8000);
    private final KettlePotProcessor processor;

    public KettlePotEntity(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3) {
        super(arg, arg2, arg3);
        processor = new KettlePotProcessor(SDRecipes.KETTLE_POT, this);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, KettlePotEntity kpe) {
        if (level.isClientSide) {
            return;
        }

        if (hasHeatSource(level)) {
            processor.tick(this);
        } else {
            processor.back();
        }

        setChanged();
        level.setBlockAndUpdate(worldPosition, getBlockState());
    }

    private boolean hasHeatSource(Level level) {
        return level.getBlockState(worldPosition.below()).is(SDTags.HEAT_SOURCE);
    }

    public int getProgress() {
        return processor.getProgress();
    }

    public Optional<KettlePotRecipe> getRecipe() {
        return processor.getRecipe();
    }

    public FluidTank getTank() {
        return fluid;
    }

    public InvWrapper getInput() {
        return input;
    }

    public boolean isReady() {
        return processor.getStatus() == RecipeProcessor.Status.READY;
    }

    public void run() {
        processor.run();
    }

    public KettlePotProcessor getProcessor() {
        return processor;
    }

    public Optional<ItemStack> takeResult(ItemStack container) {
        return processor.takeItem(container);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of((NonNullLazy<IItemHandler>) () -> input).cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> fluid).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        input.save(tag, "Input");
        tag.put("Fluid", fluid.getFluid().writeToNBT(new CompoundTag()));
        processor.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        input.load(tag, "Input");
        fluid.setFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid")));
        processor.load(tag);
    }
}
