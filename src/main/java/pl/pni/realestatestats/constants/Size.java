package pl.pni.realestatestats.constants;

public enum Size {
    S(18,45),
    M(46,80),
    L(81,400);

    private double fromArea;
    private double toArea;

    Size(double fromArea, double toArea) {
        this.fromArea = fromArea;
        this.toArea = toArea;
    }

    public double getFromArea() {
        return fromArea;
    }

    public double getToArea() {
        return toArea;
    }
}

