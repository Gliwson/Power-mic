package pl.power.services.impl;

import java.io.Serializable;
import java.util.List;

public class PairPageable<D> implements Serializable {
    private Long totalElements;
    private List<D> elements;

    public PairPageable(Long totalElements, List<D> elements) {
        this.totalElements = totalElements;
        this.elements = elements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<D> getElements() {
        return elements;
    }

    public void setElements(List<D> elements) {
        this.elements = elements;
    }
}
