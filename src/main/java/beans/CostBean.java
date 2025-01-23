package beans;

import java.util.List;

public class CostBean {
    private final List<Double> cost;

    public CostBean(List<Double> cost) {
        this.cost = cost;
    }

    public List<Double> getCost() {
        return cost;
    }
}
