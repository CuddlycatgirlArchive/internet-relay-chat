package gay.sukumi.irc.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Configuration {

    public static final Configuration INSTANCE = new Configuration();

    private final Yaml yaml = new Yaml(new Constructor(Configurations.class));
    private final File configFile = new File("irc.yml");
    private final Representer representer = new Representer();
    private Configurations configurations = new Configurations(8888, "mongodb://localhost:27015", "Lucy");

    public void save() {
        try {
            DumperOptions options = new DumperOptions();
            options.setIndent(2);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            representer.addClassTag(Configurations.class, Tag.MAP);
            new Yaml(representer, options).dump(configurations, new FileWriter(configFile));
        } catch (Exception ignored) {
        }
    }

    public void load() {
        try {
            if (!configFile.exists())
                save();

            try (InputStream in = Files.newInputStream(Paths.get("irc.yml"))) {
                configurations = yaml.loadAs(in, Configurations.class);
            }

        } catch (Exception ignored) {}
    }

    public Configurations get() {
        return configurations;
    }
}
