package games.moegirl.sinocraft.sinodivination.codegen;

import com.github.javaparser_new.StaticJavaParser;
import com.github.javaparser_new.ast.CompilationUnit;
import com.github.javaparser_new.ast.body.BodyDeclaration;
import com.github.javaparser_new.ast.body.ClassOrInterfaceDeclaration;
import lq2007.plugins.gradle_plugin.support.EnumLoopResult;
import lq2007.plugins.gradle_plugin.support.ISourcePlugin;
import lq2007.plugins.gradle_plugin.support.PluginContext;
import lq2007.plugins.gradle_plugin.support.PluginHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class GenerateBlocksByResource implements ISourcePlugin {

    private static final String HOLDER_CLASS = "games.moegirl.sinocraft.sinodivination.block.ModBlocks";
    private static final String HOLDER_PATH = HOLDER_CLASS.replace(".", "/") + ".java";

    private static final String TEMPLATE_ORE =
            "public static final RegistryObject<Block> %s = REGISTRY.register(\"%s\", OreBlock::new);";

    private CompilationUnit unit;
    private ClassOrInterfaceDeclaration body;
    private Path srcPath = null;

    @Override
    public void begin(PluginContext context, PluginHelper helper) throws Exception {
        unit = StaticJavaParser.parse(srcPath(helper).resolve(HOLDER_PATH));
        body = unit.getPrimaryType()
                .filter(BodyDeclaration::isClassOrInterfaceDeclaration)
                .map(BodyDeclaration::asClassOrInterfaceDeclaration)
                .orElseThrow();

        unit.addImport("games.moegirl.sinocraft.sinodivination.block.OreBlock");
        System.out.println("Here");
    }

    @Override
    public void each(Path file, PluginContext context, PluginHelper helper) throws Exception {
        String fileName = file.getFileName().toString();
        if (fileName.endsWith(".png")) {
            if (fileName.startsWith("ore_")) {
                addOre(fileName);
            }
        }
    }

    @Override
    public EnumLoopResult finished(PluginContext context, PluginHelper helper) throws Exception {
        unit.getStorage().orElseThrow(IOException::new).save();
        return EnumLoopResult.FINISHED;
    }

    @Override
    public Path getLoopRoot(PluginHelper helper) {
        return srcPath(helper).getParent()
                .resolve("resources/assets/sinodivination/textures/block");
    }

    private Path srcPath(PluginHelper helper) {
        if (srcPath == null) {
            srcPath = helper.projectPath().resolve("src/main/java/");
        }
        System.out.println(srcPath);
        return srcPath;
    }

    private void addOre(String fileName) {
        String registerName = fileName.substring(0, fileName.length() - 4);
        String fieldName = registerName.toUpperCase(Locale.ROOT);
        if (body.getFieldByName(fieldName).isEmpty()) {
            body.addMember(StaticJavaParser.parseBodyDeclaration(String.format(TEMPLATE_ORE, fieldName, registerName)));
        }
    }
}
