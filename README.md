# IC2 Extra Fuels

## Description
This mod is an [Industrial Craft 2](https://industrial-craft.net) addon that allows free configuration of additional fuels to be consumed by Industrial Craft's [Semifluid Generator](https://wiki.industrial-craft.net/index.php?title=Semifluid_Generator) (and as of 1.2-beta the [Fluid Heat Generator](https://wiki.industrial-craft.net/index.php?title=Fluid_Heat_Generator)).
The mod is especially intended to be used by users of Buildcraft until the new set of fuels introduced in Buildcraft 7.99a (Dense Fuel, Gaseous Fuel, etc.) are officially supported by Industrial Craft. See [this bug report](https://bt.industrial-craft.net/view.php?id=2345).

## Download
Compiled releases are found under the project's [release page](https://github.com/orangelynx/ic2-extra-fuels/releases).

## User Guide
You may compile the project yourself or download a release version. Simply place the `*.jar` file into the `/mods` folder. Upon startup, a default config file `additionalFuels.csv` ([link](src/main/resources/assets/config/additionalFuels.csv)) will be created in the configs folder `/configs`.

Each line in the `additionalFuels.csv` config file adds a liquid to be processed by the Semifluid Generator. The format is

`[fluid_name],[amount_consumed_per_tick_in_mB],[power_generated_per_tick]`

per 1.2-beta, `heat_generated_per_tick` must be also supplied:

`[fluid_name],[amount_consumed_per_tick_in_mB],[power_generated_per_tick],[heat_generated_per_tick]`

The value for `fluid_name` has to be taken out of the respective mod that creates the fluid, e.g. for buildcraft [BCEnergyFluids.java](https://github.com/BuildCraft/BuildCraft/blob/620cf49defc70da7088be4f1d37759f5b446d71e/common/buildcraft/energy/BCEnergyFluids.java).

This mod is only needed on the server side. 

## Legal Stuff
This project is licensed under the [MIT License](LICENSE).

For attributions please see the [NOTICE file](NOTICE).
