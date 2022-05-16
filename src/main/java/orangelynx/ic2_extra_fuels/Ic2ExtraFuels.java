package orangelynx.ic2_extra_fuels;

import com.google.gson.Gson;
import ic2.api.recipe.Recipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import java.io.*;


@Mod(modid = Constant.MODID, name = Constant.NAME, version = Constant.VERSION, acceptableRemoteVersions = "*")
public class Ic2ExtraFuels {


    private static Logger logger;

    private FuelData fuelData;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        File additionalFuelsConfigFile = new File(event.getModConfigurationDirectory(), Constant.ADDITIONAL_FUELS_CFG_FILE_NAME);

        if (!additionalFuelsConfigFile.exists()) {
            logger.info("Additional fuels config file \"{}\" not found. Creating default config.", Constant.ADDITIONAL_FUELS_CFG_FILE_NAME);
            this.writeDefaultAdditionalFuelsConfig(event.getModConfigurationDirectory());
        }
        try (FileReader fileReader = new FileReader(additionalFuelsConfigFile)) {
            Gson gson = new Gson();
            this.fuelData = gson.fromJson(fileReader, FuelData.class);
        } catch (FileNotFoundException e) {
            // This should never happen as we checked the file's existence before!
            logger.error("Additional fuels config file \"" + Constant.ADDITIONAL_FUELS_CFG_FILE_NAME + "\" not found! Something is very odd, please consider filing a bug report.", e);
        } catch (IOException e) {
            logger.error("Additional fuels config file \"" + Constant.ADDITIONAL_FUELS_CFG_FILE_NAME + "\" is malformed! Replacing with default config for next restart.", e);
            this.writeDefaultAdditionalFuelsConfig(event.getModConfigurationDirectory());
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (Fuel fuel : fuelData.getSemiFluidGenerator()) {
            if (Recipes.semiFluidGenerator.getFuelProperties().containsKey(fuel.getName())) {
                Gson gson = new Gson();
                logger.info("fuel semiFluidGenerator exists with props: " + gson.toJson(Recipes.semiFluidGenerator.getFuelProperties().get(fuel.getName())));
                continue;
            }
            Recipes.semiFluidGenerator.addFluid(fuel.getName(), fuel.getAmountConsumedPerTick(), fuel.getGeneratedPerTick());
            logger.info("Recipes.semiFluidGenerator.addFluid({})",fuel);
        }
        for (Fuel fuel : fuelData.getFluidHeatGenerator()) {
            if (Recipes.fluidHeatGenerator.getBurnProperties().containsKey(fuel.getName())) {
                Gson gson = new Gson();
                logger.info("fuel fluidHeatGenerator exists with props: " + gson.toJson(Recipes.fluidHeatGenerator.getBurnProperties().get(fuel.getName())));
                continue;
            }
            Recipes.fluidHeatGenerator.addFluid(fuel.getName(), fuel.getAmountConsumedPerTick(), fuel.getGeneratedPerTick());
            logger.info("Recipes.fluidHeatGenerator.addFluid({})",fuel);
        }
    }

    private void writeDefaultAdditionalFuelsConfig(File modConfigDirectory) {
        File additionalFuelsConfigFile = new File(modConfigDirectory, Constant.ADDITIONAL_FUELS_CFG_FILE_NAME);
        if (additionalFuelsConfigFile.exists()) {
            return;
        }
        try (InputStream defaultAdditionalFuelsConfigStream = getClass().getResourceAsStream("/assets/config/" + Constant.ADDITIONAL_FUELS_CFG_FILE_NAME)) {
            if (defaultAdditionalFuelsConfigStream == null) {
                throw new NullPointerException("don't find default additional");
            }
            try (FileOutputStream outputStream = new FileOutputStream(additionalFuelsConfigFile)) {
                IOUtils.copy(defaultAdditionalFuelsConfigStream, outputStream);
            }
        } catch (Throwable e) {
            logger.error("Could not write default additional fuels config file \"" + Constant.ADDITIONAL_FUELS_CFG_FILE_NAME +
                            "\" to mod's configuration directory! If this issue persists across restarts, please consider filing a bug report.",
                    e);
        }
    }
}
