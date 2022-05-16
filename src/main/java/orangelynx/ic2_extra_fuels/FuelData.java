package orangelynx.ic2_extra_fuels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FuelData {

    private List<Fuel> semiFluidGenerator = new ArrayList<>();
    private List<Fuel> fluidHeatGenerator = new ArrayList<>();

    public FuelData() {

    }

    public List<Fuel> getSemiFluidGenerator() {
        return semiFluidGenerator;
    }

    public List<Fuel> getFluidHeatGenerator() {
        return fluidHeatGenerator;
    }

    public void setFluidHeatGenerator(List<Fuel> fluidHeatGenerator) {
        this.fluidHeatGenerator = fluidHeatGenerator;
    }

    public void setSemiFluidGenerator(List<Fuel> semiFluidGenerator) {
        this.semiFluidGenerator = semiFluidGenerator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelData fuelData = (FuelData) o;
        return Objects.equals(semiFluidGenerator, fuelData.semiFluidGenerator) && Objects.equals(fluidHeatGenerator, fuelData.fluidHeatGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semiFluidGenerator, fluidHeatGenerator);
    }

    @Override
    public String toString() {
        return "FuelData{" +
                "semiFluidGenerator=" + semiFluidGenerator +
                ", fluidHeatGenerator=" + fluidHeatGenerator +
                '}';
    }
}
