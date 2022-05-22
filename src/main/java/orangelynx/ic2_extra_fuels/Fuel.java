package orangelynx.ic2_extra_fuels;

import java.util.Objects;

public class Fuel {

    public Fuel() {

    }

    private String name;
    private int amountConsumedPerTick;
    private int generatedPerTick;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountConsumedPerTick() {
        return amountConsumedPerTick;
    }

    public void setAmountConsumedPerTick(int amountConsumedPerTick) {
        this.amountConsumedPerTick = amountConsumedPerTick;
    }

    public int getGeneratedPerTick() {
        return generatedPerTick;
    }

    public void setGeneratedPerTick(int generatedPerTick) {
        this.generatedPerTick = generatedPerTick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fuel fuel = (Fuel) o;
        return amountConsumedPerTick == fuel.amountConsumedPerTick && generatedPerTick == fuel.generatedPerTick && Objects.equals(name, fuel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amountConsumedPerTick, generatedPerTick);
    }

    @Override
    public String toString() {
        return "Fuel{" +
                "name='" + name + '\'' +
                ", amountConsumedPerTick=" + amountConsumedPerTick +
                ", generatedPerTick=" + generatedPerTick +
                '}';
    }
}