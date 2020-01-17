package orangelynx.ic2_extra_fuels;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import ic2.api.recipe.Recipes;


@Mod(modid = Ic2ExtraFuels.MODID, name = Ic2ExtraFuels.NAME, version = Ic2ExtraFuels.VERSION, acceptableRemoteVersions = "*")
public class Ic2ExtraFuels {
    public static final String MODID = "ic2_extra_fuels";
    public static final String NAME = "IC2 Extra Fuels";
    public static final String VERSION = "1.2";

    private static final String ADDITIONAL_FUELS_CFG_FILE_NAME = "additionalFuels.csv";

    private static Logger logger;

    private List<Fuel> additionalFuels = new ArrayList<>();

    public final class Fuel {
        public Fuel(String name, int amountConsumedPerTick, double powerGeneratedPerTick, int heatGeneratedPerTick)
        {
            this.name = name;
            this.amountConsumedPerTick = amountConsumedPerTick;
            this.powerGeneratedPerTick = powerGeneratedPerTick;
            this.heatGeneratedPerTick = heatGeneratedPerTick;
        }

        public String name;
        public int amountConsumedPerTick;
        public double powerGeneratedPerTick;
        public int heatGeneratedPerTick;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        File additionalFuelsConfigFile = new File(event.getModConfigurationDirectory(), ADDITIONAL_FUELS_CFG_FILE_NAME);

        if (!additionalFuelsConfigFile.exists()) {
            logger.info("Additional fuels config file \"{}\" not found. Creating default config.", ADDITIONAL_FUELS_CFG_FILE_NAME);
            this.writeDefaultAdditionalFuelsConfig(event.getModConfigurationDirectory());
        }

        try {
            CSVReader additionalFuelsConfigFileReader = new CSVReader(new FileReader(additionalFuelsConfigFile));

            String[] nextLine;
            while ((nextLine = additionalFuelsConfigFileReader.readNext()) != null) {
                additionalFuels.add(new Fuel(nextLine[0],
                                             Integer.parseInt(nextLine[1]),
                                             Double.parseDouble(nextLine[2]),
                                             Double.parseInt(nextLine[3])));
            }

            additionalFuelsConfigFileReader.close();
        } catch (FileNotFoundException e) {
            // This should never happen as we checked the file's existence before!
            logger.error("Additional fuels config file \"{}\" not found! Something is very odd, please consider filing a bug report.", ADDITIONAL_FUELS_CFG_FILE_NAME);
        } catch (IOException e) {
            logger.error("Additional fuels config file \"{}\" is malformed! Replacing with default config for next restart.",
                    ADDITIONAL_FUELS_CFG_FILE_NAME);
            this.writeDefaultAdditionalFuelsConfig(event.getModConfigurationDirectory());
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        for (Fuel fuel: additionalFuels) {
            Recipes.semiFluidGenerator.addFluid(fuel.name, fuel.amountConsumedPerTick, fuel.powerGeneratedPerTick);
            Recipes.fluidHeatGenerator.addFluid(fuel.name, fuel.amountConsumedPerTick, fuel.heatGeneratedPerTick);
        }
    }

    private void writeDefaultAdditionalFuelsConfig(File modConfigDirectory)
    {
        InputStream defaultAdditionalFuelsConfigStream = getClass().getResourceAsStream("/assets/config/" + ADDITIONAL_FUELS_CFG_FILE_NAME);
        File additionalFuelsConfigFile = new File(modConfigDirectory, ADDITIONAL_FUELS_CFG_FILE_NAME);
        try {
            Files.copy(defaultAdditionalFuelsConfigStream, additionalFuelsConfigFile.toPath());
            defaultAdditionalFuelsConfigStream.close();
        } catch (IOException e) {
            logger.error("Could not write default additional fuels config file \"{}\" to mod's configuration directory! If this issue persists across restarts, please consider filing a bug report.",
                    ADDITIONAL_FUELS_CFG_FILE_NAME);
        }
    }
}
