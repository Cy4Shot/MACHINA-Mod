package com.machina.api.cap.sided;

public enum Side {
    NONE(false, false),
    OUTPUT(false, true),
    INPUT(true, false);
	
	public static final Side[] NONES = new Side[] { Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE };
	public static final Side[] INPUTS = new Side[] { Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT };
	public static final Side[] OUTPUTS = new Side[] { Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT };

    private final boolean input;
    private final boolean output;

    Side(boolean input, boolean output) {
        this.input = input;
        this.output = output;
    }

    public boolean isInput() {
        return input;
    }

    public boolean isOutput() {
        return output;
    }

    public String getSerializedName() {
        return name().toLowerCase();
    }
}