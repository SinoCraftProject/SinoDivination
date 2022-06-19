package games.moegirl.sinocraft.sinodivination.util.texture;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.*;

public class TextureParser {

    public static TextureMap parse(ResourceLocation texture) {
        String modid = texture.getNamespace();
        String texPath = texture.getPath();
        String texName = texPath.substring(0, texPath.length() - 3) + "toml";
        String[] paths = ArrayUtils.insert(0, texName.split("/"), "assets", modid);
        Path toml = ModList.get().getModFileById(modid).getFile().findResource(paths);
        try (Reader reader = new FileReader(toml.toFile())) {
            CommentedConfig config = new TomlParser().parse(reader);
            int width = config.getIntOrElse("width", 256);
            int height = config.getIntOrElse("height", 256);
            List<PointEntry> points = new LinkedList<>();
            Map<String, PointEntry> pointMap = new HashMap<>();
            List<TextureEntry> textures = new LinkedList<>();
            Map<String, TextureEntry> textureMap = new HashMap<>();
            List<SlotEntry> slot = new LinkedList<>();
            Map<String, SlotEntry> slotMap = new HashMap<>();
            List<SlotsEntry> slots = new LinkedList<>();
            Map<String, SlotsEntry> slotsMap = new HashMap<>();
            List<ProgressEntry> progress = new LinkedList<>();
            Map<String, ProgressEntry> progressMap = new HashMap<>();
            for (CommentedConfig.Entry entry : config.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof List<?> list) {
                    buildPoint(name, list, points, pointMap);
                } else if (value instanceof Map<?, ?> map) {
                    switch (((String) map.get("type")).toLowerCase(Locale.ROOT)) {
                        case "point" -> buildPoint(name, map, points, pointMap);
                        case "texture" -> buildTexture(name, map, textures, textureMap);
                        case "slot" -> buildSlot(name, map, slot, slotMap);
                        case "slots" -> buildSlots(name, map, slots, slotsMap);
                        case "progress" -> buildProgress(name, map, progress, progressMap);
                    }
                }
            }
            return new TextureMap(texture, width, height,
                    Map.copyOf(pointMap), Map.copyOf(textureMap), Map.copyOf(slotMap), Map.copyOf(slotsMap), Map.copyOf(progressMap),
                    List.copyOf(points), List.copyOf(textures), List.copyOf(slot), List.copyOf(slots), List.copyOf(progress));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void buildProgress(String name, Map<?, ?> data, List<ProgressEntry> progress, Map<String, ProgressEntry> progressMap) {
        int[] position = getPoint(data, "position");
        String begin = (data.get("begin_texture") instanceof String s && !s.isEmpty()) ? s : null;
        String finished = (String) data.get("finished_texture");
        boolean isVertical, isOpposite;
        if (data.containsKey("direction")) {
            String direction = ((String) data.get("direction")).trim().toLowerCase(Locale.ROOT);
            isVertical = direction.endsWith("vertical");
            isOpposite = direction.startsWith("-");
        } else {
            isVertical = false;
            isOpposite = false;
        }
        ProgressEntry entry = new ProgressEntry(name, position[0], position[1], begin, finished, isVertical, isOpposite);
        progress.add(entry);
        progressMap.put(name, entry);
    }

    private static void buildSlots(String name, Map<?, ?> data, List<SlotsEntry> slots, Map<String, SlotsEntry> slotsMap) {
        boolean isVertical = "vertical".equals(data.get("direction"));
        int size = getInt(data, "size", 18);
        int[] count = getPoint(data, "count", 1, isVertical);
        int[] position = getPoint(data, "position");
        int[] offset = getPoint(data, "offset", 0, !isVertical);
        List<SlotEntry> s = new ArrayList<>(count[0] * count[1]);
        for (int y = 0; y < count[1]; y++) {
            for (int x = 0; x < count[0]; x++) {
                SlotEntry entry = new SlotEntry(name, size,
                        position[0] + x * size + x * offset[0],
                        position[1] + y * size + x * offset[1]);
                s.add(entry);
            }
        }
        SlotsEntry entry = new SlotsEntry(name, List.copyOf(s));
        slots.add(entry);
        slotsMap.put(name, entry);
    }

    private static void buildSlot(String name, Map<?, ?> data, List<SlotEntry> slot, Map<String, SlotEntry> slotMap) {
        int size = getInt(data, "size", 18);
        int[] position = getPoint(data, "position");
        SlotEntry entry = new SlotEntry(name, size, position[0], position[1]);
        slot.add(entry);
        slotMap.put(name, entry);
    }

    private static void buildTexture(String name, Map<?, ?> data, List<TextureEntry> textures, Map<String, TextureEntry> textureMap) {
        int[] size = getPoint(data, "size");
        int[] position = getPoint(data, "position", new int[]{0, 0});
        TextureEntry entry = new TextureEntry(name, position[0], position[1], size[0], size[1]);
        textures.add(entry);
        textureMap.put(name, entry);
    }

    private static void buildPoint(String name, Map<?, ?> data, List<PointEntry> points, Map<String, PointEntry> pointMap) {
        buildPoint(name, (List<?>) data.get("position"), points, pointMap);
    }

    private static void buildPoint(String name, List<?> data, List<PointEntry> points, Map<String, PointEntry> pointMap) {
        PointEntry entry = new PointEntry(name, (int) data.get(0), (int) data.get(1));
        points.add(entry);
        pointMap.put(name, entry);
    }

    private static int[] getPoint(Map<?, ?> data, String name, int[] defaultValue) {
        return data.containsKey(name) ? getPoint(data, name) : defaultValue;
    }

    private static int[] getPoint(Map<?, ?> data, String name) {
        List<?> list = (List<?>) data.get(name);
        return new int[]{(int) list.get(0), (int) list.get(1)};
    }

    private static int[] getPoint(Map<?, ?> data, String name, int defaultValue, boolean vertical) {
        Object obj = data.get(name);
        if (obj instanceof Integer i) {
            return vertical ? new int[]{i, defaultValue} : new int[]{defaultValue, i};
        } else if (obj instanceof List<?>) {
            return getPoint(data, name);
        } else {
            return new int[]{defaultValue, defaultValue};
        }
    }

    private static int getInt(Map<?, ?> data, String name, int defaultValue) {
        return data.containsKey(name) ? (int) data.get(name) : defaultValue;
    }
}
