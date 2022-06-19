package games.moegirl.sinocraft.sinodivination.blockentity;

import games.moegirl.sinocraft.sinocore.api.crafting.RecipeContainer;
import games.moegirl.sinocraft.sinodivination.recipe.KettlePotRecipe;
import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.util.SDTags;
import games.moegirl.sinocraft.sinodivination.util.container.ComposeItemHandler;
import games.moegirl.sinocraft.sinodivination.util.container.InputOnlyContainer;
import games.moegirl.sinocraft.sinodivination.util.container.OutputOnlyContainer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
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
    private final OutputOnlyContainer output = new OutputOnlyContainer(1).bindEntityChange(this);
    private final ComposeItemHandler all = new ComposeItemHandler().append(input).append(output);
    private final FluidTank fluid = new FluidTank(8000);
    private ItemStack remainStack = ItemStack.EMPTY;
    @Nullable
    private KettlePotRecipe recipe = null;
    // only use to read from save
    @Nullable
    private ResourceLocation recipeId = null;
    private int progress = 0; // 0 to 100
    // maybe change? or select by heat source?
    private static final int speed = 5;
    private static final int cooldown = 1;

    // record
    @Nullable
    private KettlePotRecipe lastRecipe;
    private int lastProgress = 0;

    public KettlePotEntity(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3) {
        super(arg, arg2, arg3);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, KettlePotEntity kpe) {
        if (level.isClientSide) {
            return;
        }

        boolean updated = false;
        if (recipeId != null) {
            Optional<KettlePotRecipe> recipeOptional = level.getRecipeManager().byKey(recipeId)
                    .filter(r -> r instanceof KettlePotRecipe)
                    .map(r -> (KettlePotRecipe) r);
            recipeId = null;
            if (recipeOptional.isPresent()) {
                recipe = recipeOptional.get();
                progress = 0;
                updated = true;
            }
        }

        if (!remainStack.isEmpty()) {
            ItemStack newRemainStack = output.insertItem2(0, remainStack, false);
            if (newRemainStack.isEmpty() || !ItemStack.isSame(newRemainStack, remainStack)) {
                remainStack = newRemainStack;
                setChanged();
            }
        }

        if (hasHeatSource(level)) {
            Container inv = input.getInv();
            RecipeContainer c = RecipeContainer.create(inv, fluid);
            if (recipe != null) {
                Int2ObjectMap<KettlePotRecipe.IngredientEntry> map = recipe.matches(c);
                if (map != null) {
                    if (progress >= 100) {
                        // output
                        if (remainStack.isEmpty()) {
                            for (Int2ObjectMap.Entry<KettlePotRecipe.IngredientEntry> entry : map.int2ObjectEntrySet()) {
                                input.extractItem2(entry.getIntKey(), entry.getValue().count(), false);
                            }
                            remainStack = output.insertItem2(0, recipe.assemble(c), false);
                            recipe = null;
                            progress = 0;
                            setChanged();
                            updated = true;
                        }
                    } else {
                        // working
                        progress += speed;
                        setChanged();
                    }
                } else {
                    // clear
                    progress = 0;
                    recipe = null;
                    setChanged();
                    updated = true;
                }
            }
        } else if (progress > 0) {
            // cooldown
            progress = Math.max(0, progress - cooldown);
            setChanged();
            updated = true;
        }

        if (updated) {
            level.setBlockAndUpdate(worldPosition, getBlockState());
        }
    }

    private boolean hasHeatSource(Level level) {
        return level.getBlockState(worldPosition.below()).is(SDTags.HEAT_SOURCE);
    }

    public Optional<KettlePotRecipe> getRunningRecipe() {
        return Optional.ofNullable(recipe);
    }

    public int getProgress() {
        return progress;
    }

    public void beginRecipe() {
        if (recipe == null && level != null && !level.isClientSide) {
            SDRecipes.KETTLE_POT.match(level, RecipeContainer.createTank(input.getRawInv(), fluid)).ifPresent(r -> {
                recipeId = null;
                progress = 0;
                recipe = r;
                setChanged();
                level.setBlockAndUpdate(worldPosition, getBlockState());
            });
        }
    }

    public FluidTank getTank() {
        return fluid;
    }

    public InvWrapper getInput() {
        return input;
    }

    public InvWrapper getOutput() {
        return output;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (recipe != lastRecipe) {
            if (recipe != null) {
                tag.putString("Recipe", recipe.getId().toString());
            } else if (recipeId != null) {
                tag.putString("Recipe", recipeId.toString());
            }
            lastRecipe = recipe;

            tag.putByte("Progress", (byte) progress);
            lastProgress = progress;
        } else if (Math.abs(lastProgress - progress) > 20) {
            tag.putByte("Progress", (byte) progress);
            lastProgress = progress;
        }
        return tag;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of((NonNullLazy<IItemHandler>) () -> all).cast();
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
        output.save(tag, "Output");
        tag.putByte("Progress", (byte) progress);
        tag.put("Fluid", fluid.getFluid().writeToNBT(new CompoundTag()));
        if (recipe != null) {
            tag.putString("Recipe", recipe.getId().toString());
        } else if (recipeId != null) {
            tag.putString("Recipe", recipeId.toString());
        }
        if (!remainStack.isEmpty()) {
            tag.put("RemainStack", remainStack.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        input.load(tag, "Input");
        output.load(tag, "Output");
        progress = tag.getByte("Progress");
        fluid.setFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid")));
        if (tag.contains("Recipe", Tag.TAG_STRING)) {
            ResourceLocation id = new ResourceLocation(tag.getString("Recipe"));
            if (level == null) {
                recipeId = id;
            } else {
                level.getRecipeManager().byKey(id)
                        .filter(r -> r instanceof KettlePotRecipe)
                        .map(r -> (KettlePotRecipe) r)
                        .ifPresent(r -> recipe = r);
            }
        } else {
            recipe = null;
        }
        if (tag.contains("RemainStack", Tag.TAG_COMPOUND)) {
            remainStack = ItemStack.of(tag.getCompound("RemainStack"));
        } else {
            remainStack = ItemStack.EMPTY;
        }
    }
}
