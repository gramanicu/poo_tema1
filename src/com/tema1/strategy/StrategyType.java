package com.tema1.strategy;

public enum StrategyType {
    Base, Bribe, Greedy;

    @Override
    public String toString() {
        switch (this) {
            case Base: return "BASIC";
            case Bribe: return "BRIBED";
            case Greedy: return "GREEDY";
            default: return this.toString();
        }
    }
}
