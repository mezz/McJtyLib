package mcjty.lib.network;

import net.minecraft.util.math.BlockPos;

public class Argument {
    private final String name;
    private final ArgumentType type;
    private final Object value;

    public Argument(String name, ArgumentType type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Argument(String name, String value) {
        this(name, ArgumentType.TYPE_STRING, value);
    }

    public Argument(String name, int value) {
        this(name, ArgumentType.TYPE_INTEGER, value);
    }

    public Argument(String name, double value) {
        this(name, ArgumentType.TYPE_DOUBLE, value);
    }

    public Argument(String name, BlockPos value) {
        this(name, ArgumentType.TYPE_BLOCKPOS, value);
    }

    public Argument(String name, boolean value) {
        this(name, ArgumentType.TYPE_BOOLEAN, value);
    }

    public String getName() {
        return name;
    }

    public ArgumentType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getString() {
        return (String) value;
    }

    public Integer getInteger() {
        return (Integer) value;
    }

    public Double getDouble() {
        return (Double) value;
    }

    public BlockPos getCoordinate() {
        return (BlockPos) value;
    }

    public boolean getBoolean() {
        return (Boolean) value;
    }
}
